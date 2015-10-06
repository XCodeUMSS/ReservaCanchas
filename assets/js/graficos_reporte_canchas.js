/**
 * 
 * Espacio de nombres para la aplicacione
 * Define todo lo relacionado a los graficos
 */
XCode = {};

/**
 * Genera un color aleatorio
 * @returns {String}
 */
XCode.colorAleatorio = function () {
    var letras = '0123456789ABCDEF'.split('');
    var color = '#';
    for (var i = 0; i < 6; i++) {
        color += letras[Math.round(Math.random() * 15)];
    }
    return color;
}

/**
 * Datos que sirven para pintar el grafico,representan el la cantidad en bolivianos
 * @type Array
 */
XCode.Datos = [];



XCode.Funciones = {
    peticionCanchas: function peticionGanancias() {
        $.ajax({
            data: 'id_campo_deportivo=' + 1,
            url: "../ControladorReportes/procesarPeticionCanchasPopulares",
            type: 'post',
            beforeSend: function () {

            },
            success: function (response) {
                var respuesta = JSON.parse(response);
                var totalGanado = 0;
                var numeroTotalReservas = 0;
                console.log(respuesta.canchaspopulares);
                var canchas = respuesta.canchaspopulares;
                for (i = 0; i < canchas.length; i++) {
                    
                    var nombre = canchas[i].Nombre;
                    var reservas = canchas[i].numeroreservas;
                    numeroTotalReservas += parseInt(reservas);
                    var datoGrafico = {
                        value: reservas,
                        color: XCode.colorAleatorio(),
                        label: nombre
                    }
                    
                    XCode.Datos.push(datoGrafico);
                }
                
                setTimeout(function () {
                    XCode.Funciones.pintarGrafico();
                }, 100);

                $('#total').text(numeroTotalReservas + " Reservas Realizadas");
            },
            error: function () {
                console.log('Existen fallas en el servidor');
            }
        });
    },
    pintarGrafico: function () {
        var ctx = document.getElementById("canvas").getContext("2d");
        myPie = new Chart(ctx).Pie(XCode.Datos, {
            responsive: true
        });
    }
}

$(document).ready(function () {
    principal();
});

function principal() {
    XCode.Funciones.peticionCanchas();
}


