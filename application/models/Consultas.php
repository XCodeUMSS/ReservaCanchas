<?php

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

class Consultas extends CI_Model{
    
    public function __construct() {
        parent::__construct();
        $this->load->database();
    }
    
    public function existeNombre($nombre) {
        $this->db->select('IdCampoDeportivo');
        $this->db->from('CampoDeportivo');
        $this->db->where('Nombre',$nombre);
        $consulta = $this->db->get();
        return $consulta->num_rows() > 0;
    }
    
    public function getTiposCancha() {
        $consulta = $this->db->query('SELECT * FROM "TipoCancha"');
        return $consulta->result();
    }
    
    public function getTiposSuelo() {
        $consulta = $this->db->query('SELECT * FROM "TipoSuelo"');
        return $consulta->result();
    }
    
    public function getPrecio($idTipo){
        $this->db->select('PrecioMinimo');
        $this->db->from('TipoCancha');
        $this->db->where('IdTipoCancha',$idTipo);
        $consulta = $this->db->get();
        return $consulta->first_row()->PrecioMinimo;
    }


    public function getCamposRegistrados() {
        $this->db->select('c.IdCampoDeportivo AS id, c.Nombre AS nombre,  c.PrecioPorHora AS precio, 
        c.RutaFoto AS imagen, tc.Nombre AS tipoCancha, ts.Nombre AS tipoSuelo, 
        h.HoraInicio AS horaInicio, h.HoraFin AS horaFin');
        $this->db->from('CampoDeportivo AS c, TipoCancha AS tc,
        TipoSuelo AS ts, HorarioAtencion AS h');
        $this->db->where('c.IdTipoCancha = tc.IdTipoCancha AND
        c.IdTipoSuelo = ts.IdTipoSuelo AND
        c.IdCampoDeportivo = h.IdCampoDeportivo');
        $consulta = $this->db->get();
        return $consulta->result();
    }
    
    public function registrarCampo($campo, $horaInicio, $horaFin){
        $this->db->insert("CampoDeportivo", $campo);
        $idCampo = $this->db->insert_id();
        $horarioAtencion = array(
            'HoraInicio' => $horaInicio,
            'HoraFin' => $horaFin,
            'Dia' => "TS",
            'IdCampoDeportivo' => $idCampo
        );
        $this->db->insert("HorarioAtencion", $horarioAtencion);
    }
    
    public function registrarReserva($reserva) {
        $this->db->insert("Reserva", $reserva);  
    }
    
    public function horarios($idCampo) {
        $this->db->select('HoraInicio, HoraFin');
        $this->db->from('HorarioAtencion');
        $this->db->where('IdCampoDeportivo',$idCampo);
        $consulta = $this->db->get();
        return $consulta->first_row();
    }
    
    public function existeReserva($idCampo, $fecha, $horaInicio, $horaFin) {
        $this->db->select('IdReserva');
        $this->db->from('Reserva');
        $this->db->where("IdCampoDeportivo = '".$idCampo."' AND Fecha = '"
                .$fecha."' AND (HoraInicio < '".$horaFin."' AND HoraInicio >= '"
                .$horaInicio."' OR HoraFin > '".$horaInicio."' AND HoraFin <= '"
                .$horaFin."')");
        $consulta = $this->db->get();
        return $consulta->num_rows() > 0;
    }
    
    public function getPrecioCampo($idCampo) {
        $this->db->select('PrecioPorHora');
        $this->db->from('CampoDeportivo');
        $this->db->where('IdCampoDeportivo',$idCampo);
        $consulta = $this->db->get();
        return $consulta->first_row()->PrecioPorHora;
    }
    
    public function getReservasRegistradas() {
        $this->db->select('r.NombreCliente AS nombre, '
                . 'r.TelefonoReferencia AS telefono, r.Fecha AS fecha, '
                . 'r.HoraInicio AS horaInicio, r.HoraFin AS horaFin, '
                . 'c.Nombre AS campo');
        $this->db->from('Reserva AS r, CampoDeportivo as c');
        $this->db->where('r.IdCampoDeportivo = c.IdCampoDeportivo');
        $consulta = $this->db->get();
        return $consulta->result();
    }
}

