<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <?php require_once 'inc/inclusion_bootstrap.php'; ?>
    </head>
    <body>
        <?php require_once 'inc/cabecera_vistas.php'; ?>

        <div class="container well">
            <h2>Bienvenido: <?php echo $usuario; ?></h2><br>
            <span id="nombreUsuario" class="sr-only"><?php echo $usuario; ?></span>
            <?php if ($_SESSION['rol'] == "Administrador"): ?>
            <div class="row">
                <div class="col-sm-6">
                    <img class="img-rounded img-responsive" style="margin-bottom: 15px" src="<?php echo base_url(); ?>assets/img/imagen_cancha.jpg" width="500">
                </div>
                <div id="panelNotificaciones" class="col-sm-6">
                    
                </div>
            </div>
            <?php endif; ?>
            <?php if ($_SESSION['rol'] == "Cliente"): ?>
            <div class="row">
                <div class="col-sm-12">
                    <img class="img-rounded" style="margin-bottom: 15px" src="<?php echo base_url(); ?>assets/img/imagen_cancha.jpg" width="1100" height="100">
                </div>
            </div>
            <div class="row">
                
                
                <div class="col-sm-12">
                    <?php require_once ('inc/mensaje_alerta.php');?>
                        <?php require_once 'plantillas/reservas_usuario.php'; ?>
                    
                </div>
                
            </div>
            <?php endif; ?>
            <div class="row">

            </div>
            <?php require_once 'inc/inclusion_jquery.php'; ?>
            <script src="<?php echo base_url(); ?>assets/js/notificaciones.js"></script>
    </body>
</html>