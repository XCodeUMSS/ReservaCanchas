<?php

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Description of ControladorCanchas
 *
 * @author Alison
 */
class ControladorCanchas extends CI_Controller{
    
    private $manejador;
    private $validador;
    var $mensaje;

    /*
     * Constructor de la clase en el cual se carga el modelo consultas
     * El controlador ValidadorDatos y ManejadorImagen.
     */

    public function __construct() {
        parent::__construct();
        $this->load->model('consultas');
        include(APPPATH . 'controllers/ManejadorImagen.php');
        include(APPPATH . 'controllers/ValidadorDatos.php');
        $this->manejador = new ManejadorImagen();
        $this->validador = new ValidadorDatos();
        $this->mensaje = '';
    }

    /*
     * Funcion que carga la vista para registrar campos deportivos y recupera
     * los campos registrados, los tipos de cancha y los tipos de suelo.
     */

    public function index() {
        session_start();
        $datos['canchas'] = $this->consultas->campos_registrados();
        $datos['tipos_cancha'] = $this->consultas->tipos_cancha();
        $datos['tipos_suelo'] = $this->consultas->tipos_suelo();
        $datos['mensaje'] = $this->mensaje;
        $datos['menus'] = $this->consultas->menus($_SESSION['rol']);
        $this->load->view('vista_agregar_cancha', $datos);
    }

    /*
     * Funcion que recupera los datos del formulario y si se puede realizar
     * el registro, registrar el campo deportivo, sino informa al usuario 
     * porque no se pudo registrar.
     */

    public function agregar_campo() {
        $hora_inicio = $this->input->post('hora_inicio');
        $hora_fin = $this->input->post('hora_fin');
        $nombre = $this->input->post('nombre_cancha');
        $precio_minimo = $this->input->post('precio_hora');
        $id_tipo_cancha = $this->input->post('tipo_cancha');

        if ($this->realizar_registro($nombre, $hora_inicio, $hora_fin, 
                $precio_minimo, $id_tipo_cancha)) {
            $ruta = $this->manejador->guardar_imagen($nombre);
            $campo = array(
                'Nombre' => $nombre,
                'RutaFoto' => $ruta,
                'PrecioPorHora' => $precio_minimo,
                'IdTipoCancha' => $id_tipo_cancha,
                'IdTipoSuelo' => $this->input->post('tipo_suelo')
            );
            $this->consultas->registrar_campo($campo, $hora_inicio, $hora_fin);
            $this->mensaje = "Se registro exitosamente el campo deportivo: $nombre";
        }
        $this->index();
    }

    /*
     * Funcion que verifica si existe ya el campo deportivo y si los datos son
     * validos.
     */

    public function realizar_registro($nombre, $hora_inicio, $hora_fin, 
            $precio, $id_tipo) {
        $mensaje = '';
        $precio_minimo = $this->consultas->precio_tipo_cancha($id_tipo);
        if ($this->consultas->existe_nombre($nombre)) {
            $mensaje .= '- Este nombre ya existe. ';
        }
        $mensaje .= $this->validador->datos_validos_campo($nombre, 
                $hora_inicio, $hora_fin, $precio_minimo, $precio);

        $valido = $mensaje == '';
        $this->mensaje = $mensaje;
        return $valido;
    }
}
