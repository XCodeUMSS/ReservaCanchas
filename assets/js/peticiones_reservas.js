/**
 /** 
 *  Espera a que el DOM se termine de cargar
 */
$(document).ready(function () {
    fechaActual = $('#fecha_reserva').val();
    /*
     * Agregado del evento change, para detectar cambios en campo deportivo
     */
    $("select[name=campo_deportivo]").change(function () {
        console.log($('#fecha_reserva').val());
        realizarPeticionCampo($('select[name=campo_deportivo]').val());
    });

    var objetoInterval = setInterval(function () {
        if(fechaActual != $('#fecha_reserva').val()) {
            realizarPeticionFecha($('#fecha_reserva').val());
            fechaActual = $('#fecha_reserva').val();
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

