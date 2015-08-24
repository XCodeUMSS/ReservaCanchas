<?php

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Esta clase realizara todo lo relacionado con las repeticione de reservas
 * @version: 1.0
 * @modificado: 24 de Agosto del 2015
 * @author Alison
 */
class ManejadorRepeticion extends CI_Controller{
    
    const REPETICION_NINGUNA = 1;
    const REPETICION_DIARIA = 2;
    const REPETICION_SEMANAL = 3;
    const REPETICION_MENSUAL = 4;
    
    
    public function __construct() {
        parent::__construct();
        $this->load->model('Consultas');
    }
    
    /*
     * Funcion que valida si se puede realizar una reserva con o sin repeticion
     * tomando en cuenta las ya registradas y sus repeticiones.
     */
    public function realizar($id_campo, $fecha, $hora_inicio, $hora_fin, 
            $repeticion){
        $mensaje = $this->existe_reserva($id_campo, $fecha, $hora_inicio, 
                $hora_fin);
        $fecha_formato = DateTime::createFromFormat("d/m/Y", $fecha);
        $fecha_limite = date_add($fecha_formato, new DateInterval('P5M'));
        
        if($mensaje == ''){
            switch ($repeticion){
            case self::REPETICION_DIARIA:
                $mensaje = $this->realizar_repeticion($id_campo, $fecha, 
                        $fecha_limite, 'P1D', $hora_inicio, $hora_fin); 
                break;
            case self::REPETICION_SEMANAL:
                $mensaje = $this->realizar_repeticion($id_campo, $fecha, 
                        $fecha_limite, 'P7D', $hora_inicio, $hora_fin); 
                break;
            case self::REPETICION_MENSUAL:
                $mensaje = $this->realizar_repeticion($id_campo, $fecha, 
                        $fecha_limite, 'P1M', $hora_inicio, $hora_fin); 
                break;
            }
        }
        
        return $mensaje;
    }
    
    /*
     * Funcion que valida si se puede realizar las repeticiones de una reserva,
     * dado el intervalo que se repiten y la reservas con sus repeticiones ya
     * registradas.
     */
    
    public function realizar_repeticion($id_campo, $fecha, $fecha_limite,
            $intervalo, $hora_inicio, $hora_fin) {
        
        $fecha_formato = DateTime::createFromFormat("d/m/Y", $fecha);
        $fecha_siguiente = date_add($fecha_formato, new DateInterval($intervalo));
        $mensaje = '';
        while($fecha_siguiente < $fecha_limite && $mensaje == ''){
            $mensaje = $this->existe_reserva($id_campo, 
                    $fecha_siguiente->format("d/m/Y"), $hora_inicio, $hora_fin);
            $fecha_siguiente = date_add($fecha_siguiente, 
                    new DateInterval($intervalo));
        }
        
        if ($mensaje != '') {
            $mensaje .= 'en la repeticion.';
        }

        return $mensaje;
    }
    
    /*
     * Funcion que valida si existe una reserva o alguna repeticion que pueda
     * colisionar con la reserva del cliente.
     */
    
    public function existe_reserva($id_campo, $fecha, $hora_inicio, $hora_fin) {
        $mensaje_alerta = '';
        $reservas_comunes = $this->Consultas->existe_reservas($id_campo, 
                $hora_inicio, $hora_fin);
        foreach ($reservas_comunes as $reserva) {
            $fecha_formato = DateTime::createFromFormat("d/m/Y", 
                    $fecha)->format("Y-m-d");
            
            switch ($reserva->Repeticion) {
                case self::REPETICION_NINGUNA:
                    $mensaje_alerta = $fecha_formato == $reserva->Fecha ? 
                        '-Existe reserva ' : '';
                    break;

                case self::REPETICION_DIARIA:
                    $mensaje_alerta = $this->existe_reserva_repeticion($reserva,
                            $fecha_formato, 'P1D');
                    break;
                
                case self::REPETICION_SEMANAL:
                    $mensaje_alerta = $this->existe_reserva_repeticion($reserva,
                            $fecha_formato, 'P7D');
                    break;
                
                case self::REPETICION_MENSUAL:
                    $mensaje_alerta = $this->existe_reserva_repeticion($reserva, 
                            $fecha_formato, 'P1M');
                    break;
            }
            if ($mensaje_alerta != '') {
                break;
            }
        }
        return $mensaje_alerta;
    }
    
    /*
     * Funcion que valida si se puede realizar una reserva dadas las
     * repeticiones de una reserva.
     */
    public function existe_reserva_repeticion($reserva, $fecha, $intervalo) {
        $fecha_formato = DateTime::createFromFormat("Y-m-d", $reserva->Fecha);
        $mensaje = ($fecha == $reserva->Fecha) ? '-Existe reserva ' : '';
        $fecha_siguiente = date_add($fecha_formato, new DateInterval($intervalo));
        $fecha_siguiente_cadena = $fecha_siguiente->format("Y-m-d");
        $fecha_limite = $reserva->FechaFinal;
        
        while($fecha_siguiente_cadena <= $fecha_limite && $mensaje == ''){
            $mensaje = ($fecha == $fecha_siguiente_cadena) ? 
                    '-Existe reserva ' : '';
            $fecha_siguiente = date_add($fecha_siguiente, 
                    new DateInterval($intervalo));
            $fecha_siguiente_cadena = $fecha_siguiente->format("Y-m-d");
        }
        return $mensaje;
    }
}
