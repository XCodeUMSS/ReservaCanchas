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

XCode.Eventos = {
    agregarEvento : function() {
        $('#select_gestion').on('change', function () {
            var gestion = this.value;
            XCode.Funciones.peticionCanchas();
            XCode.Funciones.peticionCanchasTabla();
            console.log(gestion); 
        });
    },
    obtenerGestion : function (){
        return $('#select_gestion').val();
    },
    actualizarGestion : function (){
        $('#etiqueta_gestion').text(this.obtenerGestion());
    }
}

XCode.Funciones = {
    peticionCanchasTabla :  function () {
        $.ajax({
            data: 'gestion=' + XCode.Eventos.obtenerGestion(),
            url: "../ControladorReportes/procesarPeticionCanchasPopularesTabla",
            type: 'post',
            beforeSend: function () {

            },
            success: function (response) {
                $('#tabla_reportes').html(response);
                XCode.Eventos.actualizarGestion();
                //XCode.Funciones.reemplazarMes();
                //XCode.Eventos.actualizarGestion();
            },
            error: function () {
                console.log('Existen fallas en el servidor');
            }
        });
    },
    peticionCanchas: function () {
        $.ajax({
            data: 'gestion=' + XCode.Eventos.obtenerGestion(),
            url: "../ControladorReportes/procesarPeticionCanchasPopulares",
            type: 'post',
            beforeSend: function () {

            },
            success: function (response) {
                var respuesta = JSON.parse(response);
                var numeroTotalReservas = 0;
                console.log(respuesta.canchaspopulares);
                var canchas = respuesta.canchaspopulares;
                if(canchas.length != 0) {
                    XCode.Datos = [];
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
                } else {
                    myPie.destroy();
                }
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
    XCode.Eventos.agregarEvento();
    XCode.Funciones.peticionCanchas();
    XCode.Funciones.peticionCanchasTabla();
}


