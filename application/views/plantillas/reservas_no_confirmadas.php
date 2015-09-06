<div id="reservas_no_confirmadas">
    <?php foreach ($reservas as $reserva): ?>
        <div id="panel" class="panel panel-primary">
            <div class="panel-heading">Prereserva: <?php echo $reserva->nombre; ?></div>
            <div class="panel-body">

                <label>Campo Deportivo : </label> <?php echo $reserva->campo; ?> <br>
                <label>Horario : </label><?php echo $reserva->horaInicio; ?>  -  <?php echo $reserva->horaFin; ?> <br>
                <label>Fecha : </label> <?php echo $reserva->fecha; ?> <br>
                <label>Costo : </label> <?php echo $reserva->precio; ?> <br>
                <label>Telefono : </label> <?php echo $reserva->telefono; ?> <br>
                <label>Fecha Limite Pago : </label> <?php echo $reserva->fechaExpiracion; ?> <br>
                <label>Hora Limite Pago : </label> <?php echo $reserva->horaExpiracion; ?> <br>

                <a href="<?php echo base_url(); ?>index.php/ControladorPrereservas/mostrarFormulario" class="btn btn-primary">Confirmar Prereserva >></a>
            </div>
        </div>
    <?php endforeach; ?>
</div>
