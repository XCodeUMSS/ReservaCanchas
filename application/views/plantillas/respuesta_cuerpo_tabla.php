
<?php
/**
 * Plantilla para las peticiones ajax del cliente
 */
?>

<?php foreach ($reservas as $reserva): ?>
    <tr class="success">
        <td><?php echo $reserva->nombre; ?></td>
        <td><?php echo $reserva->telefono; ?></td>
        <td><?php echo $reserva->campo; ?></td>
        <td><?php echo $reserva->fecha; ?></td>
        <td><?php echo $reserva->horaInicio; ?></td>
        <td><?php echo $reserva->horaFin; ?></td>
    </tr>
<?php endforeach; ?>

