package sx.cfdi

import grails.transaction.Transactional

/**
 * Created by rcancino on 30/05/16.
 */
class EmisorController {


    def index(Integer max) {
        respond Emisor.list(params), model:[emisorCount: Emisor.count()]
    }

    def show(Emisor emisor) {
        respond emisor
    }

    def create() {
        respond new Emisor(params)
    }

    @Transactional
    def save(Emisor emisor) {
        if (emisor == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (emisor.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond emisor.errors, view:'create'
            return
        }

        emisor.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'emisor.label', default: 'Emisor'), emisor.id])
                redirect emisor
            }
            '*' { respond emisor, [status: CREATED] }
        }
    }

    def edit(Emisor emisor) {
        respond emisor
    }

    @Transactional
    def update(Emisor emisor) {
        if (emisor == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (emisor.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond emisor.errors, view:'edit'
            return
        }

        emisor.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'emisor.label', default: 'Emisor'), emisor.id])
                redirect emisor
            }
            '*'{ respond emisor, [status: OK] }
        }
    }

    @Transactional
    def delete(Emisor emisor) {

        if (emisor == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        emisor.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'emisor.label', default: 'Emisor'), emisor.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'emisor.label', default: 'Emisor'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }


}
