<?php

/**
 * Description of ControladorNotificaciones
 *
 * @author Beimar
 */
class ControladorNotificaciones extends CI_Controller {
    
    public function __construct() {
        parent::__construct();
        $this->load->model('reservaNoConfirmada');
        $this->load->model('ModeloNotificaciones');
    }
    
    public function procesarPeticionNumeroPrereservas() {
        $dato = $this->ModeloNotificaciones->obtenerNumeroPrereservas();
        
        echo $dato[0]->numero;
    }
    
    public function procesarPeticionNotificaciones() {
        $datos['reservas'] = $this->reservaNoConfirmada->obtenerReservasNoConfirmadas();
        $datos['numero'] = $this->ModeloNotificaciones->obtenerNumeroPrereservas()[0]->numero;
        
        $this->load->view('plantillas/notificaciones', $datos);
    }            
}
