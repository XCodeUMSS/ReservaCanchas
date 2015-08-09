<?php
defined('BASEPATH') OR exit('No direct script access allowed');

/**
 * Esta clase realizara todo lo relacionado con las reservas
 *
 * @author Beimar
 */
class ControladorReserva extends CI_Controller {
    
    public function __construct() {
        parent::__construct();
        
    }
    
    public function index() {
        $this->load->view('vista_realizar_reserva');
    }
}
