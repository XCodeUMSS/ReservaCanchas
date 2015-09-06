package reservaCanchas.asserts.reservarCancha;

import net.sf.sahi.client.Browser;

import org.testng.Assert;
import reservaCanchas.features.reservaCancha.ReservarCanchaFeature;

/**
 * Asserciones para verificar cuando se agrega una cancha
 * @date 24/08/2015
 * @author Khronos
 */
public class ReservarCanchaAsserts {

    public static void assertReservaCreada(Browser browser,
            ReservarCanchaFeature reservarCancha,String nombre, String telefono,
            String repetir,String cancha, String fecha, String horaInicio, String horaFin) {
        Assert.assertEquals(reservarCancha.getLbl_ErrorMessages().getText(),
                "Su reserva fue exitosamente registrada.", 
                "No se puede agregar la nueva reserva por las siguientes razones: "
                        + reservarCancha.getLbl_ErrorMessages().getText());
        Assert.assertNotNull(browser.cell(nombre).under(reservarCancha.getLbl_nombre()),
                "La reserva del cliente: \"" + nombre + "\" no fue creada correctamente");
        Assert.assertNotNull(browser.cell(telefono).rightOf(browser.cell(nombre)),
                "El telefono del cliente: \"" + nombre + "\" no fue agregado correctamente");
        //TODO - Verificar repeticiones
        Assert.assertNotNull(browser.cell(repetir).rightOf(browser.cell(nombre)),
                "El precio por hora de la cancha \"" + nombre + "\" no fue agregado correctamente");
        Assert.assertNotNull(browser.cell(cancha).rightOf(browser.cell(nombre)),
                "La cancha \" " + cancha + "\" de la reserva del usuario \"" +
                        nombre + "\" no fue seleccionada correctamente");
        Assert.assertNotNull(browser.cell(fecha).rightOf(browser.cell(nombre)),
                "La fecha de reserva: \" " + fecha + "\" del usuario \"" +
                        nombre + "\" no fue seleccionado correctamente");
        Assert.assertNotNull(browser.cell(horaInicio + ":00").rightOf(browser.cell(nombre)),
                "La Hora de inicio de la reserva del usuario \"" + nombre + "\" no fue creado correctamente");
        Assert.assertNotNull(browser.cell(horaFin + ":00").rightOf(browser.cell(nombre)),
                "La Hora de fin de la reserva del usuario \"" + nombre + "\" no fue agregada correctamente");
    }

    public static void assertSetNombre(ReservarCanchaFeature reservarCancha, String nombre) {
        Assert.assertEquals(reservarCancha.getTxt_nombre().getText(),nombre,
                "El nombre del cliente no fue agregado correctamente");
    }

    public static void assertSetTelefono(ReservarCanchaFeature reservarCancha, String telefono) {
        Assert.assertEquals(reservarCancha.getTxt_telefono().getValue(),telefono,
                "El telefono del cliente no fue agregado correctamente");
    }

    public static void assertSetRepetir(ReservarCanchaFeature reservarCancha, String repetir) {
        Assert.assertEquals(reservarCancha.getCbo_repetir().getSelectedText(),repetir,
                "El intervalo de repeticion no fue seleccionado correctamente");
    }

    public static void assertSetCancha(ReservarCanchaFeature reservarCancha, String cancha) {
        Assert.assertEquals(reservarCancha.getCbo_cancha().getSelectedText(),cancha,
                "La cancha no fue seleccionada correctamente");
    }

    public static void assertSetFecha(ReservarCanchaFeature reservarCancha, String fecha) {
        Assert.assertEquals(reservarCancha.getTxt_fecha().getText(),fecha,
                "La fecha de reserva de la cancha no fue seleccionada correctamente");
    }

    public static void assertSetHoraInicio(ReservarCanchaFeature reservarCancha, String horaInicio) {
        Assert.assertEquals(reservarCancha.getTxt_horaInicio().getText(),horaInicio,
                "La Hora del inicio de la reserva de la cancha no fue seleccionada correctamente");
    }

    public static void assertSetHoraFin(ReservarCanchaFeature reservarCancha, String horaFin) {
        Assert.assertEquals(reservarCancha.getTxt_horaFin().getText(),horaFin,
                "La Hora del fin de la reserva de la cancha no fue seleccionada correctamente");
    }

    public static void verificarNoAgregado(Browser browser, ReservarCanchaFeature reservarCancha, String nombre) {
        Assert.assertFalse(browser.cell(nombre).under(reservarCancha.getLbl_nombre()).exists(),
                "La cancha \"" + nombre + "\" fue agregada correctamente");
    }

    public static void verificarNuevaCanchaNoAgregada(Browser browser, ReservarCanchaFeature reservarCancha, String cancha) {
        Assert.assertFalse(browser.cell(cancha).under(reservarCancha.getLbl_cancha()).exists(),
                "El tipo de suelo \"" + cancha + "\" fue agregado correctamente");
    }

    public static void assertMensajeDeError(ReservarCanchaFeature reservarCancha, String mensaje) {
        Assert.assertTrue(reservarCancha.getLbl_ErrorMessages().getText().contains(mensaje), 
                "El mensaje \"" + mensaje + "\" no fue desplegado correctamente");
    }
}