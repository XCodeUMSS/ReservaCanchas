<?php

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Description of ManejadorHoraFecha
 *
 * @author Alison
 */
class ValidadorDatos extends CI_Controller{
    
    public function __construct() {
        parent::__construct();
    }
    
    public function horaFechaValidas($fecha, $horaInicio, $horaFin) {
        $mensaje = "";
        date_default_timezone_set("America/La_Paz");
        $fechaActual = date("d/m/Y");
        $horaActual = date("H:i:s");
        if (($mensajeHoras = $this->horasValidas($horaInicio, $horaFin) == "")) {
            if ($fecha < $fechaActual){
                $mensaje .= "- La fecha ya paso.";
            }
            else{
                if($fecha == $fechaActual && $horaInicio < $horaActual){
                    $mensaje .= "- La fecha y hora ya pasaron.";
                }
            }
        }
        else{
            $mensaje .= $mensajeHoras;
        }
        if($horaFin - $horaInicio <= 0){
            $mensaje .= "- La reserva debe ser minimamente de una hora. ";
        }
        return $mensaje;
    }
    
    public function horasValidas($horaInicio, $horaFin){
        return $horaInicio >= $horaFin ? "- La hora de inicio es mayor o igual"
                . "que la hora final. " : "";
    }
    
    public function datosValidosReserva($nombre, $fecha, $horaInicio, $horaFin) {
        $mensaje = "";
        $mensaje .= $this->validarNombre($nombre);
        $mensaje .= $this->horaFechaValidas($fecha, $horaInicio, $horaFin);
        return $mensaje;
    }
    
    public function datosValidosCampo($nombre, $horaInicio, $horaFin, 
            $precioMinimo, $precio) {
        $mensaje = "";
        $mensaje .= $this->validarNombre($nombre);
        $mensaje .= $this->horasValidas($horaInicio, $horaFin);
        $mensaje .= $this->precioValido($precio, $precioMinimo);
        return $mensaje;
    }

    public function validarNombre($nombre) {
        return trim($nombre) == "" ? "- El nombre no es valido." : "";
    }

    public function precioValido($precio, $precioMinimo) {
        return $precio < $precioMinimo ? "- El precio minimo es "
                .$precioMinimo."." : "";
    }

}
