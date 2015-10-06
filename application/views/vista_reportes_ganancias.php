<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
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
            <h2 style="padding: 0px; margin: 0px; border: 0px">XCode Grafico Reporte de Ganancias<small> Bolivianos vs Mes</small></h2><br>
            
            <div class="row">
                <div class="col-sm-12">
                    <canvas id="canvas" height="350" width="1100"></canvas>
                </div>
                <div class="col-md-12">
                    <h3>Total : <span id="total"></span></h3>
                </div>
                <?php require_once 'plantillas/reporte_ganancias.php'; ?>
                
            </div>
        </div>
        
        <?php require_once 'inc/inclusion_jquery.php'; ?>
        <script src="<?php echo base_url(); ?>assets/js/Chart.min.js"></script>
        <script src="<?php echo base_url(); ?>assets/js/graficos_reporte_ganancias.js"></script>
    </body>
</html>
