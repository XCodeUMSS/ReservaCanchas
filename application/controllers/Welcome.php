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
    
    private $manejador;
    private $validador;
    
    public function __construct() {
        parent::__construct();
        $this->load->model('consultas');
        include(APPPATH.'controllers/ManejadorImagen.php');
        include(APPPATH.'controllers/ValidadorDatos.php');
        $this->manejador = new ManejadorImagen();
        $this->validador = new ValidadorDatos();
    }

    public function index() {
        $datos['canchas'] = $this->consultas->getCamposRegistrados();
        $datos['tiposCancha'] = $this->consultas->getTiposCancha();
        $datos['tiposSuelo'] = $this->consultas->getTiposSuelo();
        $this->load->view('vista_agregar_cancha', $datos);
    }

    public function agregarCampo() {
        $horaInicio = $this->input->post('hora_inicio');
        $horaFin = $this->input->post('hora_fin');
        $nombre =$this->input->post('nombre_cancha');
        $precioMinimo = $this->input->post('precio_hora');
        $idTipoCancha = $this->input->post('tipo_cancha');
        
        if($this->registrarCampo($nombre, $horaInicio, $horaFin, 
                $precioMinimo, $idTipoCancha)){
            $ruta = $this->manejador->cargarImagen($nombre);
            $campo = array(
                'Nombre' => $nombre,
                'RutaFoto' => $ruta,
                'PrecioPorHora' => $precioMinimo,
                'IdTipoCancha' => $idTipoCancha,
                'IdTipoSuelo' => $this->input->post('tipo_suelo')
            );
            $this->consultas->registrarCampo($campo, $horaInicio, $horaFin);
        }
        $this->index();
    }

    public function registrarCampo($nombre, $horaInicio, $horaFin, $precio, 
            $idTipo) {
        $mensaje = "";
        $precioMinimo = $this->consultas->getPrecio($idTipo);
        if($this->consultas->existeNombre($nombre)){
            $mensaje .= "- Este nombre ya existe. ";
        }
        $mensaje .= $this->validador->datosValidosCampo($nombre, $horaInicio, 
                $horaFin, $precioMinimo, $precio);
        
        $valido = $mensaje == "";
        if(!$valido){
            echo '<script>alert("'.$mensaje.'");</script>';
        }
        return $valido;
    }
}
