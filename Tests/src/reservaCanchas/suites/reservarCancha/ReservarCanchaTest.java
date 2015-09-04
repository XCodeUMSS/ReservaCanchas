/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reservaCanchas.suites.reservarCancha;

import net.sf.sahi.client.Browser;
import org.testng.annotations.*;
import reservaCanchas.common.Config;
import reservaCanchas.common.DDT;
import reservaCanchas.features.menu.TopMenuFeature;
import reservaCanchas.features.reservaCancha.ReservarCanchaFeature;

/**
 *
 * @author Khronos
 */
public class ReservarCanchaTest {

    private Browser browser;
    private ReservarCanchaFeature reservarCancha;

    @Test(dataProvider = "reservarCancha")
    public void AgregarCanchaTest(String nombreTestCase, String nombreCliente,
            String telefono, String repetir, String cancha, String fecha,
            String horaInicio, String horaFin, String resultado) {
        if (resultado.equalsIgnoreCase("Creado")) {
                reservarCancha.reservarCancha(nombreCliente, telefono, repetir,
                        cancha, fecha, horaInicio, horaFin);
        } else {/*
            //obteniendo valores por defecto
            String defaultNombre = "", defaultPrecioHora = "", defaultTipoCancha = "",
                    defaultTipoSuelo = "", defaultHoraInicio = "", defaultHoraFin = "";
            if(resultado.equals("Limpiar debe resetear campos")){
                defaultNombre = reservarCancha.getTxt_nombre().getText();
                defaultPrecioHora = reservarCancha.getTxt_precioHora().getValue();
                defaultTipoCancha = reservarCancha.getCbo_tipoCancha().getSelectedText();
                defaultTipoSuelo = reservarCancha.getCbo_tipoSuelo().getSelectedText();
                defaultHoraInicio = reservarCancha.getTxt_horaInicio().getValue();
                defaultHoraFin = reservarCancha.getTxt_horaFin().getValue();
            }
            reservarCancha.setNombre(nombreCliente);
            reservarCancha.setPrecioHora(telefono);
            reservarCancha.setTipoCancha(repetir);
            reservarCancha.setTipoSuelo(cancha);
            reservarCancha.setHoraInicio(horaInicio);
            reservarCancha.setHoraFin(horaFin);
            switch (resultado) {
                case "con espacios extra":
                    reservarCancha.Agregar();
                    reservarCancha.verificarAgregado(nombreCliente.trim(), telefono,
                            repetir, cancha, horaInicio, horaFin);
                    break;
                case "nombre repetido":
                    reservarCancha.Agregar();
                    reservarCancha.verificarAgregado(nombreCliente, telefono,
                            repetir, cancha, horaInicio, horaFin);
                    reservarCancha.setNombre(nombreCliente);
                    reservarCancha.setPrecioHora(telefono);
                    reservarCancha.setTipoCancha(repetir);
                    reservarCancha.setTipoSuelo(cancha);
                    reservarCancha.setHoraInicio(horaInicio);
                    reservarCancha.setHoraFin(horaFin);
                    reservarCancha.Agregar();
                    reservarCancha.verificarMensajeDeError(nombreCliente);
                    break;
                case "minima longitud de 3":
                case "sin caracteres especiales":
                case "nombre blanco":
                case "precio hora blanco":
                case "precio hora no numeros":
                case "precio hora !< 10 Futbol":
                case "precio hora !< 10 Basquet":
                case "precio hora !< 50 Tenis":
                case "precio hora !< 50 Futbol de Salon":
                case "precio hora !> 1000":
                case "Hora inicio/fin no debe ser Igual":
                case "Hora inicio no debe ser Menor":
                    reservarCancha.Agregar();
                    reservarCancha.verificarNoAgregado(nombreCliente);
                    break;
                case "tipo cancha no editable":
                    String nuevoTipoCancha = repetir + " extra";
                    reservarCancha.getCbo_tipoCancha().setValue(nuevoTipoCancha);
                    reservarCancha.verificarTipoCanchaNoAgregado(nuevoTipoCancha);
                    break;
                case "tipo suelo no editable":
                    String nuevoTipoSuelo = cancha + " extra";
                    reservarCancha.getCbo_tipoSuelo().setValue(nuevoTipoSuelo);
                    reservarCancha.verificarTipoSueloNoAgregado(nuevoTipoSuelo);
                    break;
                case "Limpiar debe resetear campos":
                    reservarCancha.Limpiar();
                    reservarCancha.verificarCampos(defaultNombre,defaultPrecioHora,
                            defaultTipoCancha,defaultTipoSuelo,defaultHoraInicio,defaultHoraFin);
                    break;
                default:
                    new Exception("Test Case no soportado");
            }*/
        }
    }

    @DataProvider(name = "reservarCancha")
    public static Object[][] data() {
        return DDT.DDTReaderFull("DDT/ReservarCancha/ReservarCancha.csv");
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
        this.browser = Config.getBrowser();
        TopMenuFeature topMenu = new TopMenuFeature(browser);
        reservarCancha = topMenu.gotoReserva();
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
        //browser.close();
    }
}