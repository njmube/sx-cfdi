package sx.cfdi

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import sx.cfdi.ine.ComplementoIne
import sx.cfdi.ine.INE
import sx.cfdi.utils.Papel

import javax.xml.bind.JAXBContext
import javax.xml.bind.Unmarshaller
import java.io.ByteArrayInputStream
import org.w3c.dom.Element

@EqualsAndHashCode(includes = "id,uuid")
@ToString(includeNames=true,includes="id,serie,folio,tipo,fecha,uuid")
class Cfdi {

    String id

    String serie

    String folio

    Date fecha

    String emisor

    String emisorRfc

    String receptor

    String receptorRfc

    String tipo

    String uuid

    Date timbrado

    BigDecimal total

    String origen

    URL xml

    Date dateCreated

    Date lastUpdated


    Comprobante comprobante

    //TimbreFiscal timbreFiscal
    Element timbreFiscal

    String comentario

    static hasOne = [cancelacion: CancelacionDeCfdi,complementoIne:ComplementoIne]

    static constraints = {
        serie blannk:false,maxSize:15
        folio blank:false,maxSize:20,unique: 'serie'
        uuid nullable:true,maxSize:300
        timbrado(nullable:true)
        tipo inList:['ingreso','egreso','traslado']
        fecha nullable:false
        emisor blank:false,maxSize:600
        emisorRfc blank:false,maxSize:13
        receptor blank:false,maxSize:600
        receptorRfc blank:false,maxSize:13
        xml url:true
        origen nullable:true,maxSize:255
        cancelacion nullable:true
        complementoIne nullable:true
        comentario nullable:true,maxSize:100
    }

    static mapping={
        id generator:'uuid'
        complementoIne unique:true
    }

    //static transients = ['comprobante','timbreFiscal']

    static transients = ['comprobante','timbreFiscal']

    public Cfdi() {}

    public Cfdi(Comprobante c) {
        serie=c.serie
        folio=c.folio
        fecha=c.getFecha()
        emisor=c.getEmisor().getNombre()
        emisorRfc=c.getEmisor().getRfc()
        receptor=c.receptor.nombre
        receptorRfc=c.receptor.rfc
        total=c.total
    }

    Comprobante getComprobante(){
        if(comprobante == null){
            JAXBContext jaxbContext = JAXBContext.newInstance(Comprobante,INE,Papel);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            comprobante = (Comprobante) jaxbUnmarshaller.unmarshal(this.xml.newInputStream())
        }
        return comprobante
    }



    def getTimbreFiscal(){
        if( timbreFiscal == null ) {
            if(getComprobante() !=null){
                timbreFiscal = getComprobante()?.getComplemento()?.getAny().find {

                    it instanceof  Element && it.getLocalName() == 'TimbreFiscalDigital'
                }
            }
        }
        return timbreFiscal
    }



    String toString(){
        return "($emisor) Id:$id  Tipo:$tipo Serie:$serie Folio:$folio  UUID:$uuid "
    }
}
