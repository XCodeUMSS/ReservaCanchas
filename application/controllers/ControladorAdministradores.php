<?php

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Description of ControladorAdministradores
 *
 * @author Alison
 */
class ControladorAdministradores extends CI_Controller{
    
    var $mensaje;
    const ADMI = 1;
    
    public function __construct() {
        parent::__construct();
        $this->mensaje = '';
        $this->load->model('consultas');
    }
    
    public function index(){
        session_start();
        $datos['menus'] = $this->consultas->menus($_SESSION['rol']);
        $datos['mensaje'] = $this->mensaje;
        $this->load->view('registro_admi', $datos);
    }
    
    public function solicitud_registro(){
        $nombre_usuario = $this->input->post('nombre_usuario');
        $ci = $this->input->post('ci');
        $telefono = $this->input->post('telefono_referencia');
        $nombre = $this->input->post('nombre');
        $apellidos = $this->input->post('apellidos');
        
        if($this->consultas->existe_usuario($nombre_usuario)){
            $this->mensaje = "Ya existe el nombre de usuario: $nombre_usuario. "
                    . "Por favor escoja otro";
            $this->index();
        }
        else{
            $usuario = array(
                'Nombre' => $nombre,
                'CarnetIdentidad' => $ci,
                'TelefonoReferencia' => $telefono,
                'Apellidos' => $apellidos,
                'NombreUsuario' => $nombre_usuario,
                'Rol' => self::ADMI
            );
            $this->consultas->registrar_usuario($usuario);
            $this->mensaje = "El administrador $nombre_usuario fue exitosamente"
                    . " registrado. Listo para iniciar sesion.";
            $this->index();
        }
    }
}
