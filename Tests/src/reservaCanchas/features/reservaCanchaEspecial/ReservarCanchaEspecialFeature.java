package reservaCanchas.features.reservaCanchaEspecial;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import net.sf.sahi.client.Browser;
import net.sf.sahi.client.ElementStub;
import reservaCanchas.asserts.reservarCanchaEspecial.ReservarCanchaEspecialAsserts;

/**
 * @date 04/09/2015
 * @author Khrono
 */
public class ReservarCanchaEspecialFeature {
    /*
     * Instancia de la clase Browser, utilizada  identificar los objetos
     */

    private final Browser browser;

    //elementos del ui
    private final ElementStub cbo_evento;
    private final ElementStub cbo_repetir;
    private final ElementStub cbo_cancha;
    private final ElementStub txt_fecha;
    private final ElementStub txt_horaInicio;
    private final ElementStub txt_horaFin;

    private final ElementStub lbl_nombre;
    private final ElementStub lbl_telefono;
    private final ElementStub lbl_cancha;
    private final ElementStub lbl_fecha;
    private final ElementStub lbl_horaInicio;
    private final ElementStub lbl_horaFin;

    private final ElementStub btn_reservar;
    private final ElementStub btn_limpiar;

    private final ElementStub lbl_ErrorMessages;

    public ReservarCanchaEspecialFeature(Browser b) {
        this.browser = b;
        //Creando intancias para lso objetos del UI
        cbo_evento = browser.select("nombre_evento");
        cbo_repetir = browser.select("repeticion");
        cbo_cancha = browser.select("campo_deportivo");
        txt_fecha = browser.textbox("fecha_reserva");
        txt_horaInicio = browser.textbox("hora_inicio");
        txt_horaFin = browser.textbox("hora_fin");
        btn_reservar = browser.submit("Reservar");
        btn_limpiar = browser.reset("Limpiar");

        lbl_nombre = browser.tableHeader("Nombre");
        lbl_telefono = browser.tableHeader("Telefono de Referencia");
        lbl_cancha = browser.tableHeader("Campo Deportivo");
        lbl_fecha = browser.tableHeader("Fecha Reserva");
        lbl_horaInicio = browser.tableHeader("Hora Inicio");
        lbl_horaFin = browser.tableHeader("Hora Fin");
        //Creando refecias a mensajes del UI
        lbl_ErrorMessages = browser.paragraph("/.*/");
    }

    public void reservarCancha(String evento, String repetir, String cancha,
            String fecha, String horaInicio, String horaFin) {
        setEvento(evento);
        setRepetir(repetir);
        setCancha(cancha);
        setFecha(fecha);
        setHoraInicio(horaInicio);
        setHoraFin(horaFin);
        Reservar();
        ReservarCanchaEspecialAsserts.assertReservaCreada(browser, this, evento,
                repetir, cancha, fecha, horaInicio, horaFin);
    }

    public void Reservar() {
        btn_reservar.click();
    }

    public void Limpiar() {
        btn_limpiar.click();
    }

    public void setEvento(String evento) {
        cbo_evento.choose(evento);
        ReservarCanchaEspecialAsserts.assertSetEvento(this, evento);
    }

    public void setRepetir(String repetir) {
        cbo_repetir.choose(repetir);
        ReservarCanchaEspecialAsserts.assertSetRepetir(this, repetir);
    }

    public void setCancha(String cancha) {
        cbo_cancha.choose(cancha);
        ReservarCanchaEspecialAsserts.assertSetCancha(this, cancha);
    }

    public void setFecha(String fecha) {
        SimpleDateFormat textFormat = new SimpleDateFormat("MM/dd/yyyy", new Locale("en_EN"));
        SimpleDateFormat newFormat = new SimpleDateFormat("MMMM/d/yyyy", new Locale("en_EN"));
        Date fechaDate = null;
        String fechaNueva = "";
        try {
            fechaDate = textFormat.parse(fecha);
            fechaNueva = (newFormat.format(fechaDate)).toString();
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        txt_fecha.click();
        String[] fechaModificada = fechaNueva.split("/");
        //Seleccionamos el Mes
        ElementStub nextMonth = browser.link("next").in(browser.tableHeader("month"));
        ElementStub reqMonth = browser.span(fechaModificada[0]).in(browser.tableHeader("month"));
        while(!reqMonth.isVisible()){
            nextMonth.click();
        }
        //Seleccionamos el Año
        ElementStub nextYear = browser.link("next").in(browser.tableHeader("year"));
        ElementStub prevYear = browser.link("previous").in(browser.tableHeader("year"));
        ElementStub currYear = browser.span(0).in(browser.tableHeader("year"));
        ElementStub reqYear = browser.span(fechaModificada[2]).in(browser.tableHeader("year"));
        while(!reqYear.isVisible()){
            int currentYear = Integer.parseInt(currYear.getText());
            int requiredYear = Integer.parseInt(fechaModificada[2]);
            if(currentYear>requiredYear)
                prevYear.click();
            else
                nextYear.click();
        }
        //Seleccionamos el Dia
        ElementStub day = browser.cell(fechaModificada[1]).in(browser.table("calendar table table-bordered"));
        String className = "";
        try{
            className = day.getAttribute("className");
        }catch(Exception ex){
            ex.printStackTrace();
        }
        if(className.equals("off"))
            day = browser.cell(fechaModificada[1] + "[1]").in(browser.table("calendar table table-bordered"));
        day.click();
        
        ReservarCanchaEspecialAsserts.assertSetFecha(this, fecha);
    }

    public void setHoraInicio(String horaInicio) {
        ElementStub hora = browser.textbox("form-control bfh-number[0]");
        ElementStub minutos = browser.textbox("form-control bfh-number[1]");
        txt_horaInicio.click();
        String[] time = horaInicio.split(":");
        hora.setValue(time[0]);
        minutos.setValue(time[1]);
        ReservarCanchaEspecialAsserts.assertSetHoraInicio(this, horaInicio);
    }

    public void setHoraFin(String horaFin) {
        ElementStub hora = browser.textbox("form-control bfh-number[2]");
        ElementStub minutos = browser.textbox("form-control bfh-number[3]");
        txt_horaFin.click();
        String[] time = horaFin.split(":");
        hora.setValue(time[0]);
        minutos.setValue(time[1]);
        //TODO Take a look of this issue
        //ReservarCanchaEspecialAsserts.assertSetHoraFin(this, horaFin);
    }

    public void verificarNoAgregado(String nombre) {
        ReservarCanchaEspecialAsserts.verificarNoAgregado(browser, this, nombre);
    }

    public void verificarCanchaNoAgregada(String cancha) {
        ReservarCanchaEspecialAsserts.verificarNuevaCanchaNoAgregada(browser, this, cancha);
    }

    public void verificarCreado(String evento, String repetir, String cancha,
            String fecha, String horaInicio, String horaFin) {
        ReservarCanchaEspecialAsserts.assertReservaCreada(browser, this, evento,
                repetir, cancha, fecha, horaInicio, horaFin);
    }

    public void verificarMensajeDeError(String mensaje) {
        ReservarCanchaEspecialAsserts.assertMensajeDeError(this, mensaje);
    }

    public void verificarCampos(String defaultEvento,
            String defaultRepetir, String defaultCancha, String defaultFecha, 
            String defaultHoraInicio, String defaultHoraFin) {
        ReservarCanchaEspecialAsserts.assertSetEvento(this, defaultEvento);
        ReservarCanchaEspecialAsserts.assertSetRepetir(this, defaultRepetir);
        ReservarCanchaEspecialAsserts.assertSetCancha(this, defaultCancha);
        ReservarCanchaEspecialAsserts.assertSetFecha(this, defaultFecha);
        ReservarCanchaEspecialAsserts.assertSetHoraInicio(this, defaultHoraInicio);
        ReservarCanchaEspecialAsserts.assertSetHoraFin(this, defaultHoraFin);
    }

    public ElementStub getLbl_nombre() {
        return lbl_nombre;
    }

    public ElementStub getLbl_telefono() {
        return lbl_telefono;
    }

    public ElementStub getLbl_cancha() {
        return lbl_cancha;
    }

    public ElementStub getLbl_fecha() {
        return lbl_fecha;
    }

    public ElementStub getLbl_horaInicio() {
        return lbl_horaInicio;
    }

    public ElementStub getLbl_horaFin() {
        return lbl_horaFin;
    }

    public ElementStub getLbl_ErrorMessages() {
        return lbl_ErrorMessages;
    }

    public ElementStub getCbo_evento() {
        return cbo_evento;
    }

    public ElementStub getCbo_repetir() {
        return cbo_repetir;
    }

    public ElementStub getCbo_cancha() {
        return cbo_cancha;
    }

    public ElementStub getTxt_fecha() {
        return txt_fecha;
    }

    public ElementStub getTxt_horaInicio() {
        return txt_horaInicio;
    }

    public ElementStub getTxt_horaFin() {
        return txt_horaFin;
    }

    public ElementStub getBtn_agregar() {
        return btn_reservar;
    }

    public ElementStub getBtn_limpiar() {
        return btn_limpiar;
    }
}
