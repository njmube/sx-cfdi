<!doctype html>
<html>
<head>
    <title>Alta de complemento INE</title>
    <asset:javascript src="forms/forms.js"/>
</head>
<body>

<div class="row wrapper border-bottom white-bg page-heading">
    <div class="col-lg-10">
        <h2>Complemento INE para CFDI

        </h2>
        <ul>
            <li><h4>Serie: ${complementoIne.cfdi.serie} Folio: ${complementoIne.cfdi.folio}</h4></li>
            <li><h4>Receptor: ${complementoIne.cfdi.receptor}</h4></li>
        </ul>
        <ol class="breadcrumb">
            <li><g:link controller="cfdi" action="show" id="${complementoIne.cfdi.id}">Cfdi</g:link></li>
            <li class="active"><strong>Alta</strong></li>
        </ol>
    </div>
    <div class="col-lg-2">

    </div>
</div>



<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-lg-6">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>Alta de complemento</h5>
                    <div class="ibox-tools">
                        <a class="collapse-link">
                            <i class="fa fa-chevron-up"></i>
                        </a>
                        <a class="close-link">
                            <i class="fa fa-times"></i>
                        </a>
                    </div>

                </div>
                <div class="ibox-content">
                    <g:hasErrors bean="${complementoIne}">
                        <div class="alert alert-danger">
                            <ul class="errors" >
                                <g:renderErrors bean="${complementoIne}" as="list" />
                            </ul>
                        </div>
                    </g:hasErrors>

                    <g:form name="createForm" action="save" class="form-horizontal" method="POST">
                        <g:hiddenField name="cfdi" value="${complementoIne.cfdi.id}"/>
                        <f:with bean="${complementoIne}">
                            <f:field property="tipoDeProceso"
                                     wrapper="bootstrap3"  widget-class="form-control chosen-select"/>
                            <f:field property="tipoDeComite"
                                     wrapper="bootstrap3"  widget-class="form-control chosen-select"/>
                            <f:field property="contabilidad"
                                     widget-class="form-control numeric" wrapper="bootstrap3" widget-type="text"/>

                        </f:with>
                        <div class="form-group">
                            <div class="col-lg-offset-3 col-lg-10">
                                <g:link class="btn btn-default "
                                        controller="cfdi" action="show" id="${complementoIne.cfdi.id}">
                                    <i class="fa fa-angle-double-left" aria-hidden="true"></i> CFDI</i>
                                </g:link>
                                <a data-toggle="modal" data-target="#agregarEntidadDialog" data-requisicion="${complementoIne.id}"
                                   class="btn btn-default ">
                                    <i class="fa fa-plus"></i> Entidad
                                </a>
                                <button id="saveBtn" class="btn btn-primary ">
                                    <i class="fa fa-floppy-o"></i> Salvar
                                </button>
                                <a href="" class="btn btn-danger " data-toggle="modal" data-target="#deleteDialog"><i class="fa fa-trash"></i> Eliminar</a>
                            </div>
                        </div>

                    </g:form>
                </div>

            </div>
        </div>
        <div class="col-lg-6">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>Entidades</h5>
                    <div class="ibox-tools">
                        <a class="collapse-link">
                            <i class="fa fa-chevron-up"></i>
                        </a>
                        <a class="close-link">
                            <i class="fa fa-times"></i>
                        </a>
                    </div>
                </div>
                <div class="ibox-content">
                    <table id="grid" class="table table-striped table-hover table-bordered table-condensed">
                        <thead>
                        <tr>
                            <th>Clave</th>
                            <th>Ambito</th>
                            <th>Contabilidades</th>
                            <th></th>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${complementoIne.entidades}" status="i" var="entidad">
                            <tr>
                                <td>${fieldValue(bean: entidad, field: "clave")}</td>
                                <td>${fieldValue(bean: entidad, field: "ambito")}</td>
                                <td>${fieldValue(bean: entidad, field: "contabilidades")}</td>
                                <td>
                                    <g:link action="eliminarEntidad" id ="${entidad.id}"
                                        onClick="return confirm('Eliminar entidad?');">
                                        <i class="fa fa-trash"></i>
                                    </g:link>
                                </td>
                            </tr>
                        </g:each>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <div class="modal fade in" id="agregarEntidadDialog" tabindex="-1">
        <div class="modal-dialog ">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                            aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="myModalLabel">Agregar entidad a complemento INE:${complementoIne.id}</h4>
                </div>
                <g:form name="agregarForm" class="form-horizontal" action="agregarEntidad" >
                    <div class="modal-body">

                        <f:with bean="${new  sx.cfdi.ine.Entidad()}">
                            <g:hiddenField name="complementoIne.id" value="${complementoIne.id}"/>
                            <f:field property="clave"
                                     wrapper="bootstrap3"  widget-class="form-control "/>
                            <f:field property="ambito"
                                     wrapper="bootstrap3"  widget-class="form-control "/>
                            <f:field property="contabilidades"
                                     wrapper="bootstrap3"  widget-class="form-control "/>
                        </f:with>

                    </div>
                    <div class="modal-footer">
                        <button type="submit" class="btn btn-primary">
                            <i class="icon-ok icon-white"></i>
                            Agregar
                        </button>
                    </div>
                </g:form>
            </div>
            <!-- moda-content -->
        </div>
        <!-- modal-di -->
    </div>

    <div class="modal fade" id="deleteDialog" tabindex="-1">
        <div class="modal-dialog ">
            <div class="modal-content">
                <g:form action="delete" class="form-horizontal" method="DELETE">
                    <g:hiddenField name="id" value="${complementoIne.id}"/>

                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal"
                                aria-hidden="true">&times;</button>
                        <h4 class="modal-title" id="myDeleteModalLabel">Eliminar el registro ${complementoIne.id}</h4>
                    </div>
                    <div class="modal-body">
                        <p><small>${complementoIne}</small></p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
                        <g:submitButton class="btn btn-danger" name="aceptar" value="Eliminar" />
                    </div>
                </g:form>
            </div><!-- moda-content -->

        </div><!-- modal-di -->

    </div>



</div>





    <script type="text/javascript">

        $(function(){
            $('.chosen-select').chosen();
            $(".numeric").autoNumeric('init',{vMin:'0'},{vMax:'9999'});

            $('form[name=createForm]').submit(function(e){
                var button=$("#saveBtn");
                button.attr('disabled','disabled')
                .html('Procesando...');
                $(".numeric",this).each(function(index,element){
                    var val=$(element).val();
                    var name=$(this).attr('name');
                    var newVal=$(this).autoNumeric('get');
                    $(this).val(newVal);
                    console.log('Enviando elemento numerico con valor:'+name+" : "+val+ " new val:"+newVal);
                });
                //e.preventDefault();
                return true;
            });
	 	});
    </script>

</body>
</html>

