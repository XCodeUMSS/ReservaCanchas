<div class="col-md-12">
    <h2>Lista de Reservas</h2>
    <div class="row">
        <div class="col-sm-6">
            <div class="alert alert-info" role="alert">
                <span class="glyphicon glyphicon-ok-circle" aria-hidden="true"></span>
                <span class="sr-only">Error:</span>
                Reserva Confirmada
            </div>
        </div>

        <div class="col-sm-6">
            <div class="alert alert-danger" role="alert">
                <span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span>
                <span class="sr-only">Error:</span>
                Reserva no Confirmada
            </div>
        </div>
    </div>
    <div class="row">
        <div class="table-responsive">
            <table id="tabla_canchas" class="table table-bordered table-striped">
                <thead>
                    <tr class="">
                        <th>Fecha</th>
                        <th>Hora Inicio</th>
                        <th>Hora Fin</th>
                        <th>Fecha Expiracion</th> 
                        <th>Campo</th>
                        <th>Precio</th>
                    </tr>
                </thead>
                <tbody id="cuerpo-tabla-reservas">
                    <?php foreach ($reservas as $reserva): ?>
                        <?php if ($reserva->confirmado == 't'): ?>
                            <tr class="info">
                            <?php else: ?>
                            <tr class="danger">
                            <?php endif; ?>

                            <td><?php echo $reserva->fecha; ?></td>
                            <td><?php echo $reserva->horaInicio; ?></td>
                            <td><?php echo $reserva->horaFin; ?></td>
                            <td><?php echo $reserva->fechaExpiracion; ?></td>
                            <td><?php echo $reserva->campo; ?></td>
                            <td><?php echo $reserva->precio; ?></td>                    
                        </tr>
                    <?php endforeach; ?>
                </tbody>
            </table>
        </div>
    </div>
</div>

