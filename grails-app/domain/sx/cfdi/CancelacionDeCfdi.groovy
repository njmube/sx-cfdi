package sx.cfdi

class CancelacionDeCfdi {

    Cfdi cfdi

    String comentario

    byte[] aka

    Date dateCreated
    Date lastUpdated

    //static belongsTo = [cfdi: Cfdi]

    static constraints = {
        comentario nullable:true
        aka maxSize:(1024 * 512)  // 50kb para almacenar el xml
    }
}
