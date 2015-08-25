package reservaCanchas.features.reservaCancha;

//import ReservaCanchas.Common.Browser;
import java.awt.AWTException;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.sahi.client.Browser;
import net.sf.sahi.client.ElementStub;
import reservaCanchas.asserts.agregarCancha.AgregarCanchaAsserts;
import reservaCanchas.common.Functions;

/**
 * @date 16/08/2015
 * @author Khrono
 */
public class AgregarCanchaFeature {
    /*
     * Instancia de la clase Browser, utilizada  identificar los objetos
     */

    private Browser browser;

    //elementos del ui
    private ElementStub txt_nombre;
    private ElementStub btn_imagen;
    private ElementStub txt_precioHora;
    private ElementStub cbo_tipoCancha;
    private ElementStub cbo_tipoSuelo;
    private ElementStub txt_horaInicio;
    private ElementStub txt_horaFin;

    private ElementStub lbl_nombre;
    private ElementStub lbl_imagen;
    private ElementStub lbl_precioHora;
    private ElementStub lbl_tipoCancha;
    private ElementStub lbl_tipoSuelo;
    private ElementStub lbl_horaInicio;
    private ElementStub lbl_horaFin;

    private ElementStub btn_agregar;
    private ElementStub btn_limpiar;

    public AgregarCanchaFeature(Browser b) {
        this.browser = b;
        //Creando intancias para lso objetos del UI
        txt_nombre = browser.textbox("nombre_cancha");
        btn_imagen = browser.file("imagen");
        txt_precioHora = browser.numberbox("precio_hora");
        cbo_tipoCancha = browser.select("tipo_cancha");
        cbo_tipoSuelo = browser.select("tipo_suelo");
        txt_horaInicio = browser.textbox("hora_inicio");
        txt_horaFin = browser.textbox("hora_fin");
        btn_agregar = browser.submit("Agregar");
        btn_limpiar = browser.reset("Limpiar");

        lbl_nombre = browser.tableHeader("Nombre");
        lbl_imagen = browser.tableHeader("Archivo Imagen");
        lbl_precioHora = browser.tableHeader("Precio/Hora");
        lbl_tipoCancha = browser.tableHeader("Tipo Cancha");
        lbl_tipoSuelo = browser.tableHeader("Tipo de Suelo");
        lbl_horaInicio = browser.tableHeader("Hora Inicio");
        lbl_horaFin = browser.tableHeader("Hora Fin");
        //Creando refecias a mensajes del UI
        //disableWarning = browser.cell("Disable Policy");
    }

    public ElementStub getLbl_nombre() {
        return lbl_nombre;
    }

    public ElementStub getLbl_imagen() {
        return lbl_imagen;
    }

    public ElementStub getLbl_precioHora() {
        return lbl_precioHora;
    }

    public ElementStub getLbl_tipoCancha() {
        return lbl_tipoCancha;
    }

    public ElementStub getLbl_tipoSuelo() {
        return lbl_tipoSuelo;
    }

    public ElementStub getLbl_horaInicio() {
        return lbl_horaInicio;
    }

    public ElementStub getLbl_horaFin() {
        return lbl_horaFin;
    }

    public Browser getBrowser() {
        return browser;
    }

    public void agregarCancha(String nombre, String precioHora, String tipoCancha, String tipoSuelo, String horaInicio, String horaFin) {
        setNombre(nombre);
        setPrecioHora(precioHora);
        setTipoCancha(tipoCancha);
        setTipoSuelo(tipoSuelo);
        setHoraInicio(horaInicio);
        setHoraFin(horaFin);
        //Agregar();
        AgregarCanchaAsserts.assertCanchaAgregada(browser, this, nombre, "",precioHora,
                tipoCancha, tipoSuelo, horaInicio, horaFin);
    }

    public void agregarCancha(String nombre, String pathImagen, String precioHora,
            String tipoCancha, String tipoSuelo, String horaInicio, String horaFin) {
        setNombre(nombre);
        setImagen(pathImagen);
        setPrecioHora(precioHora);
        setTipoCancha(tipoCancha);
        setTipoSuelo(tipoSuelo);
        setHoraInicio(horaInicio);
        setHoraFin(horaFin);
        //Agregar();
        AgregarCanchaAsserts.assertCanchaAgregada(browser, this, nombre, pathImagen,
                precioHora, tipoCancha, tipoSuelo, horaInicio, horaFin);
    }

    public void Agregar() {
        btn_agregar.click();
    }

    public void Limpiar() {
        btn_limpiar.click();
    }

    public ElementStub getTxt_nombre() {
        return txt_nombre;
    }

    public void setNombre(String nombre) {
        txt_nombre.setValue(nombre);
        AgregarCanchaAsserts.assertSetNombre(this, nombre);
    }

    public ElementStub getBtn_imagen() {
        return btn_imagen;
    }

    public void setImagen(String path) {
        String filePath = System.getProperty("user.dir") + File.separator + path;
        browser.setFile(btn_imagen, filePath);
        AgregarCanchaAsserts.assertSetImagen(this, filePath);
    }

    public ElementStub getTxt_precioHora() {
        return txt_precioHora;
    }

    public void setPrecioHora(String precioHora) {
        txt_precioHora.setValue(precioHora);
        AgregarCanchaAsserts.assertSetPrecioHora(this, precioHora);
    }

    public ElementStub getCbo_tipoCancha() {
        return cbo_tipoCancha;
    }

    public void setTipoCancha(String tipoCancha) {
        cbo_tipoCancha.choose(tipoCancha);
        AgregarCanchaAsserts.assertSetTipoCancha(this, tipoCancha);
    }

    public ElementStub getCbo_tipoSuelo() {
        return cbo_tipoSuelo;
    }

    public void setTipoSuelo(String tipoSuelo) {
        cbo_tipoSuelo.choose(tipoSuelo);
        AgregarCanchaAsserts.assertSetTipoSuelo(this, tipoSuelo);
    }

    public ElementStub getTxt_horaInicio() {
        return txt_horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        ElementStub hora = browser.textbox("form-control bfh-number[0]");
        ElementStub minutos = browser.textbox("form-control bfh-number[1]");
        txt_horaInicio.click();
        String[] time = horaInicio.split(":");
        hora.setValue(time[0]);
        minutos.setValue(time[1]);
        AgregarCanchaAsserts.assertSetHoraInicio(this, horaInicio);
    }

    public ElementStub getTxt_horaFin() {
        return txt_horaFin;
    }

    public void setHoraFin(String horaFin) {
        ElementStub hora = browser.textbox("form-control bfh-number[2]");
        ElementStub minutos = browser.textbox("form-control bfh-number[3]");
        txt_horaFin.click();
        String[] time = horaFin.split(":");
        hora.setValue(time[0]);
        minutos.setValue(time[1]);
        //TODO Take a look of this issue
        //AgregarCanchaAsserts.assertSetHoraFin(this, horaFin);
    }

    public ElementStub getBtn_agregar() {
        return btn_agregar;
    }

    public ElementStub getBtn_limpiar() {
        return btn_limpiar;
    }
}
