<!doctype html>
<html>
<head>
    <title>Alta de complemento INE</title>
    <asset:javascript src="forms/forms.js"/>
</head>
<body>

<div class="row wrapper border-bottom white-bg page-heading">
    <div class="col-lg-10">
        <h2>Complemento INE para CFDI</h2>
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
                                    <i class="fa fa-back">Regresar a CFDI</i>
                                </g:link>
                                <button id="saveBtn" class="btn btn-primary ">
                                    <i class="fa fa-floppy-o"></i> Salvar
                                </button>
                            </div>
                        </div>

                    </g:form>
                </div>

            </div>
        </div>
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

