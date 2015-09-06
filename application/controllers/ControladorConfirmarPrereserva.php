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
        $this->load->model('Consultas');
    }
    
    public function mostrarVistaConfirmacion() {
        session_start();
        $datos['menus'] = $this->Consultas->menus($_SESSION['rol']);
        $this->load->view('vista_confirmar_prereserva', $datos);
    }
}
