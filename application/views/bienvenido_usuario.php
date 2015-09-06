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
            <h2>Bienvenido: <?php echo $usuario;?></h2><br>
            <div class="row">
                <div class="col-sm-6">
                    <img class="img-rounded img-responsive" src="<?php echo base_url(); ?>assets/img/imagen_cancha.jpg" width="500" height="">
                </div>
        </div>
        <?php require_once 'inc/inclusion_jquery.php'; ?>
    </body>
</html>