package reservaCanchas.features.menu;

import reservaCanchas.features.reservaCancha.AgregarCanchaFeature;

import net.sf.sahi.client.Browser;
import net.sf.sahi.client.ElementStub;

/**
 * @date 16/08/2015
 * @author Khrono
 */
public class TopMenuFeature {
	/*
	 * Instancia de la clase Browser, utilizada  identificar los objetos
	 */
	private Browser browser;
	
        //elementos del ui
	private ElementStub btn_inicio;
	private ElementStub btn_canchas;
	private ElementStub btn_reserva;
	private ElementStub btn_reservaEspecial;
	
	public TopMenuFeature(Browser b){
		this.browser = b;
		//Creando intancias para lso objetos del UI
                btn_inicio = browser.link("Inicio");
                btn_canchas = browser.link("Canchas");
                btn_reserva = browser.link("Reservar");
                btn_reservaEspecial = browser.link("Reserva Especial");
		
		//Creando refecias a mensajes del UI
		//disableWarning = browser.cell("Disable");
		
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
    public void gotoReserva(){
        btn_reserva.click();
    }

    //TODO Añadir retorno
    public void gotoReservaEspecial(){
        btn_reservaEspecial.click();
    }

}