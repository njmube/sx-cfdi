package sx.cfdi.ine

import sx.cfdi.Cfdi

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class ComplementoIneController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def complementoIneService

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond ComplementoIne.list(params), model:[complementoIneCount: ComplementoIne.count()]
    }

    def show(ComplementoIne complementoIne) {
        respond complementoIne
    }

    def create(Cfdi cfdi) {
        respond new ComplementoIne(cfdi:cfdi)
    }

    @Transactional
    def save(ComplementoIne complementoIne) {
        if (complementoIne == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }
        //println 'Salvando params: '+params
        if (complementoIne.hasErrors()) {
            transactionStatus.setRollbackOnly()
            //println 'Errores de validacion: '+complementoIne.errors
            respond complementoIne.errors, view:'create', model:[complementoIne:complementoIne]
            return
        }

        //complementoIne.save flush:true
        complementoIne = complementoIneService.save(complementoIne)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'complementoIne.label', default: 'ComplementoIne'), complementoIne.id])
                redirect action:'edit',id:complementoIne.id
            }
            '*' { respond complementoIne, [status: CREATED] }
        }
    }

    def edit(ComplementoIne complementoIne) {
        respond complementoIne
    }

    @Transactional
    def update(ComplementoIne complementoIne) {
        if (complementoIne == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (complementoIne.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond complementoIne.errors, view:'edit'
            return
        }

        complementoIne = complementoIneService.save(complementoIne)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'complementoIne.label', default: 'ComplementoIne'), complementoIne.id])
                redirect complementoIne
            }
            '*'{ respond complementoIne, [status: OK] }
        }
    }

    @Transactional
    def agregarEntidad(Entidad entidad) {
        if (entidad == null) {
            notFound()
            return
        }

        if (entidad.hasErrors()) {
            flash.message="Errores de validacion al tratar de insertar entidad"
            redirect  action:'edit',id:entidad.complementoIne.id
            return
        }
        ComplementoIne complementoIne=entidad.complementoIne
        //complementoIne.addToEntidades(clave:entidad.clave,ambito:entidad.ambito)
        complementoIne = complementoIneService.save(complementoIne)
        redirect  action: 'edit', id: complementoIne.id
    }

    @Transactional
    def delete(ComplementoIne complementoIne) {

        if (complementoIne == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        complementoIneService.delete(complementoIne)
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'complementoIne.label', default: 'ComplementoIne'), complementoIne.id])
                redirect controller:'cfdi', action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    @Transactional
    def eliminarEntidad(Entidad entidad){
        if (entidad == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        def complementoIne = entidad.complementoIne
        assert complementoIne
        complementoIneService.deleteEntidad(entidad)
        redirect  action: 'edit', id: complementoIne.id
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'complementoIne.label', default: 'ComplementoIne'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
