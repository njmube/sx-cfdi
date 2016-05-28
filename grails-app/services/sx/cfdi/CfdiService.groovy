package sx.cfdi

import grails.transaction.Transactional
import org.apache.commons.lang.builder.ToStringBuilder
import org.apache.commons.lang.builder.ToStringStyle
import sx.cfdi.ine.ComplementoIne
import sx.cfdi.ine.INE
import sx.cfdi.utils.Papel
import sx.cfdi.validation.CfdiValidationHandler

import javax.xml.XMLConstants
import javax.xml.bind.JAXBContext
import javax.xml.bind.Marshaller
import javax.xml.bind.Unmarshaller
import javax.xml.transform.Source
import javax.xml.transform.stream.StreamSource
import javax.xml.validation.Schema
import javax.xml.validation.SchemaFactory
import java.text.SimpleDateFormat

@Transactional
class CfdiService {

    def timbrador

    def cadenaBuilder

    def salvar(Comprobante comprobante){
        Cfdi cfdi = Cfdi.where {
            emisorRfc == comprobante.getEmisor().getRfc() && serie == comprobante.getSerie() && folio == comprobante.folio
        }.find()
        if(!cfdi){
            cfdi = new Cfdi()
            cfdi.folio = comprobante.folio
            cfdi.serie = comprobante.serie
            cfdi.emisor = comprobante.emisor.nombre
            cfdi.emisorRfc = comprobante.emisor.rfc
            cfdi.receptor = comprobante.receptor.nombre
            cfdi.receptorRfc = comprobante.receptor.rfc
            cfdi.fecha = comprobante.fecha
            cfdi.tipo = comprobante.tipoDeComprobante
            cfdi.total = comprobante.total
            byte[] xmlData = toBytes(comprobante)
            saveXml(cfdi,xmlData)

        }
        cfdi = cfdi.save failOnError:true, flush:true
        return cfdi
    }

    def update(Cfdi cfdi, Comprobante comprobante){
        if(cfdi.timbrado){
            throw new RuntimeException('CFDI ya timbrado no se puede actualizar su contenido')
        }
        def data = toBytes(comprobante)
        saveXml(cfdi,data)
        cfdi.save failOnError: true, flush: true
        return cfdi
    }

    byte[]  toBytes(Comprobante cfdi){
        JAXBContext ctx = JAXBContext.newInstance(Comprobante,INE,Papel)
        Marshaller marshaller = ctx.createMarshaller()
        //marshaller.setEventHandler(new CfdiValidationHandler())
        //Validation
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)

        StreamSource s1 = new StreamSource('http://www.sat.gob.mx/sitio_internet/cfd/3/cfdv32.xsd')
        StreamSource s2 = new StreamSource('http://www.sat.gob.mx/sitio_internet/cfd/ine/ine10.xsd')
        Source[] sources = new Source[2]
        sources[0] = s1
        sources[1] = s2
        Schema schema = schemaFactory.newSchema(sources)
        marshaller.setSchema(schema)

        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true)
        marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION,
                "http://www.sat.gob.mx/cfd/3 http://www.sat.gob.mx/sitio_internet/cfd/3/cfdv32.xsd" +
                        " http://www.sat.gob.mx/ine http://www.sat.gob.mx/sitio_internet/cfd/ine/ine10.xsd");

        //StringWriter writer = new StringWriter()
        ByteArrayOutputStream out = new ByteArrayOutputStream()
        marshaller.marshal(cfdi,out)
        return out.toByteArray()
    }

    def saveXml(Cfdi cfdi,byte[] data){
        def date = cfdi.fecha
        def name = "${cfdi.receptorRfc}_${cfdi.folio}.xml"
        String year = date[Calendar.YEAR]
        String month = date[Calendar.MONTH]+1
        String day = date[Calendar.DATE]

        def cfdiRootDir = new File('/Users/rcancino/data')
        final FileTreeBuilder treeBuilder = new FileTreeBuilder(cfdiRootDir)
        treeBuilder{
            dir(cfdi.emisor){
                dir(year){
                    dir(month){
                        dir(day){
                            File res = file(name) {
                              setBytes(data)
                            }
                            cfdi.xml = res.toURI().toURL()
                            //cfdi.save flush:true
                        }
                    }
                }
            }
        }

    }

    def timbrar(Cfdi cfdi){
        if(cfdi.uuid != null){
            throw new RuntimeException("Cfdi $cfdi.id ya ha sido timbrado")
        }
        cfdi  =  timbrador.timbrar(cfdi,toBytes(cfdi.getComprobante()))
        //cfdi.uuid = cfdi.getTimbreFiscal().getAttribute('UUID')
        //SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        //cfdi.timbrado=df.parse(cfdi.getTimbreFiscal().getAttribute('FechaTimbrado'))


    }

}
