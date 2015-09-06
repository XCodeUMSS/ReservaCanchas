
/**
 * Funcion principal
 * 
 */
$(document).ready(function () {
    $('#nombre_usuario').on('keyup', function () {
        console.log(this.value);
        realizarPeticionReservas(this.value);
    });
});

/**
 * Funcion que realiza la peticion ajax al servidor, filtrando por nombre de usuario
 * @param {date} fecha
 * @returns void
 */
function realizarPeticionReservas(nombreUsuario) {
    /**
     * Invocar al metodo ajax de jquery
     */
    
    $.ajax({
        data: 'nombre_usuario=' + nombreUsuario,
        url: 'procesarPeticionPrereservas',
        type: 'post',
        beforeSend: function () {
            console.log('enviando la peticion......');
        },
        success: function (response) {
            console.log('La peticion ha sido satisfactoria');
            /**
             * Renderizacion de la respuesta
             */
            $('#reservas_no_confirmadas').html(response);
        },
        error: function () {
            console.log('Existen fallas en el servidor');
        }
    });
}

