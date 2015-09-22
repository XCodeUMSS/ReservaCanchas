<!DOCTYPE html>

<html>
    <head>
        <meta charset="UTF-8">
        <title></title><meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <?php require_once 'inc/inclusion_bootstrap.php'; ?>
        <style>
            #panel {
                margin-top: 20px;
            }
        </style>
    </head>
    <body>
        <?php require_once 'inc/cabecera_vistas.php'; ?>
        <div class="container well">
            <h3>Confirmar Prereserva</h3>
            <?php require_once 'inc/mensaje_alerta.php'; ?>
            <div class="input-group">
                <span class="input-group-addon" id="basic-addon1"><span class="glyphicon glyphicon-search" aria-hidden="true"></span>  Buscar  </span>
                <input type="text" id="nombre_usuario" class="form-control" placeholder="Nombre de Usuario" aria-describedby="basic-addon1">
            </div>
            <div id="reservas_no_confirmadas">
                <?php require_once 'plantillas/reservas_no_confirmadas.php'; ?>
            </div>
        </div>

    </div>

    <?php require_once 'inc/inclusion_jquery.php'; ?>
    <script src="<?php echo base_url(); ?>assets/js/reservas_no_cofirmadas.js"></script>
    <script src="<?php echo base_url(); ?>assets/js/notificaciones.js"></script>
</body>
</html>
