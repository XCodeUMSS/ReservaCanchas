<div class="col-md-12">
    <h2>Lista de Reservas</h2>
    <div class="table-responsive">
        <table id="tabla_canchas" class="table table-bordered table-striped">
            <thead>
                <tr>
                    <th>Nombre</th>
                    <th>Telefono de Referencia</th>
                    <th>Campo Deportivo</th>
                    <th>Fecha Reserva</th> 
                    <th>Hora Inicio</th>
                    <th>Hora Fin</th>
                </tr>
            </thead>
            <tbody id="cuerpo-tabla-reservas">
                <?php foreach ($reservas as $reserva):?>
                <tr>
                    <td><?php echo $reserva->nombre; ?></td>
                    <td><?php echo $reserva->telefono; ?></td>
                    <td><?php echo $reserva->campo; ?></td>
                    <td><?php echo $reserva->fecha; ?></td>
                    <td><?php echo $reserva->horaInicio; ?></td>
                    <td><?php echo $reserva->horaFin; ?></td>                    
                </tr>
                <?php endforeach;?>
            </tbody>
        </table>
    </div>
</div>

