<?php

defined('BASEPATH') OR exit('No direct script access allowed');

class Welcome extends CI_Controller {

    /**
     * Index Page for this controller.
     *
     * Maps to the following URL
     * 		http://example.com/index.php/welcome
     * 	- or -
     * 		http://example.com/index.php/welcome/index
     * 	- or -
     * Since this controller is set as the default controller in
     * config/routes.php, it's displayed at http://example.com/
     *
     * So any other public methods not prefixed with an underscore will
     * map to /index.php/welcome/<method_name>
     * @see http://codeigniter.com/user_guide/general/urls.html
     */
    
    /*
     * Constructor de la clase en el cual se carga el modelo consultas
     * El controlador ValidadorDatos y ManejadorImagen.
     */

    const CLIENTE = 2;
    var $mensaje;
    
    public function __construct() {
        parent::__construct();
        $this->mensaje = '';
        $this->load->model('consultas');
    }

    /*
     * Funcion que carga la vista para registrar campos deportivos y recupera
     * los campos registrados, los tipos de cancha y los tipos de suelo.
     */

    public function index() {
        session_start();
        $datos['mensaje'] = $this->mensaje;
        if(!empty($_SESSION['usuario'])){
            $this->bienvenida();
        }
        else{
            $this->load->view('inicio_sesion', $datos);
        }
            
    }
    
    /*
     * Funcion que da la bienvenida al usuario
     */
    
    public function bienvenida() {
        $datos['usuario'] = $_SESSION['usuario'];
        $datos['menus'] = $this->consultas->menus($_SESSION['rol']);
        
        if($_SESSION['rol'] == 'Cliente') {
            $datos['reservas'] = $this->consultas->reservas_cliente($_SESSION['usuario']);
        }
        
        $this->load->view('bienvenido_usuario', $datos);
    }

    /*
     * Funcion que procesa los datos para iniciar sesion
     */

    public function solicitud_sesion() {
        $nombre_usuario = $this->input->post('nombre_usuario');
        $ci = $this->input->post('ci');
        $this->iniciar_sesion($nombre_usuario, $ci);
    }
    
    /*
    * Funcion que ve si los datos son correctos pa iniciar la sesion.
    */

    public function iniciar_sesion($nombre_usuario, $ci) {
        if($this->consultas->sesion_exitosa($nombre_usuario, $ci)){
            session_start();
            $_SESSION['usuario'] = $nombre_usuario;
            $_SESSION['rol'] = $this->consultas->rol_usuario($nombre_usuario);
            $this->bienvenida();
        }
        else{
            $this->mensaje = 'Los datos son incorrectos.';
            $this->index();
        }
    }
    
    /*
    *   Funcion que cierra la sesion 
    */

    public function cerrar_sesion() {
        session_start();
        session_destroy();
        $this->index();
    }

    /*
    *   Funcion que muestra el form registro. 
    */

    public function vista_registro() {
        $datos['mensaje'] = $this->mensaje;
        $this->load->view('registro_usuario', $datos);
    }
    
    /*
    *   Funcion que ve si los datos son validos para un registro. 
    */
    
    public function registrar_usuario() {
        $nombre_usuario = $this->input->post('nombre_usuario');
        $ci = $this->input->post('ci');
        $telefono = $this->input->post('telefono_referencia');
        $nombre = $this->input->post('nombre');
        $apellidos = $this->input->post('apellidos');
        
        if($this->consultas->existe_usuario($nombre_usuario)){
            $this->mensaje = "Ya existe el nombre de usuario: $nombre_usuario. "
                    . "Por favor escoja otro";
            $this->vista_registro();
        }
        else{
            $usuario = array(
                'Nombre' => $nombre,
                'CarnetIdentidad' => $ci,
                'TelefonoReferencia' => $telefono,
                'Apellidos' => $apellidos,
                'NombreUsuario' => $nombre_usuario,
                'Rol' => self::CLIENTE
            );
            $this->consultas->registrar_usuario($usuario);
            $this->iniciar_sesion($nombre_usuario, $ci);
        }
        
    }

}
