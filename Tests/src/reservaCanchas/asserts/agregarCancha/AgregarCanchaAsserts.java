package reservaCanchas.asserts.agregarCancha;

import net.sf.sahi.client.Browser;

import org.testng.Assert;
import reservaCanchas.features.agregarCancha.AgregarCanchaFeature;

/**
 * Asserciones para verificar cuando se agrega una cancha
 * @date 24/08/2015
 * @author Khronos
 */
public class AgregarCanchaAsserts {

    public static void assertCanchaAgregada(Browser browser,
            AgregarCanchaFeature agregarCancha,String nombre, String pathImagen,
            String precioHora,String tipoCancha, String tipoSuelo, String horaInicio, String horaFin) {
        Assert.assertEquals(agregarCancha.getLbl_ErrorMessages().getText(),
                "Se registro exitosamente el campo deportivo: " + nombre, 
                "No se puede agregar la nueva cancha por las siguientes razones: "
                        + agregarCancha.getLbl_ErrorMessages().getText());
        Assert.assertNotNull(browser.cell(nombre).under(agregarCancha.getLbl_nombre()),
                "La cancha \"" + nombre + "\" no fue agregada correctamente");
        Assert.assertNotNull(browser.cell(precioHora).rightOf(browser.cell(nombre)),
                "El precio por hora de la cancha \"" + nombre + "\" no fue agregado correctamente");
        Assert.assertNotNull(browser.cell(tipoCancha).rightOf(browser.cell(nombre)),
                "El tipo de cancha \" " + tipoCancha + "\" de la cancha \"" +
                        nombre + "\" no fue agregado correctamente");
        Assert.assertNotNull(browser.cell(tipoSuelo).rightOf(browser.cell(nombre)),
                "El tipo de suelo \" " + tipoSuelo + "\" de la cancha \"" +
                        nombre + "\" no fue agregado correctamente");
        Assert.assertNotNull(browser.cell(horaInicio + ":00").rightOf(browser.cell(nombre)),
                "La Hora de inicio de la cancha \"" + nombre + "\" no fue agregada correctamente");
        Assert.assertNotNull(browser.cell(horaFin + ":00").rightOf(browser.cell(nombre)),
                "La Hora de fin de la cancha \"" + nombre + "\" no fue agregada correctamente");
        String fileName;
        if(pathImagen.isEmpty()){
            fileName = "default.png";
        }else{
            String[] pathS = pathImagen.split("\\.");
            int ext = pathS.length - 1;
            String fileExtension = pathS[ext];
            fileName = nombre.replaceAll(" ", "%20") + "." + fileExtension;
        }
        Assert.assertNotNull(browser.image(fileName).rightOf(browser.cell(nombre)),
                "La Imagen de la cancha \"" + nombre + "\" no fue agregada correctamente");
    }

    public static void assertSetNombre(AgregarCanchaFeature agregarCancha, String nombre) {
        Assert.assertEquals(agregarCancha.getTxt_nombre().getText(),nombre,
                "El nombre de la cancha no fue agregado correctamente");
    }

    public static void assertSetImagen(AgregarCanchaFeature agregarCancha, String filePath) {
        //TODO agregar verificacion para este campo
        //Assert.assertTrue(filePath.contains(agregarCancha.getBtn_imagen().getValue()),
        //        "El path de la imagen de la cancha no fue agregado correctamente");
    }

    public static void assertSetPrecioHora(AgregarCanchaFeature agregarCancha, String precioHora) {
        Assert.assertEquals(agregarCancha.getTxt_precioHora().getValue(),precioHora,
                "El precio por hora de la cancha no fue agregado correctamente");
    }

    public static void assertSetTipoCancha(AgregarCanchaFeature agregarCancha, String tipoCancha) {
        Assert.assertEquals(agregarCancha.getCbo_tipoCancha().getSelectedText(),tipoCancha,
                "El tipo de la cancha no fue seleccionado correctamente");
    }

    public static void assertSetTipoSuelo(AgregarCanchaFeature agregarCancha, String tipoSuelo) {
        Assert.assertEquals(agregarCancha.getCbo_tipoSuelo().getSelectedText(),tipoSuelo,
                "El tipo del suelo de la cancha no fue seleccionado correctamente");
    }

    public static void assertSetHoraInicio(AgregarCanchaFeature agregarCancha, String horaInicio) {
        Assert.assertEquals(agregarCancha.getTxt_horaInicio().getText(),horaInicio,
                "La Hora del inicio de funcionamiento de la cancha no fue seleccionado correctamente");
    }

    public static void assertSetHoraFin(AgregarCanchaFeature agregarCancha, String horaFin) {
        Assert.assertEquals(agregarCancha.getTxt_horaFin().getText(),horaFin,
                "La Hora del fin de funcionamiento de la cancha no fue seleccionado correctamente");
    }

    public static void verificarNoAgregado(Browser browser, AgregarCanchaFeature agregarCancha, String nombre) {
        Assert.assertFalse(browser.cell(nombre).under(agregarCancha.getLbl_nombre()).exists(),
                "La cancha \"" + nombre + "\" fue agregada correctamente");
    }

    public static void verificarNuevoTipoCanchaNoAgregado(Browser browser, AgregarCanchaFeature agregarCancha, String tipoCancha) {
        Assert.assertFalse(browser.cell(tipoCancha).under(agregarCancha.getLbl_tipoCancha()).exists(),
                "El tipo de cancha \"" + tipoCancha + "\" fue agregado correctamente");
    }

    public static void verificarNuevoTipoSueloNoAgregado(Browser browser, AgregarCanchaFeature agregarCancha, String tipoSuelo) {
        Assert.assertFalse(browser.cell(tipoSuelo).under(agregarCancha.getLbl_tipoSuelo()).exists(),
                "El tipo de suelo \"" + tipoSuelo + "\" fue agregado correctamente");
    }

    public static void assertMensajeDeError(AgregarCanchaFeature agregarCancha, String mensaje) {
        Assert.assertTrue(agregarCancha.getLbl_ErrorMessages().getText().contains(mensaje), 
                "El mensaje \"" + mensaje + "\" no fue desplegado correctamente");
    }
}