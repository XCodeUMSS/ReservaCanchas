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
        $this->db->select('r.NombreCliente AS nombre, '
                . 'r.TelefonoReferencia AS telefono, r.Fecha AS fecha, '
                . 'r.HoraInicio AS horaInicio, r.HoraFin AS horaFin, '
                . 'c.Nombre AS campo, ' . ' r.Precio as precio, ' 
                . 'r.FechaExpiracion as fechaExpiracion, ' . 'r.HoraExpiracion as horaExpiracion');
        $this->db->from('Reserva AS r, CampoDeportivo as c');
        $this->db->where('r.IdCampoDeportivo = c.IdCampoDeportivo AND r.Confirmado = FALSE');
        $consulta = $this->db->get();
        return $consulta->result();
    }
}
