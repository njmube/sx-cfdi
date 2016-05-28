package sx.cfdi.ine

import groovy.transform.ToString

@ToString(excludes = 'id,version',includeNames = true,ignoreNulls = true, includePackage = false)
class Entidad {

    TClaveEntidad clave

    TAmbito ambito

    String contabilidades

    static belongsTo = [complementoIne:ComplementoIne]

    static constraints = {
        ambito nullable:true
        contabilidades nullable:true
    }
}


