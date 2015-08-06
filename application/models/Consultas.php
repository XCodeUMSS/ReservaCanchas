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
        $this->db->select('c.Nombre AS nombre,  c.PrecioPorHora AS precio, 
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
}

