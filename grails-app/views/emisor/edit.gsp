<html>
<head>
    <meta charset="UTF-8">
    <meta name="layout" content="createForm"/>
    <title>${emisorInstance.clave}</title>
</head>
<body>

<content tag="header">
    <h3>Emisor ${emisorInstance.nombre}</h3>
</content>

<content tag="operaciones">
    <ul class="nav nav-pills nav-stacked">
        <li><g:link action="index">
            <span class="glyphicon glyphicon-arrow-left"></span> Catálogo
        </g:link>
        </li>

    </ul>
</content>

<content tag="formTitle">Datos generales</content>

<content tag="form">

    <g:hasErrors bean="${emisorInstance}">
        <div class="alert alert-danger">
            <g:renderErrors bean="${emisorInstance}" as="list" />
        </div>
    </g:hasErrors>

    <g:form class="form-horizontal" action="update" method="PUT" >
        <g:hiddenField name="id" value="${emisorInstance.id}"/>
        <g:hiddenField name="version" value="${emisorInstance.version}"/>

        <f:with bean="${emisorInstance }">
            <f:field property="nombre" widget-class="form-control uppercase-field " widget-autofocus="on"/>
            <f:field property="rfc" widget-class="form-control uppercase-field"/>
            <f:field property="clave" widget-class="form-control uppercase-field"/>
            %{-- <f:field property="llavePrivada" widget-class="form-control uppercase-field"/> --}%
            <g:render template="/common/direccion" bean="${emisorInstance}"/>
            <legend>Timbrado</legend>
            <f:field property="usuarioPac" widget-class="form-control" cols="col-sm-4"/>
            <f:field property="passwordPac" widget-class="form-control" cols="col-sm-4"/>
            <f:field property="passwordPfx" widget-class="form-control" cols="col-sm-4"/>
            <f:field property="timbradoDePrueba" widget-class="form-control" cols="col-sm-4"/>
        </f:with>

        <div class="form-group">
            <div class="col-sm-offset-9 col-sm-2">
                <button type="submit" class="btn btn-default">
                    <span class="glyphicon glyphicon-floppy-save"></span> Actualizar
                </button>
            </div>
        </div>

    </g:form>



    <legend>Criptografía  </legend>

    <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
        <div class="panel panel-default">
            <div class="panel-heading" role="tab" id="headingOne">
                <h4 class="panel-title">
                    <a data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
                        Llave privada
                    </a>
                </h4>
            </div>
            <div id="collapseOne" class="panel-collapse collapse " aria-labelledby="headingOne">
                <div class="panel-body">
                    <div class="form-group">
						<textarea name="llavePrivada" rows="8" cols="50" class="form-control" disabled>
							${emisorInstance.getPrivateKey()}

						</textarea>
                    </div>
                    <g:uploadForm class="form-inline" action="registrarLlavePrivada" id="${emisorInstance.id}">
                        <div class="form-group">
                            <label for="inputFile">Actualizar archivo</label>
                            <input type="file" name="file" autocomplete="off" class="form-control ">
                        </div>
                        <g:submitButton class="btn btn-primary" name="aceptar"value="Aceptar" />
                    </g:uploadForm>
                </div>
            </div>
        </div>


        <div class="panel panel-default">
            <div class="panel-heading" role="tab" id="headingTwo">
                <h4 class="panel-title">
                    <a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
                        Certificado digital
                    </a>
                </h4>
            </div>
            <div id="collapseTwo" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingTwo">
                <div class="panel-body">
                    <div class="form-group">
                        <label for="inputFile">Número de certificado</label>
                        <input type="text" name="text" autocomplete="off" class="form-control " disabled
                               value="${emisorInstance.numeroDeCertificado}">
                    </div>
                    <div class="form-group">
		        			<textarea name="certificadoDigital" rows="8" cols="50" class="form-control" disabled>
		        				${emisorInstance.getCertificado()}
		        			</textarea>
                    </div>
                    <g:uploadForm class="form-inline" action="registrarCertificado" id="${emisorInstance.id}">
                        <div class="form-group">
                            <label for="inputFile">Actualizar archivo</label>
                            <input type="file" name="file" autocomplete="off" class="form-control ">
                        </div>
                        <g:submitButton class="btn btn-primary" name="aceptar"value="Aceptar" />
                    </g:uploadForm>
                </div>
            </div>
        </div>

        <div class="panel panel-default">
            <div class="panel-heading" role="tab" id="headingTwo">
                <h4 class="panel-title">
                    <a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapse3">
                        Certificado PFX
                    </a>
                </h4>
            </div>
            <div id="collapse3" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingTwo">
                <div class="panel-body">
                    <div class="form-group">
		        			<textarea name="certificadoPfx" rows="8" cols="50" class="form-control" disabled>
		        				${emisorInstance.getCertificadoPfx()}
		        			</textarea>
                    </div>
                    <g:uploadForm class="form-inline" action="registrarCertificadoPfx" id="${emisorInstance.id}">
                        <div class="form-group">
                            <label for="inputFile">Actualizar archivo</label>
                            <input type="file" name="file" autocomplete="off" class="form-control ">
                        </div>
                        <g:submitButton class="btn btn-primary" name="aceptar"value="Aceptar" />
                    </g:uploadForm>
                </div>
            </div>
        </div>

    </div>

</content>

</body>
</html>