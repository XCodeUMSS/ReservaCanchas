
/** 
 *  Espera a que el DOM se termine de cargar
 */

$(document).ready(function () {
    fechaActual = $('#fecha_reserva').val();
    realizarPeticionFecha($('#fecha_reserva').val());
    /*
     * Agregado del evento change, para detectar cambios en campo deportivo
     */
    $("select[name=campo_deportivo]").change(function () {
        console.log($('#fecha_reserva').val());

        realizarPeticionCampo($('select[name=campo_deportivo]').val());

        realizarPeticionHorario($('select[name=campo_deportivo]').val(), $('#fecha_reserva').val());
    });

    var objetoInterval = setInterval(function () {
        if (fechaActual != $('#fecha_reserva').val()) {
            realizarPeticionFecha($('#fecha_reserva').val());
            fechaActual = $('#fecha_reserva').val();
            realizarPeticionHorario($('select[name=campo_deportivo]').val(), $('#fecha_reserva').val());
        }
    }, 200);

});

/**
 * Funcion que realiza la peticion ajax al servidor, filtrando la fecha
 * @param {type} fecha
 * @returns void
 */
function realizarPeticionFecha(fecha) {


    $.ajax({
        data: 'fecha_reserva=' + fecha,
        url: '../servicioDeFiltracionDatos/procesarPeticionAjax',
        type: 'post',
        beforeSend: function () {
            console.log('enviando la peticion......');
        },
        success: function (response) {
            console.log('La peticion ha sido satisfactoria');


            $('#cuerpo-tabla-reservas').html(response);
        },
        error: function () {
            console.log('Existen fallas en el servidor');
        }
    });
}

/**
 * Funcion que realiza la peticion ajax al servidor, filtrando el tipo de cancha
 * @param {type} idCampoDeportivo
 * @returns void
 */
function realizarPeticionCampo(idCampoDeportivo) {
    var idCampoDeportivo = idCampoDeportivo;
    if (idCampoDeportivo == 'nulo') {
        return;
    }
    $.ajax({
        data: 'id_campo_deportivo=' + idCampoDeportivo,
        url: '../servicioDeFiltracionDatos/procesarPeticionAjax',
        type: 'post',
        beforeSend: function () {
            console.log('enviando la peticion......');
        },
        success: function (response) {
            console.log('La peticion ha sido satisfactoria');


            $('#cuerpo-tabla-reservas').html(response);
        },
        error: function () {
            console.log('Existen fallas en el servidor');
        }
    });
}

/**
 * Realiza la peticion para detectar los horarios disponibles para un campo deportivo
 * dado la fecha
 * @param {type} idCampoDeportivo
 * @param {type} fecha
 * @returns {undefined}
 */
function realizarPeticionHorario(idCampoDeportivo, fecha) {
    if (idCampoDeportivo == 'nulo') {
        mostrarMensajeSeleccion();
        return;
    }
    $.ajax({
        data: 'id_campo_deportivo=' + idCampoDeportivo + '&fecha_reserva=' + fecha,
        url: '../servicioDeFiltracionDatos/procesarHorarios',
        type: 'post',
        beforeSend: function () {
            console.log('enviando la peticion de horario......');
        },
        success: function (response) {
            console.log('La peticion de horario ha sido satisfactoria');
            var respuesta = JSON.parse(response);
            var horarioAtencion = respuesta.horariosAtencion[0];
            var horarios = respuesta.horarios;
            var horariosDisponibles = [];

            var horaIni = horarioAtencion.horaInicio;
            var horaTer = horarioAtencion.horaFin;

            for (var i = 0; i < horarios.length; i++) {
                if (horarios[i].horaInicio == horarioAtencion.horaInicio) {
                    horaIni = horarios[i].horaFin;
                } else {
                    var objeto = {};
                    if (horaIni != horarios[i].horaInicio) {
                        objeto.horaInicio = horaIni;
                        objeto.horaFin = horarios[i].horaInicio;

                        horariosDisponibles[horariosDisponibles.length] = objeto;
                        horaIni = horarios[i].horaFin;
                    } else {
                        horaIni = horarios[i].horaFin;
                    }

                }

            }
            
            if (horaIni != horaTer) {
                var objeto = {
                    horaInicio: horaIni,
                    horaFin: horaTer
                };
                horariosDisponibles[horariosDisponibles.length] = objeto;
            }


            var result = "<ul class=\"list-group\">" +
                    "<li class=\"list-group-item active\">Horarios Disponibles</li>";
            for (var i = 0; i < horariosDisponibles.length; i++) {
                result = result + "<li class=\"list-group-item\"><span class=\"text-left\">" + horariosDisponibles[i].horaInicio +
                        "</span> -     <span class=\"text-right\">" + horariosDisponibles[i].horaFin + "</span></li>"
            }


            result = result + "</ul>";
            console.log(result);
            $('#campo-disponible').html(result);
        },
        error: function () {
            console.log('Existen fallas de horarios en el servidor');
        }
    });
}

/**
 * funcion para mostrar el mensaje de seleccion de cancha, para el caso en que el 
 * usuario no seleccione una cancha
 * @returns 
 */

function mostrarMensajeSeleccion() {
    var result = "<ul class=\"list-group\">" +
            "<li class=\"list-group-item active\">Horarios Disponibles</li>" +
            "<div class=\"alert alert-info\" role=\"alert\">"
            + "<span class=\"glyphicon glyphicon-info-sign\" aria-hidden=\"true\"></span>"
            + "Debe Seleccionar un Campo Deportivo</div>"
            + "</ul>";
    $('#campo-disponible').html(result);
}
