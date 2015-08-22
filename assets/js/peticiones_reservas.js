/**
 /** 
 *  Espera a que el DOM se termine de cargar
 */
$(document).ready(function () {
    
    /*
     * Agregado del evento change, para detectar cambios en campo deportivo
     */
    $("select[name=campo_deportivo]").change(function () {
        console.log($('#fecha_reserva').val());
        realizarPeticionCampo($('select[name=campo_deportivo]').val());
    });
    
    var div = document.getElementsByName('fecha_reserva');
    
    div[0].addEventListener('change', function () {
        console.log(this.value);
    }, false);
    
});

/**
 * Funcion que realiza la peticion ajax al servidor, filtrando el tipo de cancha
 * @param {type} idCampoDeportivo
 * @returns void
 */
function realizarPeticionCampo(idCampoDeportivo) {
    var idCampoDeportivo = idCampoDeportivo;
    
    $.ajax({
        data: 'id_campo_deportivo=' + idCampoDeportivo + '&fecha_reserva='+$('#fecha_reserva').val(),
        url:  '../servicioDeFiltracionDatos/procesarPeticionAjax',
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

