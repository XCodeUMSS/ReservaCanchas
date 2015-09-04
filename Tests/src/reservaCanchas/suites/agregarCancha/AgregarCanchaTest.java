/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reservaCanchas.suites.agregarCancha;

import net.sf.sahi.client.Browser;
import org.testng.annotations.*;
import reservaCanchas.common.Config;
import reservaCanchas.common.DDT;
import reservaCanchas.features.menu.TopMenuFeature;
import reservaCanchas.features.agregarCancha.AgregarCanchaFeature;

/**
 *
 * @author Khronos
 */
public class AgregarCanchaTest {

    private Browser browser;
    private AgregarCanchaFeature agregarCancha;

    @Test(dataProvider = "agregarCancha")
    public void AgregarCanchaTest(String nombreTestCase, String nombreCancha,
            String pathImagen, String precioHora, String tipoCancha,
            String tipoSuelo, String horaInicio, String horaFin, String resultado) {
        if (resultado.equalsIgnoreCase("Agregado")) {
            if (pathImagen.isEmpty()) {
                agregarCancha.agregarCancha(nombreCancha, precioHora,
                        tipoCancha, tipoSuelo, horaInicio, horaFin);
            } else {
                agregarCancha.agregarCancha(nombreCancha, pathImagen, precioHora,
                        tipoCancha, tipoSuelo, horaInicio, horaFin);
            }
        } else {
            //obteniendo valores por defecto
            String defaultNombre = "", defaultPrecioHora = "", defaultTipoCancha = "",
                    defaultTipoSuelo = "", defaultHoraInicio = "", defaultHoraFin = "";
            if(resultado.equals("Limpiar debe resetear campos")){
                defaultNombre = agregarCancha.getTxt_nombre().getText();
                defaultPrecioHora = agregarCancha.getTxt_precioHora().getValue();
                defaultTipoCancha = agregarCancha.getCbo_tipoCancha().getSelectedText();
                defaultTipoSuelo = agregarCancha.getCbo_tipoSuelo().getSelectedText();
                defaultHoraInicio = agregarCancha.getTxt_horaInicio().getValue();
                defaultHoraFin = agregarCancha.getTxt_horaFin().getValue();
            }
            agregarCancha.setNombre(nombreCancha);
            agregarCancha.setPrecioHora(precioHora);
            agregarCancha.setTipoCancha(tipoCancha);
            agregarCancha.setTipoSuelo(tipoSuelo);
            agregarCancha.setHoraInicio(horaInicio);
            agregarCancha.setHoraFin(horaFin);
            switch (resultado) {
                case "con espacios extra":
                    agregarCancha.Agregar();
                    agregarCancha.verificarAgregado(nombreCancha.trim(), precioHora,
                            tipoCancha, tipoSuelo, horaInicio, horaFin);
                    break;
                case "nombre repetido":
                    agregarCancha.Agregar();
                    agregarCancha.verificarAgregado(nombreCancha, precioHora,
                            tipoCancha, tipoSuelo, horaInicio, horaFin);
                    agregarCancha.setNombre(nombreCancha);
                    agregarCancha.setPrecioHora(precioHora);
                    agregarCancha.setTipoCancha(tipoCancha);
                    agregarCancha.setTipoSuelo(tipoSuelo);
                    agregarCancha.setHoraInicio(horaInicio);
                    agregarCancha.setHoraFin(horaFin);
                    agregarCancha.Agregar();
                    agregarCancha.verificarMensajeDeError(nombreCancha);
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
                    agregarCancha.Agregar();
                    agregarCancha.verificarNoAgregado(nombreCancha);
                    break;
                case "tipo cancha no editable":
                    String nuevoTipoCancha = tipoCancha + " extra";
                    agregarCancha.getCbo_tipoCancha().setValue(nuevoTipoCancha);
                    agregarCancha.verificarTipoCanchaNoAgregado(nuevoTipoCancha);
                    break;
                case "tipo suelo no editable":
                    String nuevoTipoSuelo = tipoSuelo + " extra";
                    agregarCancha.getCbo_tipoSuelo().setValue(nuevoTipoSuelo);
                    agregarCancha.verificarTipoSueloNoAgregado(nuevoTipoSuelo);
                    break;
                case "Limpiar debe resetear campos":
                    agregarCancha.Limpiar();
                    agregarCancha.verificarCampos(defaultNombre,defaultPrecioHora,
                            defaultTipoCancha,defaultTipoSuelo,defaultHoraInicio,defaultHoraFin);
                    break;
                default:
                    new Exception("Test Case no soportado");
            }
        }
    }

    @DataProvider(name = "agregarCancha")
    public static Object[][] data() {
        //return DDT.DDTReaderFull("DDT/AgregarCancha/AgregarCancha.csv");
        return DDT.DDTReaderFull("DDT/AgregarCancha/Canchas.csv");
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
        this.browser = Config.getBrowser();
        TopMenuFeature topMenu = new TopMenuFeature(browser);
        agregarCancha = topMenu.gotoCanchas();
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
        //browser.close();
    }
}