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
    public function __construct() {
        parent::__construct();
        $this->load->model('consultas');
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
        
        if($this->datosValidos($nombre, $horaInicio, $horaFin, $precioMinimo, 
                $idTipoCancha)){
            include(APPPATH.'controllers/ManejadorImagen.php');
            $manejador = new ManejadorImagen();
            $ruta = $manejador->cargarImagen($nombre);
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

    public function datosValidos($nombre, $horaInicio, $horaFin, $precio, 
            $idTipo) {
        $valido = true;
        $mensaje = "";
        if($this->consultas->existeNombre($nombre)){
            $mensaje .= "- Este nombre ya existe. ";
            $valido = false; 
        }
        if ($horaInicio >= $horaFin) {
            $mensaje .= "- Los horarios no son validos. ";
            $valido = false;
        }
        
        $precioMinimo = $this->consultas->getPrecio($idTipo);
        if($precio < $precioMinimo){
            $mensaje .= "- El precio minimo es ".$precioMinimo.".";
            $valido = false;
        }
        
        if(!$valido){
            echo '<script>alert("'.$mensaje.'");</script>';
        }
        return $valido;
    }
}
