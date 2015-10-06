/**
 * 
 * Espacio de nombres para la aplicacione
 * Define todo lo relacionado a los graficos
 */
XCode = {};

/**
 * Datos que sirven para pintar el grafico,representan el la cantidad en bolivianos
 * @type Array
 */
XCode.Datos = [];

/**
 * Los meses de la gestion
 * @type Array
 */
XCode.Meses = ["Enero", "Febrero", "Marzo", "Abril",
        "Mayo", "Junio", "Julio", "Agosto", "Septiembre",
        "Octubre", "Noviembre", "Diciembre"];

/**
 * Configuracion del grafico
 * @type Object
 */
XCode.ConjuntoDatosGrafico  = {
        fillColor: "rgba(151,187,205,0.5)",
        strokeColor: "rgba(151,187,205,0.8)",
        highlightFill: "rgba(151,187,205,0.75)",
        highlightStroke: "rgba(151,187,205,1)",
        data: XCode.Datos
};

/**
 * Datos de inicializacion
 * @type object
 */
XCode.GraficosReportes = {
    datosGrafico : {
        labels: XCode.Meses,
        datasets: [XCode.ConjuntoDatosGrafico]
    }
}

/**
 * Funciones necesarias para la renderizacion del grafico
 * @type Object
 */
XCode.Funciones = {
    peticionGanancias: function peticionGanancias() {
        $.ajax({
            data: 'id_campo_deportivo=' + 1,
            url: "../ControladorReportes/procesarPeticionGanancias",
            type: 'post',
            beforeSend: function () {

            },
            success: function (response) {
                var respuesta = JSON.parse(response);
                var ganancias = respuesta.reporteganancias;
                
                var inicio = ganancias[0].mes;
                totalGanado = 0;
                
                for (i = 0; i < inicio - 1; i++) {
                    XCode.Datos.push(0);
                }
                
                for(e = 0; e < ganancias.length; e++) {
                    XCode.Datos.push(ganancias[e].totalganado);
                    var x = parseInt(ganancias[e].totalganado);
                    totalGanado = totalGanado + x;
                }
                setTimeout(function () {
                    XCode.Funciones.pintarGrafico();
                }, 100);

                $('#total').text(totalGanado + " Bs");
            },
            error: function () {
                console.log('Existen fallas en el servidor');
            }
        });
    },
    pintarGrafico: function () {
        var ctx = document.getElementById("canvas").getContext("2d");
        graficoReporte = new Chart(ctx).Bar(XCode.GraficosReportes.datosGrafico, {
            responsive: true
        });
    }
}

/**
 * Funcion principal del script
 * @returns void
 */
function principal() {
    XCode.Funciones.peticionGanancias();
}

/**
 * Espera que el DOM sea completamente cargado para que la funcion principal se ejecute
 * @param function param
 */
$(document).ready(function () {
    principal();
});


