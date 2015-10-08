
<h2>Reporte de Canchas Populares Gestion <strong id="etiqueta_gestion"></strong></h2>
    <div class="table-responsive">
        <table id="tabla_canchas" class="table table-bordered table-striped">
            <thead>
                <tr>
                    <th>Nombre Campo Deportivo</th>
                    <th>Total Generado</th>
                    <th>Numero de Reservas</th>
                </tr>
            </thead>
            <tbody id="cuerpo-tabla-reservas">
                <?php foreach ($canchaspopulares as $cancha):?>
                <tr>
                    <td><?php echo $cancha->Nombre; ?> </td>
                    <td><?php echo $cancha->totalganado; ?> Bs.</td>
                    <td><?php echo $cancha->numeroreservas; ?> </td>
                                       
                </tr>
                <?php endforeach;?>
            </tbody>
        </table>
    </div>

