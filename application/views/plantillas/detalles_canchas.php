
<div class="container well">
    <?php foreach ($canchas as $cancha):?>
    <div class="row campo">
        <div class="col-sm-6">
            <h3><?php echo $cancha->nombre; ?></h3>
            <label>Precio por Hora:</label> <?php echo $cancha->precio; ?><br>
            <label>Tipo de Cancha :</label><?php echo $cancha->tipoCancha; ?><br>
            <label>Tipo de Suelo :</label><?php echo $cancha->tipoSuelo; ?><br>
            <label>Hora Inicio Atencion :</label><?php echo $cancha->horaInicio; ?><br>
            <label>Hora Fin Atencion :</label><?php echo $cancha->horaFin; ?>


            <p><a href="<?php echo base_url();?>index.php/ControladorPrereservas/index?cod=<?php echo $cancha->id; ?>" class="btn btn-primary">Realizar Preserva >></a></p>
        </div>
        <div class=" col-sm-6 clearfix">
            <img class="img-rounded img-responsive" src="<?php echo (base_url().$cancha->imagen); ?>" width="250" height="150">
        </div>

    </div>
     <?php endforeach;?>
</div>

