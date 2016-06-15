package sx.cfdi.sw2

import grails.transaction.NotTransactional
import grails.transaction.Transactional
import groovy.sql.Sql
import org.springframework.jdbc.datasource.SingleConnectionDataSource
import sx.cfdi.Cfdi


class ExportadorService {


    def grailsApplication

    @NotTransactional
    def exportarCfdi(Cfdi cfdi) {
         // Buscar el CFDI en la base de datos de produccion
        def oldCfdi = buscarCfdi(cfdi.origen)
        //println 'Actualizando cfdi:'+oldCfdi

        def params = [
                cfdi.getTimbreFiscal().getAttribute('UUID'),
                cfdi.getTimbreFiscal().getAttribute('FechaTimbrado'),
                cfdi.getCadenaOriginal(),
                cfdi.xml.file,
                cfdi.getXml().getBytes(),
                cfdi.origen
                ]
        Sql sql = sql()
        def res = sql.executeUpdate(
                "UPDATE SX_CFDI SET UUID=?, TIMBRADO=?, CADENA_ORIGINAL=?, XML_FILE=?,  XML=? WHERE ORIGEN_ID=?"
                ,params)
    }


    @NotTransactional
    def buscarCfdi(String ventaId) {
        def row = sql().firstRow("select * from sx_cfdi  where origen_id = ?",[ventaId])
        return row
    }

    def sql 

    private Sql sql(){
        if(sql==null){
            def db=grailsApplication.config.sw2.db
            SingleConnectionDataSource ds=new SingleConnectionDataSource(
                driverClassName:'com.mysql.jdbc.Driver',
                url:db.url,
                username:db.username,
                password:db.password,
                suppressClose:true)
            sql=new Sql(ds)
        }
        return sql
    }
}
