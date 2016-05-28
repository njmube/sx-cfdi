package sx.cfdi

class CfdiController {

    def cfdiService

    def index() {
        /*
        def periodo=session.periodo
        def list=Cfdi.findAll(
                "from Cfdi c where date(c.fecha) between ? and ? order by c.dateCreated desc",
                [periodo.fechaInicial,periodo.fechaFinal])
          */
        [cfdiInstanceList:Cfdi.list(params)]
    }

    def show(Cfdi cfdi) {
        if(cfdi==null){
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'cfdiInstance.label', default: 'Cfdi'), params.id])
            redirect action: "index", method: "GET"
        }
        def uri=request.getHeader('referer')
        [cfdiInstance:cfdi,origen:uri]
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
}
