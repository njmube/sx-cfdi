<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Cfdi ${cfdiInstance.folio}</title>
</head>
<body>

    <div class="row wrapper border-bottom white-bg page-heading">
    <div class="col-lg-10">
        <h2>Emisor:${cfdiInstance.emisor} (${cfdiInstance.emisorRfc})</h2>
        <ol class="breadcrumb">
            <li><g:link action="index">Facturas</g:link></li>
            <li><g:link action="show" id="${cfdiInstance.id}"><strong>Edición</strong></g:link></li>
        </ol>
    </div>
</div>

    <div class="row wrapper wrapper-content animated fadeInRight">


        <div class="col-lg-10">
            <div class="ibox float-e-margins">
                <div class="ibox-content text-center p-md">
                    <table id="grid" class="table table-striped table-hover table-bordered table-condensed">
                        <thead>
                        <tr>
                            <th>Propiedad</th>
                            <th>Valor</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>Receptor</td>
                            <td>${cfdiInstance.receptor} (${cfdiInstance.receptorRfc})</td>
                        </tr>
                        <tr>
                            <td>Fecha</td>
                            <td><g:formatDate date="${cfdiInstance.fecha}" format="dd/MM/yyyy"/></td>
                        </tr>
                        <tr>
                            <td>Serie</td>
                            <td>${fieldValue(bean:cfdiInstance,field:"serie")}</td>
                        </tr>
                        <tr>
                            <td>Folio</td>
                            <td>${fieldValue(bean:cfdiInstance,field:"folio")}</td>
                        </tr>
                        <tr>
                            <td>Tipo</td>
                            <td>${fieldValue(bean:cfdiInstance,field:"tipo")}</td>
                        </tr>
                        <tr>
                            <td>Timbrado</td>
                            <td>${cfdiInstance?.getTimbreFiscal()?.getAttribute('FechaTimbrado')}</td>
                        </tr>
                        <tr>
                            <td>UUID</td>
                            <td>${cfdiInstance?.getTimbreFiscal()?.getAttribute('UUID')}</td>
                        </tr>
                        <tr>
                            <td>Comentario</td>
                            <td>${fieldValue(bean:cfdiInstance,field:"comentario")}</td>
                        </tr>

                        </tbody>
                    </table>

                    <div class="">

                        <g:link class="btn btn-primary btn-outline " action="imprimirCfdi" id="${cfdiInstance.id}">
                            <span class="fa fa-print"> Imprimir</span>
                        </g:link>

                        <g:link class="btn btn-info btn-outline " action="mostrarXml" resource="${cfdiInstance}">
                            <i class="fa fa-code"></i>XML
                        </g:link>

                        <g:link class="btn btn-success btn-outline " action="descargarXml" resource="${cfdiInstance}">
                            <span class="glyphicon glyphicon-cloud-download"> Descargar</span>
                        </g:link>

                        <a href="#enviarCorreoForm" data-toggle="modal" class="btn btn-success btn-outline ">
                            <span class="glyphicon glyphicon-envelope"></span> Enviar
                        </a>

                        <g:if test="${cfdiInstance.cancelacion}">
                            <g:link  action="mostrarAcuse" controller="cancelacionDeCfdi"
                                     id="${cfdiInstance.cancelacion.id}"
                                     class="btn btn-default " >
                                </span> Acuse
                            </g:link>
                            <g:link  action="descargarAcuseXml" controller="cancelacionDeCfdi"
                                     id="${cfdiInstance.cancelacion.id}"
                                     class="btn btn-default " >
                                </span> Descargar acuse
                            </g:link>
                        </g:if>

                        <g:else>
                            <g:if test ="${!cfdiInstance.timbrado}">

                                <a  class="btn btn-warning btn-outline " data-toggle="modal" data-target="#timbrarDialog">
                                    <i class="fa fa-bell"></i> Timbrar
                                </a>
                                <g:if test="${cfdiInstance.complementoIne}">
                                    <g:link  action="ine" controller="complementoIne" action="edit"
                                             id="${cfdiInstance.complementoIne.id}"
                                             class="btn btn-primary btn-outline " >
                                        </span> Complemento INE
                                    </g:link>
                                </g:if>
                                <g:else>
                                    <g:link  action="ine" controller="complementoIne" action="create"
                                             id="${cfdiInstance.id}"
                                             class="btn btn-primary btn-outline " >
                                        </span> Complemento INE
                                    </g:link>
                                </g:else>

                            </g:if>
                            <g:else>
                                <g:link  action="cancelar" class="btn btn-default "
                                         onclick="return confirm('Cancelar CFDI?');" id="${cfdiInstance.id }">
                                    <span class="glyphicon glyphicon-remove-circle"></span> Cancelar
                                </g:link>
                            </g:else>
                        </g:else>
                    </div>
                </div>
            </div>
            <%--
            <g:if test ="${cfdiInstance.getTimbreFiscal()}">
                <div class="alert">
                    Timbre: ${cfdiInstance.getTimbreFiscal()}
                </div>
            </g:if>
            --%>
        </div>

    </div>

    <div class="modal fade" id="timbrarDialog" tabindex="-1">
        <div class="modal-dialog ">
            <div class="modal-content">
                <g:form action="timbrar" class="form-horizontal" method="DELETE">
                    <g:hiddenField name="id" value="${cfdiInstance.id}"/>

                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal"
                                aria-hidden="true">&times;</button>
                        <h4 class="modal-title" id="myDeleteModalLabel">Timbrar CFDI: ${cfdiInstance.id}</h4>
                    </div>
                    <div class="modal-body">
                        <p><small>${cfdiInstance}</small></p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
                        <g:submitButton class="btn btn-warning" name="aceptar" value="Timbrar" />
                    </div>
                </g:form>
            </div><!-- moda-content -->

        </div><!-- modal-di -->

    </div>


</body>
</html>