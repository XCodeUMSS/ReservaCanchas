<?php

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Esta clase realiza las validaciones de los datos
 * @version: 1.0
 * @modificado: 10 de Agosto del 2015
 * @author: Alison
 */
class ValidadorDatos extends CI_Controller {

    public function __construct() {
        parent::__construct();
    }

    /* Funcion que verifica si las horas son consistentes, si la fecha y las 
     * horas son validas en cuanto a la fecha y hora actual.
     */

    public function hora_fecha_validas($fecha, $hora_inicio, $hora_fin) {
        $mensaje = '';
        $mensaje_horas = $this->horas_validas($hora_inicio, $hora_fin);
        if ($mensaje_horas == '') {
            $mensaje .= $this->tiempo_pasado($fecha, $hora_inicio);
        } else {
            $mensaje .= $mensaje_horas;
        }
        if ($hora_fin - $hora_inicio <= 0) {
            $mensaje .= "- La reserva debe ser minimamente de una hora. ";
        }
        return $mensaje;
    }

    /*
     * Funcion que verifica si la hora final es mayor a la de inicio.
     */

    public function horas_validas($hora_inicio, $hora_fin) {
        $mensajeAlerta = '- La hora de inicio es mayor o igual que la hora '
                . 'final.';
        return $hora_inicio >= $hora_fin ? $mensajeAlerta : "";
    }

    /*
     * Funcion que verifica si los datos son validos para un reserva.
     */

    public function datos_validos_reserva($nombre, $fecha, $hora_inicio, 
            $hora_fin) {
        $mensaje = "";
        $mensaje .= $this->validar_nombre($nombre);
        $mensaje .= $this->hora_fecha_validas($fecha, $hora_inicio, $hora_fin);
        return $mensaje;
    }

    /*
     * Funcion que verifica si los datos son validos para un campo deportivo
     */

    public function datos_validos_campo($nombre, $hora_inicio, $hora_fin, 
            $precio_minimo, $precio) {
        $mensaje = "";
        $mensaje .= $this->validar_nombre($nombre);
        $mensaje .= $this->horas_validas($hora_inicio, $hora_fin);
        $mensaje .= $this->precio_valido($precio, $precio_minimo);
        return $mensaje;
    }

    /*
     * Funcion que verifica si el nombre es vacio o puros espacios.
     */

    public function validar_nombre($nombre) {
        $mensaje = '- El nombre no es valido.';
        return trim($nombre) == '' ? $mensaje : '';
    }

    /*
     * Funcion que verifica si el precio es mayor o igual al minimo.
     */

    public function precio_valido($precio, $precio_minimo) {
        $mensajeAlerta = "- El precio minimo es " . $precio_minimo . ".";
        return $precio < $precio_minimo ? $mensajeAlerta : '';
    }

    /*
     * Funcion que verifica si la fecha y hora ya pasaron.
     */

    public function tiempo_pasado($fecha, $hora_inicio) {
        $mensajeAlerta = '';
        date_default_timezone_set('America/La_Paz');
        $fecha_actual = date('d/m/Y');
        $hora_actual = date('H:i:s');
        if ($fecha < $fecha_actual) {
            $mensajeAlerta = '- La fecha ya paso.';
        } else {
            if ($fecha == $fecha_actual && $hora_inicio < $hora_actual) {
                $mensajeAlerta = '- La fecha y hora ya pasaron.';
            }
        }
        return $mensajeAlerta;
    }

}
