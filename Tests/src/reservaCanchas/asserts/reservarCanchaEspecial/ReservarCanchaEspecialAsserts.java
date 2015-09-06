package reservaCanchas.asserts.reservarCanchaEspecial;

import net.sf.sahi.client.Browser;

import org.testng.Assert;
import reservaCanchas.features.reservaCanchaEspecial.ReservarCanchaEspecialFeature;

/**
 * Asserciones para verificar cuando se agrega una cancha
 * @date 24/08/2015
 * @author Khronos
 */
public class ReservarCanchaEspecialAsserts {

    public static void assertReservaCreada(Browser browser,
            ReservarCanchaEspecialFeature reservarCancha,String evento, String repetir,
            String cancha, String fecha, String horaInicio, String horaFin) {
        Assert.assertEquals(reservarCancha.getLbl_ErrorMessages().getText(),
                "Su reserva fue exitosamente registrada.", 
                "No se puede agregar la nueva reserva por las siguientes razones: "
                        + reservarCancha.getLbl_ErrorMessages().getText());
        Assert.assertNotNull(browser.cell(evento).under(reservarCancha.getLbl_nombre()),
                "La reserva del evento: \"" + evento + "\" no fue creada correctamente");
        //TODO - Verificar repeticiones
        Assert.assertNotNull(browser.cell(repetir).rightOf(browser.cell(evento)),
                "El precio por hora de la cancha \"" + evento + "\" no fue agregado correctamente");
        Assert.assertNotNull(browser.cell(cancha).rightOf(browser.cell(evento)),
                "La cancha \" " + cancha + "\" de la reserva del usuario \"" +
                        evento + "\" no fue seleccionada correctamente");
        Assert.assertNotNull(browser.cell(fecha).rightOf(browser.cell(evento)),
                "La fecha de reserva: \" " + fecha + "\" del usuario \"" +
                        evento + "\" no fue seleccionado correctamente");
        Assert.assertNotNull(browser.cell(horaInicio + ":00").rightOf(browser.cell(evento)),
                "La Hora de inicio de la reserva del usuario \"" + evento + "\" no fue creado correctamente");
        Assert.assertNotNull(browser.cell(horaFin + ":00").rightOf(browser.cell(evento)),
                "La Hora de fin de la reserva del usuario \"" + evento + "\" no fue agregada correctamente");
    }

    public static void assertSetEvento(ReservarCanchaEspecialFeature reservarCancha, String evento) {
        Assert.assertEquals(reservarCancha.getCbo_evento().getSelectedText(),evento,
                "El nombre del evento no fue agregado correctamente");
    }

    public static void assertSetRepetir(ReservarCanchaEspecialFeature reservarCancha, String repetir) {
        Assert.assertEquals(reservarCancha.getCbo_repetir().getSelectedText(),repetir,
                "El intervalo de repeticion no fue seleccionado correctamente");
    }

    public static void assertSetCancha(ReservarCanchaEspecialFeature reservarCancha, String cancha) {
        Assert.assertEquals(reservarCancha.getCbo_cancha().getSelectedText(),cancha,
                "La cancha no fue seleccionada correctamente");
    }

    public static void assertSetFecha(ReservarCanchaEspecialFeature reservarCancha, String fecha) {
        Assert.assertEquals(reservarCancha.getTxt_fecha().getText(),fecha,
                "La fecha de reserva de la cancha no fue seleccionada correctamente");
    }

    public static void assertSetHoraInicio(ReservarCanchaEspecialFeature reservarCancha, String horaInicio) {
        Assert.assertEquals(reservarCancha.getTxt_horaInicio().getText(),horaInicio,
                "La Hora del inicio de la reserva de la cancha no fue seleccionada correctamente");
    }

    public static void assertSetHoraFin(ReservarCanchaEspecialFeature reservarCancha, String horaFin) {
        Assert.assertEquals(reservarCancha.getTxt_horaFin().getText(),horaFin,
                "La Hora del fin de la reserva de la cancha no fue seleccionada correctamente");
    }

    public static void verificarNoAgregado(Browser browser, ReservarCanchaEspecialFeature reservarCancha, String evento) {
        Assert.assertFalse(browser.cell(evento).under(reservarCancha.getLbl_nombre()).exists(),
                "El evento: \"" + evento + "\" fue agregada correctamente");
    }

    public static void verificarNuevaCanchaNoAgregada(Browser browser, ReservarCanchaEspecialFeature reservarCancha, String cancha) {
        Assert.assertFalse(browser.cell(cancha).under(reservarCancha.getLbl_cancha()).exists(),
                "El tipo de suelo \"" + cancha + "\" fue agregado correctamente");
    }

    public static void assertMensajeDeError(ReservarCanchaEspecialFeature reservarCancha, String mensaje) {
        Assert.assertTrue(reservarCancha.getLbl_ErrorMessages().getText().contains(mensaje), 
                "El mensaje \"" + mensaje + "\" no fue desplegado correctamente");
    }
}