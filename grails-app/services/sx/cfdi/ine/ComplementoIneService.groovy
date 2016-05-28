package sx.cfdi.ine

import grails.transaction.Transactional
import sx.cfdi.Cfdi
import sx.cfdi.Comprobante

@Transactional
class ComplementoIneService {

    def cfdiService

    def save(ComplementoIne ine) {

        ine = ine.save failOnError:true, flush:true
        Cfdi cfdi = ine.cfdi
        Comprobante comprobante = cargarComplementoIne(cfdi,ine)
        cfdi = cfdiService.update(cfdi, comprobante)
        return ine
        //ine.save failOnError:true, flush:true
    }

    Comprobante cargarComplementoIne(Cfdi cfdi,ComplementoIne ine){

        Comprobante comprobante = cfdi.getComprobante()
        if(!comprobante.getComplemento()){
            comprobante.setComplemento(new Comprobante.Complemento())
        }
        comprobante.getComplemento().getAny().removeAll {it instanceof INE}
        /*
        comprobante.getComplemento().getAny().each {
            if(it instanceof INE){
                throw new RuntimeException('Ya se ha registrado un complemento INE a este CFDI')
            }
        }
        */
        sx.cfdi.ine.ObjectFactory factory = new sx.cfdi.ine.ObjectFactory()

        INE cn = factory.createINE()
        cn.setVersion('1.0')
        cn.setTipoComite(ine.tipoDeComite)
        cn.setTipoProceso(ine.tipoDeProceso)
        cn.setIdContabilidad(ine.contabilidad)

        ine.entidades.each { it ->
            INE.Entidad ie = factory.createINEEntidad()
            ie.setAmbito(it.ambito)
            ie.setClaveEntidad(it.clave)
            cn.getEntidad().add(ie)
        }

        comprobante.getComplemento().getAny().add(cn)
        return comprobante
    }

    def delete(ComplementoIne ine){
        Cfdi cfdi = ine.cfdi
        Comprobante comprobante = removerComplementoIne(cfdi,ine)
        cfdi.complementoIne = null
        cfdi = cfdiService.update(cfdi, comprobante)

    }

    def deleteEntidad(Entidad entidad){
        ComplementoIne ine = entidad.complementoIne
        ine.removeFromEntidades(entidad)
        save(ine)
    }

    Comprobante removerComplementoIne(Cfdi cfdi,ComplementoIne ine){

        Comprobante comprobante = cfdi.getComprobante()
        if(!comprobante.getComplemento()){
            comprobante.setComplemento(new Comprobante.Complemento())
        }
        comprobante.getComplemento().getAny().removeAll {it instanceof INE}

        return comprobante
    }
}
