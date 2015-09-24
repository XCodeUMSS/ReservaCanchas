<div class="panel panel-primary">
    <div class="panel-heading">
        <h3 style="margin-top: 3px; margin-bottom: 3px"><span class="glyphicon glyphicon-dashboard" aria-hidden="true"></span>  
            Notificaciones Prereservas <span style="padding-bottom: 0px" class="badge"><?php echo $numero; ?></span>
        </h3>
    </div>
    <div class="panel-body">
        <ul class="media-list">
            <?php foreach ($reservas as $reserva): ?>

                <li class="media">
                    <div class="media-left">
                        <a href="#">
                            <img class="media-object img-rounded" src="<?php echo (base_url() . $reserva->foto); ?>" alt="..." width="60" height="60">
                        </a>
                    </div>
                    <div class="media-body">
                        <h4 class="media-heading"><p class="text-uppercase"><strong><?php echo $reserva->campo; ?></strong><small> <?php echo $reserva->fecha; ?></small></p></h4>
                        <p class="text-muted">Reservada por: <strong><?php echo $reserva->nombre; ?></strong> de <?php echo $reserva->horaInicio; ?> a <?php echo $reserva->horaFin; ?></p>

                    </div>
                </li>

            <?php endforeach; ?>
        </ul>
    </div>
</div>