@javax.xml.bind.annotation.XmlSchema(
        namespace = "http://www.sat.gob.mx/cfd/3",
        elementFormDefault = javax.xml.bind.annotation.XmlNsForm.QUALIFIED,
        xmlns = {
                @XmlNs(prefix= "cfdi", namespaceURI="http://www.sat.gob.mx/cfd/3"),
                @XmlNs(prefix= "ine", namespaceURI="http://www.sat.gob.mx/ine")
        })
package sx.cfdi;

import javax.xml.bind.annotation.XmlNs;