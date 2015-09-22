<?php

/**
 * Description of ModeloNotificaciones
 *
 * @author Beimar
 */
class ModeloNotificaciones extends CI_Model{
   
    /*
     * Constructor de la clase en el cual se carga la base de datos.
     */
    public function __construct() {
        parent::__construct();
        $this->load->database();
    }
    
    public function obtenerNumeroPrereservas() {
        $consulta = $this->db->query('select count(*) as numero from "Reserva" where "Confirmado" = false');
        return $consulta->result();
    }
    
}
