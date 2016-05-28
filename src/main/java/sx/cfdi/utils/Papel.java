package sx.cfdi.utils;

import javax.xml.bind.annotation.*;

/**
 * Created by rcancino on 26/05/16.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "cadenaOriginal",
})
@XmlRootElement(name = "Papel")
public class Papel {

    @XmlAttribute(name = "cadenaOriginal", required = true)
    protected String cadenaOriginal;

    public String getCadenaOriginal() {
        return cadenaOriginal;
    }

    public void setCadenaOriginal(String cadenaOriginal) {
        this.cadenaOriginal = cadenaOriginal;
    }


}
