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


XCode.Gestion;

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

XCode.traducirMes = function(mesNumero) { 
    switch (mesNumero) {
        case "1 " : 
            return "Enero"
        break;
         case "2 " : 
            return "Febrero"
        break;
         case "3 " : 
            return "Marzo"
        break;
         case "4 " : 
            return "Abril"
        break;
         case "5 " : 
            return "Mayo"
        break;
         case "6 " : 
            return "Junio"
        break; 
        case "7 " : 
            return "Julio"
        break; 
        case "8 " : 
            return "Agosto"
        break;
        case "9 " : 
            return "Septiembre"
        break;
        case "10 " : 
            return "Octubre"
        break;
        case "11 " : 
            return "Noviembre"
        break;
        case "12 " : 
            return "Diciembre"
        break;
        
    }
} 

XCode.Eventos = {
    agregarEvento : function() {
        $('#select_gestion').on('change', function () {
            var gestion = this.value;
            graficoReporte.destroy();
            
            XCode.Funciones.peticionGanancias();
            XCode.Funciones.peticionGananciasTabla();
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

/**
 * Funciones necesarias para la renderizacion del grafico
 * @type Object
 */
XCode.Funciones = {
    reemplazarMes : function() {
        var campos = $('td'); 
        if(campos) { 
            for(i = 0;i < campos.length;i++) {
                if((i % 3) == 0){ 
                    campos[i].textContent = XCode.traducirMes(campos[i].textContent);
                    console.log(XCode.traducirMes(campos[i].textContent));
                }
            }
        } 
    }, 
    procesarRespuesta: function (response) {
        var respuesta = JSON.parse(response);
        var ganancias = respuesta.reporteganancias;
        console.log(response);
        console.log("entra a la primera");

        if (ganancias.length != 0) {
            XCode.Datos = [];
            
            var inicio = ganancias[0].mes;
            var totalGanado = 0;

            for (i = 0; i < inicio - 1; i++) {
                XCode.Datos.push(0);
            }

            for (e = 0; e < ganancias.length; e++) {
                XCode.Datos.push(ganancias[e].totalganado);
                var x = parseInt(ganancias[e].totalganado);
                totalGanado = totalGanado + x;
            }
            
            XCode.ConjuntoDatosGrafico.data = XCode.Datos;
            setTimeout(function () {
                XCode.Funciones.pintarGrafico();
            }, 100);

            $('#total').text(totalGanado + " Bs");
            XCode.Eventos.actualizarGestion();
            console.log(XCode.Datos);
        } else {
            $('#total').text(0 + " Bs");
            XCode.Eventos.actualizarGestion();
            graficoReporte.destroy();
        }
    },
    peticionGanancias: function() {
        $.ajax({
            data: 'gestion=' + XCode.Eventos.obtenerGestion(),
            url: "../ControladorReportes/procesarPeticionGanancias",
            type: 'post',
            beforeSend: function () {

            },
            success: this.procesarRespuesta,
            error: function () {
                console.log('Existen fallas en el servidor');
            }
        });
    },
    peticionGananciasTabla: function() {
        $.ajax({
            data: 'gestion=' + XCode.Eventos.obtenerGestion(),
            url: "../ControladorReportes/procesarPeticionGananciasTabla",
            type: 'post',
            beforeSend: function () {

            },
            success: function (response) {
                $('#tabla_reportes').html(response);
                XCode.Funciones.reemplazarMes();
                XCode.Eventos.actualizarGestion();
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
    XCode.Eventos.agregarEvento();
    XCode.Funciones.peticionGanancias();
    XCode.Funciones.peticionGananciasTabla();
    //setTimeout(XCode.Funciones.reemplazarMes(),300);
}

/**
 * Espera que el DOM sea completamente cargado para que la funcion principal se ejecute
 * @param function param
 */
$(document).ready(function () {
    principal();
});


