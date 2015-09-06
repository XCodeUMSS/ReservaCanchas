<?php

defined('BASEPATH') OR exit('No direct script access allowed');

/**
 * Controlador para confirmar las reservas y tambien el proceso de peticion
 * para la filtracion de datos mediante nombre de usuario
 *
 * @author Beimar
 */
class ControladorConfirmarPrereserva extends CI_Controller {

    public function __construct() {
        parent::__construct();
        $this->load->model('reservaNoConfirmada');
    }

    /**
     * Funcion encargada de mostrar la vista de confirmacion de una prereserva
     */
    public function mostrarVistaConfirmacion() {
        $datos['reservas'] = $this->reservaNoConfirmada->obtenerReservasNoConfirmadas();
        
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

}
