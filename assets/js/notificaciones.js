Notificaciones = {
	numeroInicial : 0,
    inicializar: inicializarComponentes,
    agregarEvento: agregarEvento,
    verificarNumeroPrereervas: function (numeroPrereservas) {
        if (numeroPrereservas > 0) {
            botonNotificacion.removeClass('sr-only');
            botonNotificacion.text(numeroPrereservas);
			if(numeroPrereservas != Notificaciones.numeroInicial) {
				if(Ayudantes.urlValido() || Ayudantes.rutaActual() == "http://localhost/ReservaCanchas/index.php/") {
					ProcesoPeticion.realizarPeticionPrereservas();
				}
				Notificaciones.numeroInicial = numeroPrereservas;
			}
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
        if (Ayudantes.rutaActual() == "http://localhost/ReservaCanchas/index.php/Welcome/solicitud_sesion") {
            return true;
        } else {
            return false;
        }
    },
    getUrlValido: function () {
        if (Ayudantes.urlValido()) {
            return "../ControladorNotificaciones/procesarPeticionNumeroPrereservas";
        } else if(Ayudantes.rutaActual() == "http://localhost/ReservaCanchas/index.php/"){
            return "ControladorNotificaciones/procesarPeticionNumeroPrereservas";
        } else {
            return "../ControladorNotificaciones/procesarPeticionNumeroPrereservas";
        }
    },
    getUrlReservas : function () {
        if (Ayudantes.urlValido()) {
            console.log('esdsafsdfad');
            return "../ControladorNotificaciones/procesarPeticionNotificaciones";
        } else if(Ayudantes.rutaActual() == "http://localhost/ReservaCanchas/index.php/"){
            return "ControladorNotificaciones/procesarPeticionNotificaciones";
        } else {
            return "../ControladorNotificaciones/procesarPeticionNotificaciones";
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
            url: Ayudantes.getUrlReservas(),
            type: 'post',
            beforeSend: function () {

            },
            success: function (response) {
				console.log('renderizando');
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

    $('[data-toggle="tooltip"]').tooltip(); 

    ProcesoPeticion.realizarPeticionNumeroPrereservas();
    if(Ayudantes.urlValido() || Ayudantes.rutaActual() == "http://localhost/ReservaCanchas/index.php/") {
        ProcesoPeticion.realizarPeticionPrereservas();
    }
});

function inicializarComponentes() {
    botonNotificacion = $('.sr-only.badge');
    botonNotificacion.css('cursor', 'pointer');
}

function agregarEvento(botonNotificacion) {
    botonNotificacion.on('click', function (event) {
        
        window.location="http://localhost/ReservaCanchas/index.php/ControladorConfirmarPrereserva/mostrarVistaConfirmacion";
       
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

