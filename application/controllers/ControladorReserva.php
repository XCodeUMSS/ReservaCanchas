<?php
defined('BASEPATH') OR exit('No direct script access allowed');

/**
 * Esta clase realizara todo lo relacionado con las reservas
 *
 * @author Beimar & Alison
 */
class ControladorReserva extends CI_Controller {
    
    private $validador;
    
    public function __construct() {
        parent::__construct();
        $this->load->model('consultas');
        include(APPPATH.'controllers/ValidadorDatos.php');
        $this->validador = new ValidadorDatos();
    }
    
    public function index() {
        $datos['canchas'] = $this->consultas->getCamposRegistrados();
        $datos['reservas'] = $this->consultas->getReservasRegistradas();
        $this->load->view('vista_realizar_reserva', $datos);
    }
    
    public function reservar() {
        $nombre = $this->input->post('nombre_cliente');
        $telefono = $this->input->post('telefono_referencia');
        $idCampo = $this->input->post('campo_deportivo');
        $fecha = date_format(date_create($this->input->post('fecha_reserva')), 
                "d/m/Y");
        $horaInicio = $this->input->post('hora_inicio').":00";
        $horaFin = $this->input->post('hora_fin').":00";
        
        if($this->realizarReserva($nombre, $idCampo, $fecha, $horaInicio, $horaFin)){
            $precio = $this->calcularPrecio($idCampo, $horaInicio, $horaFin);
            $reserva = array(
                'NombreCliente' => $nombre,
                'TelefonoReferencia' => $telefono,
                'Precio' => $precio,
                'IdCampoDeportivo' => $idCampo,
                'Fecha' => $fecha,
                'HoraInicio' => $horaInicio,
                'HoraFin' => $horaFin
            );
            $this->consultas->registrarReserva($reserva);
        }
        $this->index();
    }
    
    public function realizarReserva($nombre, $idCampo, $fecha, $horaInicio, 
            $horaFin){
        $mensaje = "";
        $mensaje .= $this->validador->datosValidosReserva($nombre, 
                $fecha, $horaInicio, $horaFin);
        $mensaje .= $this->existeReserva($idCampo, $fecha, $horaInicio, 
                $horaFin);
        $mensaje .= $this->dentroHorariosAtencion($idCampo, $horaInicio, 
                $horaFin);
        
        $valido = $mensaje == "";
        
        if(!$valido){
            echo '<script>alert("'.$mensaje.'");</script>';
        }
        return $valido;
    }

    public function dentroHorariosAtencion($campo, $horaInicio, $horaFin) {
        $horarios = $this->consultas->horarios($campo);
        $horarioIni = $horarios->HoraInicio;
        $horarioFin = $horarios->HoraFin;
        return $horaInicio >= $horarioIni && $horaInicio < $horarioFin &&
                $horaFin > $horarioIni && $horaFin <= $horarioFin ? "" : "- Las"
                . " horas no estan dentro de los horarios de atencion. ";
    }

    public function existeReserva($idCampo, $fecha, $horaInicio, $horaFin) {
        return $this->consultas->existeReserva($idCampo, $fecha, $horaInicio, 
                $horaFin) ? "- Existe una reserva." : "";
    }

    public function calcularPrecio($idCampo, $horaInicio, $horaFin) {
        $precioPorHora = $this->consultas->getPrecioCampo($idCampo);
        $tiempoIni = explode(":", $horaInicio);
        $tiempoFin = explode(":", $horaFin);
        $minutosIni = $tiempoIni[0] * 60 + $tiempoIni[1];
        $minutosFin = $tiempoFin[0] * 60 + $tiempoFin[1];
        $diferencia = $minutosFin - $minutosIni;
        $precio = $precioPorHora * $diferencia / 60;
        return $precio;
    }
}
