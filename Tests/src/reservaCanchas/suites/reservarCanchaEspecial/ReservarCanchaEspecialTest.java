/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reservaCanchas.suites.reservarCanchaEspecial;

import net.sf.sahi.client.Browser;
import org.testng.annotations.*;
import reservaCanchas.common.Config;
import reservaCanchas.common.DDT;
import reservaCanchas.features.menu.TopMenuFeature;
import reservaCanchas.features.reservaCanchaEspecial.ReservarCanchaEspecialFeature;

/**
 *
 * @author Khronos
 */
public class ReservarCanchaEspecialTest {

    private Browser browser;
    private ReservarCanchaEspecialFeature reservarCancha;

    @Test(dataProvider = "reservarCanchaEspecial")
    public void AgregarCanchaTest(String nombreTestCase, String evento,
            String repetir, String cancha, String fecha, String horaInicio,
            String horaFin, String resultado) {
        if (resultado.equalsIgnoreCase("Creado")) {
                reservarCancha.reservarCancha(evento, repetir,
                        cancha, fecha, horaInicio, horaFin);
        } else {
            //obteniendo valores por defecto
            String defaultNombre = "", defaultRepetir = "",
                    defaultCancha = "", defaultFecha = "",
                    defaultHoraInicio = "", defaultHoraFin = "";
            if(resultado.equals("Limpiar debe resetear campos")){
                defaultNombre = reservarCancha.getCbo_evento().getText();
                defaultRepetir = reservarCancha.getCbo_repetir().getSelectedText();
                defaultCancha = reservarCancha.getCbo_cancha().getSelectedText();
                defaultFecha = reservarCancha.getTxt_fecha().getValue();
                defaultHoraInicio = reservarCancha.getTxt_horaInicio().getValue();
                defaultHoraFin = reservarCancha.getTxt_horaFin().getValue();
            }
            reservarCancha.setEvento(evento);
            reservarCancha.setRepetir(repetir);
            reservarCancha.setCancha(cancha);
            reservarCancha.setFecha(fecha);
            reservarCancha.setHoraInicio(horaInicio);
            reservarCancha.setHoraFin(horaFin);
            switch (resultado) {
                /*case "nombre repetido":
                    reservarCancha.Agregar();
                    reservarCancha.verificarCreado(nombreCliente, telefono,
                            repetir, cancha, horaInicio, horaFin);
                    reservarCancha.setEvento(nombreCliente);
                    reservarCancha.setPrecioHora(telefono);
                    reservarCancha.setTipoCancha(repetir);
                    reservarCancha.setTipoSuelo(cancha);
                    reservarCancha.setHoraInicio(horaInicio);
                    reservarCancha.setHoraFin(horaFin);
                    reservarCancha.Agregar();
                    reservarCancha.verificarMensajeDeError(nombreCliente);
                    break;*/
                case "evento no editable":
                    String nuevoEvento = evento + " extra";
                    reservarCancha.getCbo_evento().setValue(nuevoEvento);
                    reservarCancha.verificarNoAgregado(evento);
                    break;
                case "repeticion no editable":
                    String nuevaRepeticion = repetir + " extra";
                    reservarCancha.getCbo_repetir().setValue(nuevaRepeticion);
                    reservarCancha.verificarNoAgregado(repetir);
                    break;
                case "cancha no editable":
                    String nuevaCancha = cancha + " extra";
                    reservarCancha.getCbo_cancha().setValue(nuevaCancha);
                    reservarCancha.verificarCanchaNoAgregada(nuevaCancha);
                    break;
                case "Limpiar debe resetear campos":
                    reservarCancha.Limpiar();
                    reservarCancha.verificarCampos(defaultNombre,
                            defaultRepetir,defaultCancha,defaultFecha,
                            defaultHoraInicio,defaultHoraFin);
                    break;
                default:
                    new Exception("Test Case no soportado");
            }
        }
    }

    @DataProvider(name = "reservarCanchaEspecial")
    public static Object[][] data() {
        return DDT.DDTReaderFull("DDT/ReservarCanchaEspecial/ReservarCanchaEspecial.csv");
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
        this.browser = Config.getBrowser();
        TopMenuFeature topMenu = new TopMenuFeature(browser);
        reservarCancha = topMenu.gotoReservaCanchaEspecial();
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
        //browser.close();
    }
}