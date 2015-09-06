<?php
defined('BASEPATH') OR exit('No direct script access allowed');

/**
 * Description of ControladorConfirmarPrereserva
 *
 * @author Beimar
 */
class ControladorConfirmarPrereserva extends CI_Controller {
    
    public function __construct() {
        parent::__construct();
        $this->load->model('reservaNoConfirmada');
    }
    
    public function mostrarVistaConfirmacion() {
        $datos['reservas'] = $this->reservaNoConfirmada->obtenerReservasNoConfirmadas();
        
        $this->load->view('vista_confirmar_prereserva', $datos);
    }
}
