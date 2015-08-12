<!DOCTYPE html>

<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <?php require_once 'inc/inclusion_bootstrap.php'; ?>
    </head>
    <body>
        <?php require_once 'inc/cabecera_vistas.php'; ?>
        <div class="container well">
            <h2>XCode Realizar Reserva Especial</h2><br>
            <div class="row">
                <div class="col-sm-6">
                    <img class="img-rounded img-responsive" src="<?php echo base_url(); ?>assets/img/imagen_cancha.jpg" width="500" style="height: 280px;">
                </div>

                <div class="col-sm-6">
                    <form class="form-horizontal" enctype="multipart/form-data" method="POST" action="<?php echo base_url(); ?>index.php/ControladorReservaEspecial/reservar">
                        <div class="form-group">
                            <label class="control-label col-sm-3">Evento :</label>
                            <div class="col-sm-9">
                                <select class="form-control" name="nombre_evento">
                                    <?php foreach ($eventos as $evento): ?>
                                        <option value="<?php echo $evento->id; ?>"><?php echo $evento->nombre; ?></option>
                                    <?php endforeach; ?>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-3">Cancha :</label>
                            <div class="col-sm-9">
                                <select class="form-control" name="campo_deportivo">
                                    <?php foreach ($canchas as $cancha): ?>
                                        <option value="<?php echo $cancha->id; ?>"><?php echo $cancha->nombre; ?></option>
                                    <?php endforeach; ?>
                                </select>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="control-label col-sm-3">Fecha : </label>
                            <div class="col-sm-9">
                                <div class="bfh-datepicker" data-name="fecha_reserva" id="fecha_reserva"></div>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="control-label col-sm-3">Horario Inicio:</label>
                            <div class="col-sm-3">
                                <div class="bfh-timepicker" data-name="hora_inicio" data-time="08:00" id="horario_inicio">
                                </div>
                            </div>
                            <label class="control-label col-sm-3">Horario Fin:</label>
                            <div class="col-sm-3">
                                <div class="bfh-timepicker" data-name="hora_fin" data-time="09:00" id="horario_fin" data-align='right'>
                                </div>
                            </div>
                        </div>

                        <div class="form-group">
                            <div class="col-sm-offset-3 col-sm-9">
                                <input type="submit" class="btn btn-primary" value="Reservar">
                                <input type="reset" class="btn btn-default" value="Limpiar">
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <?php require_once '/tabla_reserva.php'; ?>
        </div>
        <?php require_once 'inc/inclusion_jquery.php'; ?>
    </body>
</html>