<div class="alert alert-info" role="alert">
    <h3><strong>Notificaciones</strong>: tiene <?php echo $numero; ?> prereservas por confirmar</h3>
</div>
<?php foreach ($reservas as $reserva): ?>
<div class="alert alert-info" role="alert">
    <a href="#" class="alert-link"><h4><?php echo $reserva->campo; ?></h4></a>
    <p>Esta cancha fue prereservada para el <strong><?php echo $reserva->fecha; ?></strong> <br></p>
    <p>por el usuario <strong><?php echo $reserva->nombre; ?></strong></p>
</div>
<?php endforeach; ?>
