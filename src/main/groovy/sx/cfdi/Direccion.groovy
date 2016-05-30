package sx.cfdi

import groovy.transform.ToString

@ToString(includeNames=true,includePackage=false)
class Direccion {

    String calle
    String numeroInterior
    String numeroExterior
    String colonia
    String municipio
    String codigoPostal
    String estado
    String pais


    static constraints = {
        calle(size:1..200)
        numeroInterior(size:1..50,nullable:true)
        numeroExterior(size:1..50,nullable:true)
        colonia(nullable:true)
        municipio(nullable:true)
        codigoPostal(nullable:true)
        estado(nullable:true)
        pais(size:1..100)
    }


}


