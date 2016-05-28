<ul class="nav navbar-top-links navbar-right">
    <li>
        <g:link controller="home">
            <span class="m-r-sm text-muted welcome-message">SX-CFDI ${session?.empresa?.nombre}
        </span>
        </g:link>
        
    </li>

    <li class="dropdown">
        <a class="dropdown-toggle count-info" data-toggle="dropdown" href="#">
            <i class="fa fa-envelope"></i>
        </a>
        
        <ul class="dropdown-menu dropdown-messages">
            <li>
                <div class="dropdown-messages-box">
                    <div class="media-body">
                        <strong>No hay mensajes</strong>
                    </div>
                </div>
            </li>
            <li class="divider"></li>
        </ul>
    </li>

    <li class="dropdown">
        <a class="dropdown-toggle count-info" data-toggle="dropdown" href="#">
            <i class="fa fa-bell"></i>
        </a>
        <ul class="dropdown-menu dropdown-alerts">
            <li>
                <div>
                    No Hay alertas

                </div>
            </li>
            <li class="divider"></li>


        </ul>
    </li>

    
    <li>
        <g:form controller="logout" class="" >
            <button class="btn btn-w-m btn-link" type="submit" > <i class="fa fa-sign-out"></i></i> Cerrar sesión</button>
        </g:form>
        
    </li>
    <li>
        <a class="right-sidebar-toggle">
            <i class="fa fa-tasks"></i>
        </a>
    </li>
</ul>