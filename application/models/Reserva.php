<?php

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Description of Reserva
 *
 * @author Alison
 */

class Reserva extends CI_Model{
    
    var $cliente = '';
    var $telefono = '';
    var $campo_deportivo = '';
    var $fecha = '';
    var $hora_inicio = '';
    var $hora_fin = '';
    var $reserva_especial = '';
    var $precio = '';
    var $detalle = '';
    
    public function __construct(){
        parent::__construct();
        $this->load->model('Consultas');
    }
    
    public function datos(){
        $reserva = array(
            'NombreCliente' => $this->cliente,
            'TelefonoReferencia' => $this->telefono,
            'Precio' => $this->precio,
            'IdCampoDeportivo' => $this->campo_deportivo,
            'Fecha' => $this->fecha,
            'HoraInicio' => $this->hora_inicio,
            'HoraFin' => $this->hora_fin,
            'ReservaEspecial' => $this->reserva_especial
        );
        return $reserva;
    }
    public function actualizar($nombre, $telefono, $id_campo, $fecha, 
                    $hora_inicio, $hora_fin, $precio, $tipo_reserva){
        $this->cliente = $nombre;
        $this->telefono = $telefono;
        $this->campo_deportivo = $id_campo;
        $this->fecha = $fecha;
        $this->hora_inicio = $hora_inicio;
        $this->hora_fin = $hora_fin;
        $this->precio = $precio;
        $this->reserva_especial = $tipo_reserva;
        $this->detalle = "Reserva dia $fecha de $hora_inicio a $hora_fin";
    }

    public function cambiar_fecha($fecha){
        $this->fecha = $fecha;
    }
    
    public function fecha() {
        return $this->fecha;
    }
}
