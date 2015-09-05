<header role="navigation" class="navbar navbar-default navbar-inverse">
    <div class="container">
        <div class="navbar-header">
            <img src="<?php echo base_url(); ?>assets/img/logo_app.png" width="40" height="40">
            <button type="button" data-target="#navbarCollapse" data-toggle="collapse" class="navbar-toggle">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>

            <a href="#" class="btn" style="padding-top: 14px;">XCodeRC</a>
        </div>

        <div id="navbarCollapse" class="collapse navbar-collapse navbar-right">
            <ul class="nav navbar-nav">
                <li class="active"><a href="#">Inicio</a></li>
                <li><a href="<?php echo base_url();?>index.php/Welcome/index">Canchas</a></li>
                <li><a href="<?php echo base_url();?>index.php/ControladorReserva/index">Reservar</a></li>
                <li><a href="<?php echo base_url();?>index.php/ControladorReservaEspecial/index">Reserva Especial</a></li>
                <li><a href="<?php echo base_url();?>index.php/ControladorPrereservas/mostrarDetallesCanchas">Prereservas</a></li>
            </ul>
        </div>
    </div>

</header>