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
        $datos['gestiones'] = $this->modeloReportes->obtenerGestiones();
        //$datos['ganancias'] = $this->modeloReportes->reportesGanancias();
     
        $this->load->view('vista_reportes_ganancias', $datos);
    }
    
    public function canchasPopulares() {
        
        
        $datos['menus'] = $this->consultas->menus($_SESSION['rol']);
        $datos['gestiones'] = $this->modeloReportes->obtenerGestiones();
        //$datos['canchaspopulares'] = $this->modeloReportes->reporteCanchaPopular($gestion);
        $this->load->view('vista_reportes_canchaspopulares', $datos);
    }


    public function procesarPeticionGanancias() {
        $gestion = $this->input->post('gestion');
        
        $datos['gestiones'] = $this->modeloReportes->obtenerGestiones();
        $datos['reporteganancias'] = $this->modeloReportes->reportesGanancias($gestion);
        echo json_encode($datos);
    }
    
    public function procesarPeticionGananciasTabla() {
        $gestion = $this->input->post('gestion');
       
        $datos['ganancias'] = $this->modeloReportes->reportesGanancias($gestion);
        $this->load->view('plantillas/reporte_ganancias', $datos);
    }
    
    public function procesarPeticionCanchasPopulares() {
        $gestion = $this->input->post('gestion');
        
        $datos['canchaspopulares'] = $this->modeloReportes->reporteCanchaPopular($gestion);
        echo json_encode($datos);
    }
    
    public function procesarPeticionCanchasPopularesTabla() {
        $gestion = $this->input->post('gestion');
        
        $datos['canchaspopulares'] = $this->modeloReportes->reporteCanchaPopular($gestion);
        $this->load->view('plantillas/reporte_canchas_populares', $datos);
    }
}
