package sx.cfdi

import sx.cfdi.ine.INE

import javax.xml.bind.JAXBContext
import javax.xml.bind.util.JAXBSource
import javax.xml.transform.Source
import javax.xml.transform.Transformer
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult
import javax.xml.transform.stream.StreamSource

import org.springframework.context.ResourceLoaderAware
import org.springframework.core.io.ResourceLoader

/**
 * Created by rcancino on 25/05/16.
 */
class CadenaBuilder implements ResourceLoaderAware{

    File xsltFile

    ResourceLoader resourceLoader

    /**
     * Genera la cadean original de un comprobante fiscal digital
     *
     * @return La cadena original
     */
    String generarCadena(Comprobante cfdi){
        try {
            TransformerFactory factory=TransformerFactory.newInstance()
            xsltFile=resourceLoader.getResource("sat/cadenaoriginal_3_2.xslt").getFile()
            assert xsltFile.exists(),"No existe el archivo xslt para la cadena del sat: "+xsltFile.getPath()
            //StreamSource source=new StreamSource(xsltFile);
            StreamSource source = new StreamSource('http://www.sat.gob.mx/sitio_internet/cfd/3/cadenaoriginal_3_2/cadenaoriginal_3_2.xslt')

            Transformer transformer=factory.newTransformer(source);
            Writer writer=new StringWriter();
            StreamResult out=new StreamResult(writer);

            //Source so=new DOMSource(document.getDomNode());
            // Source
            JAXBContext jc = JAXBContext.newInstance(Comprobante,INE);
            JAXBSource so = new JAXBSource(jc, cfdi);

            transformer.transform(so, out);
            return writer.toString();

        }catch (Exception ex){
            ex.printStackTrace()
        }

    }



    @Override
    public void setResourceLoader(ResourceLoader arg0) {
        resourceLoader=arg0

    }
}
