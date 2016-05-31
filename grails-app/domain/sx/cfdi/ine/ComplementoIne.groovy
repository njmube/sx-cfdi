package sx.cfdi.ine

import groovy.transform.ToString
import sx.cfdi.Cfdi

@ToString(includes = 'id,tipoDeProceso,tipoDeComite,contabilidadId',includeNames = true,ignoreNulls = true, includePackage = false)
class ComplementoIne {

    //Cfdi cfdi

    //TipoDeProceso tipoDeProceso;
    TTipoProc tipoDeProceso

    //TipoDeComite tipoDeComite
    TTipoComite tipoDeComite

    Integer contabilidad

    List entidades

    static hasMany = [entidades:Entidad]

    static belongsTo = [cfdi:Cfdi]

    static constraints = {
        tipoDeComite nullable:true
        contabilidad nullable:true,maxSize:999999

    }
    static mapping = {
        entidades cascade: "all-delete-orphan"
    }
}
/*
enum TipoDeProceso {

    ORDINARIO("Ordinario"),
    PRECAMPAÑA("Precampa\u00f1a"),
    CAMPAÑA("Campa\u00f1a");

    private String value

    TipoDeProceso(String v){
        this.value = v
    }

    public String value() {
        return value;
    }

    public static TipoDeProceso fromValue(String v) {
        for (TipoDeProceso c: TipoDeProceso.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}

public enum TipoDeComite {


    EJECUTIVO_NACIONAL("Ejecutivo Nacional"),
    EJECUTIVO_ESTATAL("Ejecutivo Estatal");

    private final String value;

    TipoDeComite(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TipoDeComite fromValue(String v) {
        for (TipoDeComite c: TipoDeComite.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
*/