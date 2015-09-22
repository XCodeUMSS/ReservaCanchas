Notificaciones = {
    inicializar: inicializarComponentes,
    agregarEvento: agregarEvento,
    verificarNumeroPrereervas: function (numeroPrereservas) {
        if (numeroPrereservas > 0) {
            botonNotificacion.removeClass('sr-only');
            botonNotificacion.text(numeroPrereservas);
        } else {
            botonNotificacion.addClass('sr-only');
            botonNotificacion.text(numeroPrereservas);
        }
    }
};

Ayudantes = {
    rutaActual: function () {
        var URLactual = window.location.href;
        return URLactual;
    },
    urlValido: function () {
        if (Ayudantes.rutaActual() == "http://localhost/ReservaCanchas/index.php/") {
            return true;
        } else {
            return false;
        }
    },
    getUrlValido: function () {
        if (Ayudantes.urlValido()) {
            return "ControladorNotificaciones/procesarPeticionNumeroPrereservas";
        } else {
            return "../ControladorNotificaciones/procesarPeticionNumeroPrereservas";
        }
    }
}

ProcesoPeticion = {
    realizarPeticionNumeroPrereservas: function () {
        realizarPeticionNumeroPrereservas();
        setInterval(function () {
            realizarPeticionNumeroPrereservas();
        }, 1500);
    },
    realizarPeticionPrereservas: function () {
        $.ajax({
            data: 'id_campo_deportivo=' + 1,
            url: 'ControladorNotificaciones/procesarPeticionNotificaciones',
            type: 'post',
            beforeSend: function () {

            },
            success: function (response) {
                $('#panelNotificaciones').html(response);
            },
            error: function () {
                console.log('Existen fallas en el servidor');
            }
        });
    }
};

var botonNotificacion = null;

$(document).ready(function () {
    Notificaciones.inicializar();
    Notificaciones.agregarEvento(botonNotificacion);

    ProcesoPeticion.realizarPeticionNumeroPrereservas();
});

function inicializarComponentes() {
    botonNotificacion = $('.sr-only.badge');
    botonNotificacion.css('cursor', 'pointer');
}

function agregarEvento(botonNotificacion) {
    botonNotificacion.on('click', function (event) {
        if(Ayudantes.urlValido()) {
            ProcesoPeticion.realizarPeticionPrereservas();
        } else {
            window.location="http://localhost/ReservaCanchas/index.php/ControladorConfirmarPrereserva/mostrarVistaConfirmacion";
        }
    });
}

function realizarPeticionNumeroPrereservas() {
    $.ajax({
        data: 'id_campo_deportivo=' + 1,
        url: Ayudantes.getUrlValido(),
        type: 'post',
        beforeSend: function () {
            
        },
        success: function (response) {

            Notificaciones.verificarNumeroPrereervas(response);
        },
        error: function () {
            console.log('Existen fallas en el servidor');
        }
    });
}

