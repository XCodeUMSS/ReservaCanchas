<div class="col-md-12">
    <h2>Reporte de Ganancias Gestion 2015</h2>
    <div class="table-responsive">
        <table id="tabla_canchas" class="table table-bordered table-striped">
            <thead>
                <tr>
                    <th>Mes</th>
                    <th>Total Ganado</th>
                    <th>Reservas Realizadas</th>
                </tr>
            </thead>
            <tbody id="cuerpo-tabla-reservas">
                <?php foreach ($ganancias as $ganancia):?>
                <tr>
                    <td><?php echo $ganancia->mes; ?> </td>
                    <td><?php echo $ganancia->totalganado; ?> Bs.</td>
                    <td><?php echo $ganancia->numeroreservas; ?> </td>
                                       
                </tr>
                <?php endforeach;?>
            </tbody>
        </table>
    </div>
</div>

