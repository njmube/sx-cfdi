
<!DOCTYPE html>
<html>
<head>
    <title>Emisores</title>
    <meta name="layout" content="operaciones">
</head>
<body>

<content tag="header">
    Catálogo de emisores de  CFDIs
</content>

<content tag="periodo">

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
            <th>Id</th>
            <th>Nombre</th>
            <th>RFC</th>
            <th>Modificado</th>
        </tr>
        </thead>
        <tbody>
        <g:each in="${cfdiInstanceList}" status="i" var="cfdiInstance">
            <tr id="${row.id}">
                <td >
                    <g:link  action="edit" id="${row.id}">
                        ${fieldValue(bean:row,field:"id")}
                    </g:link>
                </td>
                <td >
                    <g:link  action="edit" id="${row.id}">
                        ${fieldValue(bean:row,field:"nombre")}
                    </g:link>
                </td>
                <td>${fieldValue(bean:row,field:"rfc")}</td>
                <td><g:formatDate date="${row.lastUpdated}" format="dd/MM/yyyy HH:mm"/></td>
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
                            <f:field property="serie" wrapper="bootstrap3"  widget-class="form-control" />
                            <f:field property="folio" wrapper="bootstrap3"  widget-class="form-control"/>
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



</body>
</html>

