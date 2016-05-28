import sx.cfdi.CadenaBuilder
import sx.cfdi.Sellador
import sx.cfdi.Timbrador

// Place your Spring DSL code here
beans = {
    cadenaBuilder(CadenaBuilder){}
    sellador(Sellador)
    timbrador(Timbrador)
}
