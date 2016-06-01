package sx.cfdi

import java.text.SimpleDateFormat

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils

import com.edicom.ediwinws.cfdi.client.CfdiClient
import com.edicom.ediwinws.cfdi.utils.ZipUtils

//import org.apache.commons.logging.LogFactory
//import com.luxsoft.lx.core.Empresa

/**
 * Created by rcancino on 26/05/16.
 */
class Timbrador {

    def grailsApplication


    ZipUtils zipUtils=new ZipUtils()
    CfdiClient cfdiClient=new CfdiClient()
    SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")

    Cfdi timbrar(Cfdi cfdi,byte[] xml){
        //assert empresa.usuarioPac,"Debe registrar un usuario para el servicio del PAC "
        //assert empresa.passwordPac,"Debe registrar un password para el servicio del PAC "
        if(cfdi.getTimbreFiscal()!=null){
            throw new RuntimeException("Cfdi $cfdi.id ya ha sido timbrado")
        }
        try {
            String user='PAP830101CR3'
            String password='yqjvqfofb'
            //log.info 'Timbrando: '+cfdi
            //String nombre=cfdi.xmlName
            String nombre = "${cfdi.receptorRfc}_${cfdi.folio}.xml"
            byte[] zipFile=zipUtils.comprimeArchivo(nombre, xml)
            byte[] res
            /*
            if(empresa.timbradoDePrueba){
                log.info 'Timbrando de prueba: '+cfdi
                res=cfdiClient.getCfdiTest(user, password, zipFile)
            }else{
                log.debug 'Timbrando real de: '+cfdi
                res=cfdiClient.getCfdi(user, password, zipFile)
            }
            */
            //res=cfdiClient.getCfdiTest(user, password, zipFile)
            res=cfdiClient.getCfdi(user, password, zipFile)
            
            Map<String, byte[]> map =zipUtils.descomprimeArchivo(res)
            Map.Entry<String, byte[]> entry=map.entrySet().iterator().next()

            //log.info('Timbrado exitoso: '+entry.getValue())
            saveXml(cfdi, entry.getValue(),entry.getKey())


            //cfdi.uuid=cfdi.getTimbreFiscal().getAttribute('UUID')
            //cfdi.timbrado=df.parse(cfdi.getTimbreFiscal().getAttribute('FechaTimbrado'))
            cfdi.save(failOnError:true,flush:true)
            return cfdi
        } catch (Exception e) {
            e.printStackTrace()
            String msg="Imposible timbrar cfdi $cfdi.id Error: "+ExceptionUtils.getMessage(e)
            throw new RuntimeException(msg,ExceptionUtils.getCause(e))
        }
    }

    def saveXml(Cfdi cfdi,byte[] data, String name){
        def date = cfdi.fecha
        //def name = "${cfdi.receptorRfc}_${cfdi.folio}.xml"
        String year = date[Calendar.YEAR]
        String month = date[Calendar.MONTH]+1
        String day = date[Calendar.DATE]
        def sx=grailsApplication.config.sx
        def cfdiRootDir = new File(sx.cfdi.dirPath)
        //def cfdiRootDir = new File('/Users/rcancino/data')
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
                        }
                    }
                }
            }
        }

    }
}
