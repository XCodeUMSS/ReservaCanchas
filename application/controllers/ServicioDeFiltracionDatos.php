<?php

defined('BASEPATH') OR exit('No direct script access allowed');

/**
 * Esta clase brindara servicio de datos a las peticiones ajax, en la filtracion
 * de campos deportivos y fechas
 * @version: 1.0
 * @modificado: 20 de Agosto del 2015
 * @author: Beimar
 */
class ServicioDeFiltracionDatos extends CI_Controller {
    
    
    public function __construct() {
        parent::__construct();
        $this->load->model('modeloFiltracionDatos');
    }
    
    /**
     * Procesa las peticiones de la filtracion mediante fecha
     */
    public function procesarPeticionFecha() {
        $fecha_reserva = $this->input->post('fecha_reserva');
        
        $datos['reservas'] = null;
        
        if($fecha_reserva) {
            $datos['reservas'] = $this->obtenerDatosFecha($fecha_reserva);
        
            $this->enviarDatos($datos);
        }
    }


    /**
     * Funcion que procesa la peticion ajax del cliente
     * filtrando los campos reservados
     */
    
    public function procesarPeticionAjax() {
        $id_campo_deportivo = $this->input->post('id_campo_deportivo');
        $fecha_reserva = $this->input->post('fecha_reserva');
        $datos['reservas'] = null;
        
        if($id_campo_deportivo && $fecha_reserva) {
            $datos['reservas'] = $this->obtenerDatos($id_campo_deportivo, $fecha_reserva);
        
            $this->enviarDatos($datos);
        }
    }
    
    public function procesarHorarios() {
        $id_campo_deportivo = $this->input->post('id_campo_deportivo');
        $fecha_reserva = $this->input->post('fecha_reserva');
        $datos = null;
        
        if($id_campo_deportivo && $fecha_reserva) {
            
            $datos['horarios'] = $this->modeloFiltracionDatos->obtenerFechas($id_campo_deportivo, $fecha_reserva);
            $datos['horariosAtencion']  = $this->modeloFiltracionDatos->obetenerHorariosAtencion($id_campo_deportivo);
            
            echo json_encode($datos);
        }
    }


    /**
     * Obtiene los datos del modelo del servicio
     * @param type $fecha_reserva
     * @return void
     */
    public function obtenerDatosFecha($fecha_reserva) {
        return $this->modeloFiltracionDatos->fechas_registradas($fecha_reserva);
    }
    
    
    /**
     * Obtiene los datos del modelo del servicio
     * @param type $id_campo_deportivo
     * @return void
     */
    public function obtenerDatos($id_campo_deportivo, $fecha) {
        return $this->modeloFiltracionDatos->reservas_registradas($id_campo_deportivo, $fecha);
    }

    /**
     * Envia los datos al cliente en formato html para que se puede renderizar
     * @param type $datos
     */
    public function enviarDatos($datos) {
        $this->load->view('plantillas/respuesta_cuerpo_tabla', $datos); 
    }
}
