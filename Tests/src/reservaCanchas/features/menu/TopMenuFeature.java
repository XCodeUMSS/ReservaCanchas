package reservaCanchas.features.menu;

import reservaCanchas.features.agregarCancha.AgregarCanchaFeature;

import net.sf.sahi.client.Browser;
import net.sf.sahi.client.ElementStub;
import reservaCanchas.features.reservaCancha.ReservarCanchaFeature;

/**
 * @date 16/08/2015
 * @author Khrono
 */
public class TopMenuFeature {
	/*
	 * Instancia de la clase Browser, utilizada  identificar los objetos
	 */
	private final Browser browser;
	
        //elementos del ui
	private final ElementStub btn_inicio;
	private final ElementStub btn_canchas;
	private final ElementStub btn_reserva;
	private final ElementStub btn_reservaEspecial;
	
	public TopMenuFeature(Browser b){
		this.browser = b;
		//Creando intancias para lso objetos del UI
                btn_inicio = browser.link("Inicio");
                btn_canchas = browser.link("Canchas");
                btn_reserva = browser.link("Reservar");
                btn_reservaEspecial = browser.link("Reserva Especial");
	}

    //TODO Añadir retorno
    public void gotoInicio(){
        btn_inicio.click();
    }

    public AgregarCanchaFeature gotoCanchas(){
        btn_canchas.click();
        return new AgregarCanchaFeature(browser);
    }

    //TODO Añadir retorno
    public ReservarCanchaFeature gotoReserva(){
        btn_reserva.click();
        return new ReservarCanchaFeature(browser);
    }

    //TODO Añadir retorno
    public void gotoReservaEspecial(){
        btn_reservaEspecial.click();
    }

}