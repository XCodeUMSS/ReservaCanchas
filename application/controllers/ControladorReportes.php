<?php

/**
 * Description of ControladorReportes
 *
 * @author Beimar
 */
class ControladorReportes extends CI_Controller{
    public function __construct() {
        session_start();
        parent::__construct();
        $this->load->model('modeloReportes');
        $this->load->model('consultas');
    }
    
    public function ganancias() {
        $datos['menus'] = $this->consultas->menus($_SESSION['rol']);
        //$datos['gestiones'] = $this->modeloReportes->obtenerGestiones();
        $datos['ganancias'] = $this->modeloReportes->reportesGanancias();
    
        $this->load->view('vista_reportes_ganancias', $datos);
    }
    
    public function canchasPopulares() {
        $datos['menus'] = $this->consultas->menus($_SESSION['rol']);
        //$datos['gestiones'] = $this->modeloReportes->obtenerGestiones();
        $datos['canchaspopulares'] = $this->modeloReportes->reporteCanchaPopular();
        $this->load->view('vista_reportes_canchaspopulares', $datos);
    }


    public function procesarPeticionGanancias() {
        $datos['reporteganancias'] = $this->modeloReportes->reportesGanancias();
        echo json_encode($datos);
    }
    
    public function procesarPeticionCanchasPopulares() {
        $datos['canchaspopulares'] = $this->modeloReportes->reporteCanchaPopular();
        echo json_encode($datos);
    }
}
