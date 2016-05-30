package sx.cfdi.sw2

import grails.transaction.NotTransactional
import grails.transaction.Transactional
import groovy.sql.Sql
import org.apache.commons.lang.StringUtils
import org.bouncycastle.util.encoders.Base64
import org.springframework.jdbc.datasource.SingleConnectionDataSource
import sx.cfdi.Comprobante
import sx.cfdi.Comprobante.Emisor
import sx.cfdi.Comprobante.Receptor
import sx.cfdi.Comprobante.Conceptos
import sx.cfdi.Comprobante.Conceptos.Concepto
import sx.cfdi.Comprobante.Impuestos
import sx.cfdi.Comprobante.Impuestos.Traslados
import sx.cfdi.Comprobante.Impuestos.Traslados.Traslado
import sx.cfdi.Comprobante.Impuestos.Retenciones
import sx.cfdi.ObjectFactory
import sx.cfdi.TUbicacion
import sx.cfdi.TUbicacionFiscal
import sx.cfdi.ine.INE
import sx.cfdi.ine.TAmbito
import sx.cfdi.ine.TClaveEntidad
import sx.cfdi.ine.TTipoComite
import sx.cfdi.ine.TTipoProc
import sx.cfdi.utils.Papel

import javax.xml.bind.JAXBContext
import javax.xml.bind.Marshaller
import java.math.RoundingMode

class ImportadorDeSiipapService {

    def grailsApplication

    def cadenaBuilder

    def sellador

    @NotTransactional
    def buscarVenta(String id) {
        def row = sql().firstRow("select * from sx_ventas v where v.cargo_id = ? ",[id])
        return row
    }

    @NotTransactional
    def buscarCfdi(String serie,String folio) {
        def row = sql().firstRow("select * from sx_cfdi  where serie = ? and folio = ?",[serie,folio])
        return row
    }

    @NotTransactional
    def buscarCliente(Long id) {
        def row = sql().firstRow("select * from sx_clientes v where v.cliente_id = ? ",[id])
        return row
    }

    @NotTransactional
    def buscarEmpresa(Long id) {
        def row = sql().firstRow("select * from sw_empresas  where empresa_id = ? ",[id])
        return row
    }

    @NotTransactional
    def buscarSucursal(Long id) {
        def row = sql().firstRow("select * from sw_sucursales  where sucursal_id = ? ",[id])
        return row
    }

    def buscarPartidas(String id){
        def rows = sql().rows(
                "select v.*,u.unidad from sx_ventasdet v join sx_unidades u on v.unidad_id = u.unidad where venta_id = ?",
                [id])
        return rows
    }

    def importarCfdi(String serie, String folio){
        def oldCfdi = buscarCfdi(serie,folio)
        if(oldCfdi == null) {
            throw new RuntimeException("No localizo el Serie: $serie Folio:$folio")
        }
        def venta = buscarVenta(oldCfdi.origen_id)
        if(venta == null){
            throw new RuntimeException("No localizo la venta origen del Cfdi  Serie: $serie Folio:$folio Venta (Cargo_id):"+oldCfdi.origen_id)
        }
        return generarCfdi(venta, serie)
    }

    def generarCfdi(def venta, String serie){

        ObjectFactory factory = new ObjectFactory()
        Comprobante cfdi = factory.createComprobante();
        cfdi.version = '3.2'
        cfdi.setTipoDeComprobante('ingreso')
        cfdi.formaDePago = 'PAGO EN UNA SOLA EXHIBICION'
        cfdi.fecha = venta.fecha
        cfdi.metodoDePago = venta.fpago
        cfdi.moneda = venta.moneda
        cfdi.tipoCambio = venta.tc

        def empresa = buscarEmpresa(1)

        Emisor emisor = factory.createComprobanteEmisor()
        emisor.setNombre(empresa.nombre)
        emisor.setRfc(empresa.rfc)
        TUbicacionFiscal uf = new TUbicacionFiscal()
        uf.calle = empresa.calle
        uf.colonia = empresa.colonia
        uf.noExterior = empresa.numero
        uf.noInterior = empresa.numeroint?:' '
        uf.municipio = empresa.delmpo
        uf.estado = empresa.estado?:' '
        uf.codigoPostal = empresa.cp
        uf.pais = empresa.pais
        emisor.domicilioFiscal = uf
        // Regimen fiscal
        Emisor.RegimenFiscal regimenFiscal = factory.createComprobanteEmisorRegimenFiscal()
        regimenFiscal.setRegimen(empresa.regimen)
        emisor.regimenFiscal.add(regimenFiscal)

        // Expedido en
        def sucursal = buscarSucursal(venta.sucursal_id)
        if(sucursal){
            TUbicacion expedidoEn=new TUbicacion()
            expedidoEn.setCalle(sucursal.calle);
            expedidoEn.setCodigoPostal(sucursal.cp);
            expedidoEn.setColonia(sucursal.colonia);
            expedidoEn.setEstado(sucursal.estado?:'_');
            expedidoEn.setMunicipio(sucursal.delmpo);
            expedidoEn.setNoExterior(sucursal.numero?:'-');
            expedidoEn.setNoInterior(sucursal.numeroint?:'-');
            expedidoEn.setPais(sucursal.pais);
            cfdi.setLugarExpedicion(sucursal.pais)
            emisor.setExpedidoEn(expedidoEn)
        }
        cfdi.emisor = emisor

        def cliente = buscarCliente(venta.cliente_id)
        assert cliente,' No existe el cliente para la venta: '+venta.cargo_id

        Receptor receptor = factory.createComprobanteReceptor()
        receptor.nombre = venta.nombre
        receptor.rfc = cliente.rfc
        TUbicacion direccion = new TUbicacion()
        direccion.calle = cliente.calle
        direccion.noExterior = cliente.numero?:'-'
        direccion.noInterior = cliente.numeroint?:'-'
        direccion.colonia = cliente.colonia
        direccion.municipio = cliente.delmpo
        direccion.estado = cliente.estado?:'_'
        direccion.pais = cliente.pais
        direccion.codigoPostal = cliente.cp
        receptor.setDomicilio(direccion)
        cfdi.setReceptor(receptor)

        def partidas = buscarPartidas(venta.cargo_id)
        Conceptos conceptos = factory.createComprobanteConceptos()
        partidas.each { row ->
            Concepto c = factory.createComprobanteConceptosConcepto()

            c.setCantidad(new BigDecimal(row.cantidad/row.factoru)
                    .multiply(BigDecimal.valueOf(-1))
                    .setScale(3,RoundingMode.HALF_EVEN));
            c.setUnidad(row.unidad);
            c.setNoIdentificacion(row.clave);
            c.setDescripcion(row.descripcion);
            c.setValorUnitario(row.precio.setScale(2));
            c.setImporte(row.importe.setScale(2));
            if(venta.clave.equals("1")){
                c.setValorUnitario(row.precio * 1.16);
                c.setImporte(row.importe * 1.16);
            }
            conceptos.getConcepto().add(c)
        }
        cfdi.setConceptos(conceptos)
        BigDecimal importeBruto = partidas.sum 0.0 ,{it.importe}
        BigDecimal descuento = partidas.sum 0.0 ,{it.DSCTO}
        cfdi.setTotal(venta.total)
        cfdi.setSubTotal(importeBruto);

        Impuestos impuestos = new Impuestos()
        cfdi.setImpuestos(impuestos)
        if(venta.clave == "1"){
            BigDecimal subTotal=importeBruto * 1.16
            cfdi.setSubTotal(subTotal);
            cfdi.setDescuento(descuento * 1.16);
        }else{
            impuestos.setTotalImpuestosTrasladados(venta.impuesto);

            Traslados traslados=new Traslados()
            cfdi.getImpuestos().setTraslados(traslados)

            // IVA
            Traslado traslado=new Traslado()
            traslado.setImpuesto('IVA');
            traslado.setImporte(venta.impuesto);
            traslado.setTasa(0.16);
            traslados.getTraslado().add(traslado)

            cfdi.setDescuento(descuento);
        }
        //cfdi.setImpuestos(impuestos)

        cfdi.folio = venta.docto
        cfdi.serie = serie

        /****** INE ******

        sx.cfdi.ine.ObjectFactory ineFactory = new sx.cfdi.ine.ObjectFactory()
        INE ine = ineFactory.createINE()
        ine.setVersion('1.0');
        ine.setTipoProceso(TTipoProc.ORDINARIO)
        ine.setTipoComite(TTipoComite.EJECUTIVO_NACIONAL)
        **/

        /*
        def entidad = ineFactory.createINEEntidad();
        entidad.setAmbito(TAmbito.FEDERAL);
        entidad.setClaveEntidad(TClaveEntidad.AGU);
        def contabilidad = ineFactory.createINEEntidadContabilidad();
        contabilidad.setIdContabilidad(1);
        entidad.getContabilidad().add(contabilidad);
        ine.getEntidad().add(entidad);
        */
        /*
        Comprobante.Complemento complemento = factory.createComprobanteComplemento()
        complemento.getAny().add(ine)
        cfdi.setComplemento(complemento)
        */
        //****** INE ********/

        //def cadena = cadenaBuilder.generarCadena(cfdi)
        //log.info 'Generando sello para cadena: ' + cadena

        //def sello = sellador.sellar(empresa.CFDI_PK,cadena)
        //cfdi.setSello(sello)

        //Certificado digital
        //byte[] encodedCert=Base64.encode(sellador.getCertificado(empresa.CERTIFICADO_DIGITAL).getEncoded())
        //cfdi.setCertificado(new String(encodedCert))
        //cfdi.setNoCertificado(empresa.NO_CERTIFICADO)





        /* Adenda especial
        Comprobante.Addenda addenda = factory.createComprobanteAddenda()
        Papel papel = new Papel()
        papel.setCadenaOriginal(cadena)
        addenda.getAny().add(papel)
        cfdi.setAddenda(addenda)
        */

        return cfdi
    }

    def toXml(Comprobante cfdi){
        JAXBContext ctx = JAXBContext.newInstance(Comprobante,INE,Papel)
        Marshaller mashaller = ctx.createMarshaller()
        mashaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true)
        mashaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION,
                "http://www.sat.gob.mx/cfd/3 http://www.sat.gob.mx/sitio_internet/cfd/3/cfdv32.xsd" +
                " http://www.sat.gob.mx/ine http://www.sat.gob.mx/sitio_internet/cfd/ine/ine10.xsd");
        StringWriter writer = new StringWriter()
        mashaller.marshal(cfdi,writer)
        return writer
    }

    def importarEmpresa(){
        def emp = buscarEmpresa(1)

    }

    private sql(){
        def db=grailsApplication.config.sw2.db
        //def db = [url: 'jdbc:mysql://localhost/sw2',username: 'root', password: 'sys']
        SingleConnectionDataSource ds=new SingleConnectionDataSource(
                driverClassName:'com.mysql.jdbc.Driver',
                url:db.url,
                username:db.username,
                password:db.password,
                suppressClose:true)
        Sql sql=new Sql(ds)
        return sql
    }
}
