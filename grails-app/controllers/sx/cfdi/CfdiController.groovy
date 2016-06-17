package sx.cfdi

class CfdiController {

    def importadorDeSiipapService

    def cfdiService

    def cadenaBuilder 

    def index() {
        /*
        def periodo=session.periodo
        def list=Cfdi.findAll(
                "from Cfdi c where date(c.fecha) between ? and ? order by c.dateCreated desc",
                [periodo.fechaInicial,periodo.fechaFinal])
          */
        params.sort = 'folio'
        def tipo = params.tipo ?:'ingreso'
        def list = Cfdi.where{
            tipo == tipo
        }.list(params)
        [cfdiInstanceList:list,tipo:tipo,titulo:tipo.capitalize()]
    }

    def show(Cfdi cfdi) {
        if(cfdi==null){
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'cfdiInstance.label', default: 'Cfdi'), params.id])
            redirect action: "index", method: "GET"
        }
        def uri=request.getHeader('referer')
        def cadena = cadenaBuilder.generarCadena(cfdi.getComprobante())
        [cfdiInstance:cfdi,origen:uri,cadena:cadena]
    }

    def mostrarXml(Cfdi cfdi){
        render(text: cfdi.xml.text, contentType: "text/xml", encoding: "UTF-8")
    }

    def descargarXml(Cfdi cfdi){
        if(cfdi==null){
            println "Este cfdi es nulo"
            notFound()
            return
        }
        def name = "${cfdi.receptorRfc}_${cfdi.uuid?:cfdi.id}"
        response.setContentType("application/octet-stream")
        response.setHeader("Content-disposition", "attachment; filename=\"$name\"")
        response.outputStream << cfdi.xml.newInputStream()

    }

    def timbrar(Cfdi cfdi){
        if(cfdi==null){
            println "Este cfdi es nulo"
            notFound()
            return
        }
        cfdi = cfdiService.timbrar(cfdi)
        redirect action: "show", id:cfdi.id
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'cfdi.label', default: 'Cfdi'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }

    def importar(ImportacionDeSiipapCommand command){
        if(command==null){
            println "Parametros de importación incorrectos"
            notFound()
            return
        }
        
        Cfdi found = Cfdi.where {
            serie == command.serie && folio == command.folio
        }.find()

        if(found) {
            flash.message = "Cfdi ya importado"
            redirect action: "show", id:found.id
            return
        }
        Cfdi cfdi = cfdiService.importarCfdi(command.serie,command.folio,command.claveCliente)
        redirect action: "show", id:cfdi.id

    }
}

class ImportacionDeSiipapCommand {

    String serie
    String folio
    String claveCliente
    //Date fecha

}
