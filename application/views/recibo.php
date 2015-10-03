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
            <h2>XCode Recibo</h2><br>
            <div class="row">
                <div class="col-sm-6">
                    <img class="img-rounded img-responsive" src="<?php echo base_url(); ?>assets/img/imagen_cancha.jpg" width="600" height="">
                </div>
                <div class="col-sm-6">
                    <?php require_once 'inc/mensaje_alerta.php'; ?>
                </div>
                <div id="recibo" class="col-sm-6">
                    <h3 class="text-center">RECIBO # <?php echo $codigo?></h3>
                    <p class="text-right"><label>Fecha: </label><?php $datetime = new DateTime('now');
                    echo $datetime->format('d/m/y')?></p>
                    <p><label>Recibi de: </label> <?php echo $cliente; ?></p>
                    <p><label>La suma de: </label> <?php echo $precio_total; ?> Bolivianos</p>
                    <p><label>A concepto de: </label> Reserva</p>
                    <div class="table-responsive">
                        <table id="detalle" class="table table-bordered table-striped">
                            <thead>
                                <tr>
                                    <th>Detalle</th>
                                    <th>Precio</th>
                                </tr>
                            </thead>
                            <tbody id="cuerpo-tabla">
                                <?php if($confirmar){ ?>
                                    <tr>
                                        <td><?php echo $detalle; ?></td>
                                        <td><?php echo $precio_total; ?></td>
                                    </tr>
                                <?php } else {
                                    foreach ($reservas as $reserva): ?>
                                    <tr>
                                        <td><?php echo $reserva->detalle; ?></td>
                                        <td><?php echo $reserva->precio; ?></td>
                                    </tr>
                                <?php endforeach; }?>
                            </tbody>
                        </table>
                    </div>
                    <p><label>Total a pagar:</label> <?php echo $precio_total; ?> Bolivianos</p>
                    <p><label>Recibido por:</label>________________</p>
                </div>
                <div class="col-sm-6">
                    <input type="button" class="btn btn-primary" onclick="imprimir('recibo')" value="Imprimir recibo">
                </div>
            </div>
        </div>
        <?php require_once 'inc/inclusion_jquery.php'; ?>
        <style> #detalle{width: 500px; max-width: 600px}</style>
        <script src="<?php echo base_url(); ?>assets/js/imprimir.js"></script>
    </body>
</html>
