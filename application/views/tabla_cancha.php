<div class="col-md-12">
    <h2>Lista de Canchas</h2>
    <div class="table-responsive">
        <table id="tabla_canchas" class="table table-bordered table-striped">
            <thead>
                <tr>
                    <th>Nombre</th>
                    <th>Archivo Imagen</th>
                    <th>Precio/Hora</th>
                    <th>Tipo Cancha</th> 
                    <th>Tipo de Suelo</th>
                    <th>Hora Inicio</th>
                    <th>Hora Fin</th>
                </tr>
            </thead>
            <tbody id="cuerpo-tabla-canchas">
                <?php foreach ($canchas as $cancha):?>
                <tr>
                    <td><?php echo $cancha->nombre; ?></td>
                    <td><img height="100" width="100" src="<?php echo (base_url().$cancha->imagen); ?>"/></td>
                    <td><?php echo $cancha->precio; ?></td>
                    <td><?php echo $cancha->tipoCancha; ?></td>
                    <td><?php echo $cancha->tipoSuelo; ?></td>
                    <td><?php echo $cancha->horaInicio; ?></td>
                    <td><?php echo $cancha->horaFin; ?></td>
                </tr>
                <?php endforeach;?>
            </tbody>
        </table>
    </div>
</div>

