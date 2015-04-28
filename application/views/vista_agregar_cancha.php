<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <?php require_once 'inc/inclusion_bootstrap.php';?>
    </head>
    <body>
        <?php require_once 'inc/cabecera_vistas.php';?>

        <div class="container well">
            <h2>XCode Agregar Cancha Deportiva</h2><br>
            <div class="row">
                <div class="col-sm-6">
                    <img class="img-rounded img-responsive" src="<?php base_url(); ?>assets/img/imagen_cancha.jpg" width="500" height="">
                </div>
                
                <div class="col-sm-6">
                    <form class="form-horizontal">
                        <div class="form-group">
                            <label class="control-label col-sm-3">Nombre:</label>
                            <div class="col-sm-9">
                                <input type="text" required class="form-control" id="nombre_cancha" placeholder="Nombre de la cancha">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-3">Imagen:</label>
                            <div class="col-sm-9">
                                <input type="file" required style="padding-left: 0px;"class="btn" id="imagen_archivo">
                            </div>
                        </div>
						
						<div class="form-group">
                            <label class="control-label col-sm-3">Precio/Hora:</label>
                            <div class="col-sm-9">
                                <input type="number" required class="form-control" id="precio_hora">
                            </div>
                        </div>
                        
                        <div class="form-group">
                            <label class="control-label col-sm-3">Tipo de Cancha</label>
                            <div class="col-sm-9">
                                <select class="form-control">
                                    <option>Tenis</option>
                                    <option>Futsal</option>
                                    <option>Futbol</option>
                                    <option>Basquet</option>
                                </select>
                            </div>
                        </div>
                        
                        <div class="form-group">
                            <label class="control-label col-sm-3">Tipo de Suelo</label>
                            <div class="col-sm-9">
                                <select class="form-control">
                                    <option>Cesped</option>
                                    <option>Pavimento</option>
                                    <option>Madera</option>
                                </select>
                            </div>
                        </div>
                        
                        <div class="form-group">
                            <label class="control-label col-sm-3">Horario Inicio:</label>
                            <div class="col-sm-3">
                                <input type="time" required class="form-control" id="horario_inicio">
                            </div>
                            <label class="control-label col-sm-3">Horario Fin:</label>
                            <div class="col-sm-3">
                                <input type="time" required class="form-control" id="horario_fin">
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
			
			<div class="col-md-12">
                    <h2>Lista de Canchas</h2>
					<div class="table-responsive">
						<table id="tabla_canchas" class="table table-bordered table-striped">
                        <thead>
                            <tr>
                                <th>Nombre</th>
                                <th>Archivo Imagen</th>
                                <th>Precio/Hora</th>
                                <th>Tipo Cancha</th> 
								<th>Tipo de Suelo</th>
                                <th>Hora Inicio</th>
								<th>Hora Fin</th>
                            </tr>
                        </thead>
                        
                        <tbody id="cuerpo-tabla-canchas">
                            
                        </tbody>
                    </table>
					</div>
                    
                </div>
        </div>

			
        


       <?php require_once 'inc/inclucion_jquery.php'; ?>
    </body>
</html>