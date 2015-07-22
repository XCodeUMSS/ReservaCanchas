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
            <h2>XCode Agregar Cancha Deportiva</h2><br>
            <div class="row">
                <div class="col-sm-6">
                    <img class="img-rounded img-responsive" src="<?php echo base_url(); ?>assets/img/imagen_cancha.jpg" width="500" height="">
                </div>

                <div class="col-sm-6">
                    <form class="form-horizontal" enctype="multipart/form-data" method="POST" action="<?php echo base_url();?>index.php/Welcome/agregarCampo">
                        <div class="form-group">
                            <label class="control-label col-sm-3">Nombre:</label>
                            <div class="col-sm-9">
                                <input type="text" required class="form-control" name="nombre_cancha" id="nombre_cancha" placeholder="Nombre de la cancha">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-3">Imagen:</label>
                            <div class="col-sm-9">
                                <input type="file" accept="image/*" style="padding-left: 0px;"class="btn" name="imagen" id="imagen">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="control-label col-sm-3">Precio/Hora:</label>
                            <div class="col-sm-9">
                                <input type="number" min="10" required class="form-control" name="precio_hora" id="precio_hora">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="control-label col-sm-3">Tipo de Cancha</label>
                            <div class="col-sm-9">
                                <select class="form-control" name="tipo_cancha">
                                    <?php foreach ($tiposCancha as $tipoCancha): ?>
                                    <option value="<?php echo $tipoCancha->IdTipoCancha; ?>"><?php echo $tipoCancha->Nombre; ?></option>
                                    <?php endforeach;?>
                                </select>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="control-label col-sm-3">Tipo de Suelo</label>
                            <div class="col-sm-9">
                                <select class="form-control" name="tipo_suelo">
                                    <?php foreach ($tiposSuelo as $tipoSuelo): ?>
                                    <option value="<?php echo $tipoSuelo->IdTipoSuelo; ?>"><?php echo $tipoSuelo->Nombre; ?></option>
                                    <?php endforeach;?>
                                </select>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="control-label col-sm-3">Horario Inicio:</label>
                            <div class="col-sm-3">
                                <input type="time" required class="form-control" name="hora_inicio" id="horario_inicio">
                            </div>
                            <label class="control-label col-sm-3">Horario Fin:</label>
                            <div class="col-sm-3">
                                <input type="time" required class="form-control" name="hora_fin" id="horario_fin">
                            </div>
                        </div>

                        <div class="form-group">
                            <div class="col-sm-offset-3 col-sm-9">
                                <input type="submit" class="btn btn-primary" value="Agregar">
                                <input type="reset" class="btn btn-default" value="Limpiar">
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <?php require_once '/tabla_cancha.php'; ?>
        </div>
        <?php require_once 'inc/inclucion_jquery.php'; ?>
    </body>
</html>