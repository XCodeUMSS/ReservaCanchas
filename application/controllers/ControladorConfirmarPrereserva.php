<?php

defined('BASEPATH') OR exit('No direct script access allowed');

/**
 * Controlador para confirmar las reservas y tambien el proceso de peticion
 * para la filtracion de datos mediante nombre de usuario
 *
 * @author Beimar
 */
class ControladorConfirmarPrereserva extends CI_Controller {
    
    var $mensaje;

    public function __construct() {
        session_start();
        parent::__construct();
        $this->load->model('reservaNoConfirmada');
        $this->load->model('Consultas');
        $this->mensaje = '';
    }

    /**
     * Funcion encargada de mostrar la vista de confirmacion de una prereserva
     */
    public function mostrarVistaConfirmacion() {
        $datos['reservas'] = $this->reservaNoConfirmada->obtenerReservasNoConfirmadas();
        $datos['menus'] = $this->Consultas->menus($_SESSION['rol']);
        $datos['mensaje'] = $this->mensaje;
        $this->load->view('vista_confirmar_prereserva', $datos);
    }

    /**
     * Funcion encargada de procesar la peticion ajax
     */
    public function procesarPeticionPrereservas() {
        $nombreUsuario = $this->input->post('nombre_usuario');
        
        $datos['reservas'] = $this->reservaNoConfirmada->obtenerReservasNoConfirmadasPorUsuario($nombreUsuario);

        $this->load->view('plantillas/reservas_no_confirmadas', $datos);
    }
    
    /*
     * Funcion que confirma prereserva
     */
    
    public function confirmar_prereserva(){
        if (isset($_REQUEST['cod'])) {
            $this->reservaNoConfirmada->confirmar($_REQUEST['cod']);
            $this->mensaje = 'La reserva fue confirmada.';
            $reserva = $this->reservaNoConfirmada->codigo($_REQUEST['cod']);
            $datos['confirmar'] = true;
            $datos['cliente'] = $reserva[0]->cliente;
            $datos['detalle'] = "Reserva el dia " .$reserva[0]->fecha . " de " .
                    $reserva[0]->horaInicio . " a " . $reserva[0]->horaFin;
            $datos['precio_total'] = $reserva[0]->precio;
            $datos['campo'] = $reserva[0]->campo;
            $datos['mensaje'] = $this->mensaje;
            $datos['menus'] = $this->Consultas->menus($_SESSION['rol']);
            $datos['admi'] = $this->Consultas->nombre_admi($_SESSION['usuario']);
            $datos['codigo'] = $this->crear_recibo($datos);
            $this->Consultas->reserva_pagada($_REQUEST['cod'], $datos['codigo']);
            $this->load->view('recibo', $datos);
        }
    }
    
    /*
     * Funcion que crea un recibo
     */
    
    public function crear_recibo($datos) {
        $datetime = new DateTime('now');
        $datetime = $datetime->format('d/m/y');
        $recibo = array(
            "Fecha" => $datetime,
            "Precio" => $datos['precio_total'],
            "Administrador" => $_SESSION['usuario'],
            "NumeroReservas" => 1
        );
        return $this->Consultas->insertar_recibo($recibo);
    }

}
