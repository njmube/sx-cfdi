
<!DOCTYPE html>
<html>
<head>
    <title>CFDI s</title>
    <meta name="layout" content="operaciones">
    
</head>
<body>

<content tag="header">
    Comprobantes fiscales digitales CFDI (${titulo})
</content>

<content tag="periodo">
    Periodo:${session?.periodo?.mothLabel()}
</content>

<content tag="operaciones">
    <li>
        <a data-toggle="modal" data-target="#importarDialog"><i class="fa fa-upload"></i> Importar</a>
    </li>
</content>

<content tag="grid">
    <table id="grid" class="table table-striped table-hover table-bordered table-condensed">
        <thead>
        <tr>
            <th>Serie</th>
            <th>Folio</th>
            <th>Fecha</th>
            <th>Receptor</th>
            <th>Total</th>
            <th>UUID</th>
            <th>Timbrado</th>
        </tr>
        </thead>
        <tbody>
        <g:each in="${cfdiInstanceList}" status="i" var="cfdiInstance">
            <tr>
                <td>${fieldValue(bean: cfdiInstance, field: "serie")}</td>
                <td><g:link action="show" id="${cfdiInstance.id}">${fieldValue(bean: cfdiInstance, field: "folio")}</g:link></td>
                <td><g:formatDate date="${cfdiInstance.fecha}" format="dd/MM/yyyy"/></td>
                <td>${fieldValue(bean: cfdiInstance, field: "receptor")}</td>
                <td>${formatNumber(number:cfdiInstance.total,type:'currency')}</td>
                <td>${fieldValue(bean: cfdiInstance, field: "uuid")}</td>
                <td><g:formatDate date="${cfdiInstance.timbrado}" /></td>
            </tr>
        </g:each>
        </tbody>
    </table>

    <div class="modal fade" id="importarDialog" tabindex="-1">
        <div class="modal-dialog ">
            <div class="modal-content">
                <g:form action="importar" class="form-horizontal" >

                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal"
                                aria-hidden="true">&times;</button>
                        <h4 class="modal-title" id="myDeleteModalLabel">Importar CFDI de Siipap Win</h4>
                    </div>
                    <div class="modal-body">
                        <f:with bean="${new sx.cfdi.ImportacionDeSiipapCommand()}">
                            %{-- <f:field property="serie" wrapper="bootstrap3"  widget-class="form-control" /> --}%
                            <f:field property="fecha" wrapper="bootstrap3"  widget-class="form-control date"/>
                            <f:field property="folio" wrapper="bootstrap3"  widget-class="form-control"/>
                            <f:field property="claveCliente" wrapper="bootstrap3"  widget-class="form-control"/>
                        </f:with>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
                        <g:submitButton class="btn btn-warning" name="aceptar" value="Importar" />
                    </div>
                </g:form>
            </div><!-- moda-content -->
        </div><!-- modal-di -->
    </div>
    
</content>

<content tag="searchService">
    <g:createLink action="search"/>
</content>

</body>
</html>

