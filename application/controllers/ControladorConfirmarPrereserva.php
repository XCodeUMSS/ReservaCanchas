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
        $this->load->model('Prereserva');
    }
    
    public function mostrarVistaConfirmacion() {
        $this->load->view('vista_confirmar_prereserva');
    }
}
