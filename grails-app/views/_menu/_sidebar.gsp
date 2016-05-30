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
        <a href="#"><i class="fa fa-th-large"></i> <span class="nav-label">Catálogos</span><span class="fa arrow"></span></a>

        <ul class="nav nav-second-level collapse">
            <li class="active">
                <g:link action="index" controller="emisor" >
                    <i class="fa fa-wifi"></i> Emisores
                </g:link>
            </li>
        </ul>
	</li>
    <li >
        <g:link action="index" controller="cfdi" params="[tipo:'ingreso']">
            <i class="fa fa-dollar"></i> Facturas
        </g:link>
    </li>
    <li class="">
        <g:link action="index" controller="cfdi" params="[tipo:'notas']">
            <i class="fa fa-minus-circle"></i> Notas de crédito
        </g:link>
    </li>
    <li class="">
        <g:link action="index" controller="cfdi" params="[tipo:'cargos']">
            <i class="fa fa-plus-circle"></i> Notas de cargo
        </g:link>
    </li>
    <li >
        <g:link action="index" controller="cfdi" params="[tipo:'traslado']">
            <i class="fa fa-exchange"></i> Traslados
        </g:link>
    </li>
    <li >
        <g:link action="index" controller="cfdi" params="[tipo:'pagos']">
            <i class="fa fa-money"></i> Comprobantes de pago
        </g:link>
    </li>
	<li>


	</li>


    
</ul>