<ul class="nav metismenu" id="side-menu">
	<li class="nav-header">
		<div class="dropdown profile-element">
			<span>
                <img src="http://placehold.it/54x54&text=Foto" class="img-circle" absolute="false">
            </span>
        	<a data-toggle="dropdown" class="dropdown-toggle" href="#">
        	    <span class="clear"> 
        	    	<span class="block m-t-xs"> 
        	    		<strong class="font-bold">
                            ${applicationContext?.springSecurityService?.getCurrentUser()?.nombres}
                        </strong>
        	        </span> 
        	    	<span class="text-muted text-xs block">Sistemas <b class="caret"></b>
        	    	</span> 
        		</span>
        	</a>
        	<ul class="dropdown-menu animated fadeInRight m-t-xs">
                <li>
                    <g:link action="edit" controller="perfil">
                        <i class="fa fa-user-secret"></i> Perfil
                    </g:link>
                </li>
                <li><a href="mailbox.html">Mensajes</a></li>
                <li><a href="contacts.html">Notas</a></li>
                <li class="divider"></li>
                <li><a href="login.html">Salir </a></li>
			</ul>
		</div>
		<div class="logo-element">
			LX
		</div>
	</li>
	<li class="">
        <a href="index.html">
        	<i class="fa fa-tachometer"></i> <span class="nav-label">Tableros</span> <span class="fa arrow"></span>
        </a>
        <ul class="nav nav-second-level collapse">
            <li class="active">
                <g:link action="homeDashboard" controller="home">
                    <i class="fa fa-area-chart"></i> Inicio
                </g:link>
            </li>
            <li ><a href="index.html">Compras</a></li>
            <li><a href="dashboard_2.html">Ventas</a></li>
            <li><a href="dashboard_3.html">Tesoreria</a></li>
        </ul>
	</li>
    <li class="active">
        <g:link action="index" controller="cfdi">
            <i class="fa fa-area-chart"></i> Facturas
        </g:link>
    </li>
	<li>


	</li>


    
</ul>