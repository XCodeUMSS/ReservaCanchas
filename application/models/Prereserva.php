<?php


/**
 * Modelo para un Prereserva
 *
 * @author Beimar
 */
class Prereserva extends CI_Model{
    var $cliente = '';
    var $telefono = '';
    var $campo_deportivo = '';
    var $fecha = '';
    var $hora_inicio = '';
    var $hora_fin = '';
    var $reserva_especial = '';
    var $precio = '';
    var $repeticiones = array();
    var $fecha_expiracion = '';
    var $hora_expiracion = '';
    var $confirmado = '';
    
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
            'ReservaEspecial' => $this->reserva_especial,
            'FechaExpiracion' => $this->fecha_expiracion,
            'HoraExpiracion' => $this->hora_expiracion,
            'Confirmado' => $this->confirmado
        );
        return $reserva;
    }
    
    
    public function actualizar($nombre, $telefono, $id_campo, $fecha, 
                    $hora_inicio, $hora_fin, $precio, $tipo_reserva,
            $fecha_expiracion, $horaExpiracion,$confirmado){
        $this->cliente = $nombre;
        $this->telefono = $telefono;
        $this->campo_deportivo = $id_campo;
        $this->fecha = $fecha;
        $this->hora_inicio = $hora_inicio;
        $this->hora_fin = $hora_fin;
        $this->precio = $precio;
        $this->reserva_especial = $tipo_reserva;
        $this->fecha_expiracion = $fecha_expiracion;
        $this->hora_expiracion = $horaExpiracion;
        $this->confirmado = $confirmado;
    }

    public function cambiar_fecha($fecha){
        $this->fecha = $fecha;
    }
    
    public function fecha() {
        return $this->fecha;
    }
}
