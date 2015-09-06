<?php

/**
 * Description of ReservaNoConfirmada
 *
 * @author Beimar
 */
class ReservaNoConfirmada extends CI_Model{
    public function __construct() {
        parent::__construct();
        $this->load->database();
    }
    
    public function obtenerReservasNoConfirmadas() {
        
    }
}
