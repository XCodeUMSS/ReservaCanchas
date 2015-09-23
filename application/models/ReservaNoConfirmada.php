<?php

/**
 * Clase encargada de hacer consultas sobre las reservas no confirmadas
 * y consulta para la filtracion de datos mediante nombre de usuario
 *
 * @author Beimar
 */
class ReservaNoConfirmada extends CI_Model{
    public function __construct() {
        parent::__construct();
        $this->load->database();
    }
    
    /**
     * Esta funcion permite obtener las reservas no confirmadas
     * @return Array
     */
    public function obtenerReservasNoConfirmadas() {
        $this->db->select('r.IdReserva as id, r.NombreCliente AS nombre, '
                . 'r.TelefonoReferencia AS telefono, r.Fecha AS fecha, '
                . 'r.HoraInicio AS horaInicio, r.HoraFin AS horaFin, '
                . 'c.Nombre AS campo, ' . ' r.Precio as precio, ' 
                . 'r.FechaExpiracion as fechaExpiracion, ' . 'r.HoraExpiracion as horaExpiracion, '
                . 'c.RutaFoto as foto');
        $this->db->from('Reserva AS r, CampoDeportivo as c');
        $this->db->where('r.IdCampoDeportivo = c.IdCampoDeportivo AND r.Confirmado = FALSE');
        $consulta = $this->db->get();
        return $consulta->result();
    }
    
    /**
     * Esta funcion permite obtener las reservas No confirmadas, filtrado por nombre 
     * de usuario
     * @param type $nombreUsuario
     * @return Array
     */
    public function obtenerReservasNoConfirmadasPorUsuario($nombreUsuario) {
        $this->db->select('r.IdReserva as id, r.NombreCliente AS nombre, '
                . 'r.TelefonoReferencia AS telefono, r.Fecha AS fecha, '
                . 'r.HoraInicio AS horaInicio, r.HoraFin AS horaFin, '
                . 'c.Nombre AS campo, ' . ' r.Precio as precio, ' 
                . 'r.FechaExpiracion as fechaExpiracion, ' . 'r.HoraExpiracion as horaExpiracion');
        $this->db->from('Reserva AS r, CampoDeportivo as c');
        $this->db->where("r.IdCampoDeportivo = c.IdCampoDeportivo AND r.Confirmado = FALSE AND \"r\".\"NombreCliente\" ilike '".$nombreUsuario."%'");
        $consulta = $this->db->get();
        return $consulta->result();
    }
    
    
    public function confirmar($id) {
        $this->db->set('Confirmado', true);
        $this->db->where('IdReserva', $id);
        $this->db->update('Reserva');
    }
}
