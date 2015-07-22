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
        
        if($this->datosValidos($nombre, $horaInicio, $horaFin)){
            $extension = pathinfo($_FILES["imagen"]["name"], PATHINFO_EXTENSION);
            $ruta = "./assets/img/".$nombre.".".$extension;
            if(is_uploaded_file($_FILES["imagen"]["tmp_name"])){
                move_uploaded_file ($_FILES["imagen"]["tmp_name"], $ruta);
            }
            else{
                $ruta = "./assets/img/default.png";
            }
            $campo = array(
                'Nombre' => $nombre,
                'RutaFoto' => $ruta,
                'PrecioMinimo' => $this->input->post('precio_hora'),
                'IdTipoCancha' => $this->input->post('tipo_cancha'),
                'IdTipoSuelo' => $this->input->post('tipo_suelo')
            );
            $this->consultas->registrarCampo($campo, $horaInicio, $horaFin);
        }
        $this->index();
    }

    public function datosValidos($nombre, $horaInicio, $horaFin) {
        $valido = true;
        if($this->consultas->existeNombre($nombre)){
            echo "<script>alert ('Este nombre ya existe');</script>";
            $valido = false; 
        } else{
            if ($horaInicio >= $horaFin) {
                echo "<script>alert ('Los horarios no son validos');</script>";
                $valido = false;
            }
        }
        return $valido;
    }
}
