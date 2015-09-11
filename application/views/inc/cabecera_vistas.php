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
                <li class="active"><a href="<?php echo base_url(); ?>index.php/">Inicio</a></li>
                <?php foreach ($menus as $menu): ?>
                    <?php if ($menu->nombre == "Campos Deportivos"): ?>
                        <li><a href="<?php echo $menu->url ?>"><?php echo $menu->nombre ?></a></li>
                    <?php endif; ?>

                <?php endforeach; ?>
                <li class="dropdown">
                    <a data-toggle="dropdown" class="dropdown-toggle" href="#">Gestion Reservas <b class="caret"></b></a>
                    <ul role="menu" class="dropdown-menu">
                        <?php foreach ($menus as $menu): ?>
                            <?php if ($menu->nombre != "Campos Deportivos"): ?>
                                <li><a href="<?php echo $menu->url ?>"><?php echo $menu->nombre ?></a></li>
                            <?php endif; ?>

                        <?php endforeach; ?>
                    </ul>
                </li>
                <li><a href="<?php echo base_url(); ?>index.php/Welcome/cerrar_sesion">Cerrar Sesion</a></li>
            </ul>
        </div>
    </div>

</header>