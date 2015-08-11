<?php

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Esta clase realiza las consultas a la base de datos.
 * @version: 1.0
 * @modificado: 10 de Agosto del 2015
 * @author: Alison
 */
class Consultas extends CI_Model {
    /*
     * Constructor de la clase en el cual se carga la base de datos.
     */

    public function __construct() {
        parent::__construct();
        $this->load->database();
    }

    /*
     * Funcion que verifica si existe algun campo deportivo con el nombre 
     * $nombre.
     */

    public function existe_nombre($nombre) {
        $this->db->select('IdCampoDeportivo');
        $this->db->from('CampoDeportivo');
        $this->db->where('Nombre', $nombre);
        $consulta = $this->db->get();
        return $consulta->num_rows() > 0;
    }

    /*
     * Funcion que devuelve todos los tipos de cancha.
     */

    public function tipos_cancha() {
        $consulta = $this->db->query('SELECT * FROM "TipoCancha"');
        return $consulta->result();
    }

    /*
     * Funcion que devuelve todos los tipos de suelo.
     */

    public function tipos_evento() {
        $consulta = $this->db->query('SELECT * FROM "TipoEvento"');
        return $consulta->result();
    }

    /*
     * Funcion que devuelve todos los tipos de suelo.
     */

    public function tipos_suelo() {
        $consulta = $this->db->query('SELECT * FROM "TipoSuelo"');
        return $consulta->result();
    }

    /*
     * Funcion que devuelve el precio minimo de acuerdo al tipo de cancha.
     */

    public function precio_tipo_cancha($idTipo) {
        $this->db->select('PrecioMinimo');
        $this->db->from('TipoCancha');
        $this->db->where('IdTipoCancha', $idTipo);
        $consulta = $this->db->get();
        return $consulta->first_row()->PrecioMinimo;
    }

    /*
     * Funcion que devuelve los campos registrados.
     */

    public function campos_registrados() {
        $this->db->select('c.IdCampoDeportivo AS id, 
                           c.Nombre AS nombre,  c.PrecioPorHora AS precio, 
                           c.RutaFoto AS imagen, tc.Nombre AS tipoCancha, 
                           ts.Nombre AS tipoSuelo, h.HoraInicio AS horaInicio, 
                           h.HoraFin AS horaFin');
        $this->db->from('CampoDeportivo AS c, TipoCancha AS tc,
                        TipoSuelo AS ts, HorarioAtencion AS h');
        $this->db->where('c.IdTipoCancha = tc.IdTipoCancha AND
                        c.IdTipoSuelo = ts.IdTipoSuelo AND
                        c.IdCampoDeportivo = h.IdCampoDeportivo');
        $consulta = $this->db->get();
        return $consulta->result();
    }

    /*
     * Funcion que registra campo deportivo y sus horarios de atencion
     */

    public function registrar_campo($campo, $hora_inicio, $hora_fin) {
        $this->db->insert("CampoDeportivo", $campo);
        $id_campo = $this->db->insert_id();
        $horario_atencion = array(
            'HoraInicio' => $hora_inicio,
            'HoraFin' => $hora_fin,
            'Dia' => "TS",
            'IdCampoDeportivo' => $id_campo
        );
        $this->db->insert("HorarioAtencion", $horario_atencion);
    }

    /*
     * Funcion que registra una reserva.
     */

    public function registrar_reserva($reserva) {
        $this->db->insert("Reserva", $reserva);
    }

    /*
     * Funcion que devuelve los horarios de atencion de un campo deportivo.
     */

    public function horarios($id_campo) {
        $this->db->select('HoraInicio, HoraFin');
        $this->db->from('HorarioAtencion');
        $this->db->where('IdCampoDeportivo', $id_campo);
        $consulta = $this->db->get();
        return $consulta->first_row();
    }

    /*
     * Funcion que verifica si existe una reserva dado: id_campo, fecha,
     * hora_inicio y  hora_fin.
     */

    public function existe_reserva($id_campo, $fecha, $hora_inicio, $hora_fin) {
        $this->db->select('IdReserva');
        $this->db->from('Reserva');
        $this->db->where("IdCampoDeportivo = '" . $id_campo . 
                "' AND Fecha = '" . $fecha . 
                "' AND (HoraInicio < '" . $hora_fin . 
                "' AND HoraInicio >= '". $hora_inicio . 
                "' OR HoraFin > '" . $hora_inicio . 
                "' AND HoraFin <= '". $hora_fin . "')");
        $consulta = $this->db->get();
        return $consulta->num_rows() > 0;
    }

    /*
     * Funcion que devuelve el precio por hora de un campo.
     */

    public function precio_campo($id_campo) {
        $this->db->select('PrecioPorHora');
        $this->db->from('CampoDeportivo');
        $this->db->where('IdCampoDeportivo', $id_campo);
        $consulta = $this->db->get();
        return $consulta->first_row()->PrecioPorHora;
    }

    /*
     * Funcion que devuelve las reservas registradas.
     */

    public function reservas_registradas() {
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
