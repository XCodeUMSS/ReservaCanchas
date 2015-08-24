<?php

defined('BASEPATH') OR exit('No direct script access allowed');

/**
 * Clase encargada de las de Reservas Especiales Basada en la Clase Controlador
 * Reserva
 * @version: 1.0
 * @modificado: 11 de Agosto del 2015
 * @author: Walter
 */
class ControladorReservaEspecial extends CI_Controller {

    private $validador;
    private $manejadorRepeticion;

    //Constantes a ser usadas como valores por defecto en los campos necesarios
    const TELEFONO_EVENTO = 0;
    const PRECIO_EVENTO = 0;
    const RESERVA_ESPECIAL = true;
    const REPETICION_NINGUNA = 0;
    
    /*
     * Constructor de la clase en el cual se carga el modelo consultas
     * Y el controlador ValidadorDatos.
     */

    public function __construct() {
        parent::__construct();
        $this->load->model('consultas');
        include(APPPATH . 'controllers/ValidadorDatos.php');
        $this->validador = new ValidadorDatos();
        include(APPPATH . 'controllers/ManejadorRepeticion.php');
        $this->manejadorRepeticion = new ManejadorRepeticion();
    }

    /*
     * Funcion que carga la vista principal de realizar reserva
     * Y se recupera de consultas, los campos deportivos registrados y
     * las reservas registradas.
     */

    public function index() {
        $datos['eventos'] = $this->consultas->tipos_evento();
        $datos['canchas'] = $this->consultas->campos_registrados();
        $datos['reservas'] = $this->consultas->reservas_registradas();
        $datos['repeticiones'] = $this->consultas->tipos_repeticion();
        $this->load->view('vista_realizar_reserva_especial', $datos);
    }

    /*
     * Funcion que recupera datos del formulario, en caso de que se pueda 
     * realizar la reserva, registra la reserva, caso contrario informa al
     * usuario porque no se registra la reserva.
     */

    public function reservar() {
        $nombre_evento = $this->input->post('nombre_evento');
        $telefono = self::TELEFONO_EVENTO;
        $id_campo = $this->input->post('campo_deportivo');
        $fecha = $this->formatear_fecha($this->input->post('fecha_reserva'));
        $hora_inicio = $this->input->post('hora_inicio') . ':00';
        $hora_fin = $this->input->post('hora_fin') . ':00';
        $repeticion = $this->input->post('repeticion');
        $fecha_fin_repeticion = date_add(DateTime::createFromFormat("d/m/Y", $fecha), 
                new DateInterval('P5M'));
        $fecha_final = $repeticion == self::REPETICION_NINGUNA ? $fecha :
                $fecha_fin_repeticion->format("d/m/Y");

        if ($this->realizar_reserva($nombre_evento, $id_campo, $fecha, $hora_inicio, 
                $hora_fin, $repeticion)) {
            $precio = self::PRECIO_EVENTO;
            $reserva = array(
                'NombreCliente' => $nombre_evento,
                'TelefonoReferencia' => $telefono,
                'Precio' => $precio,
                'IdCampoDeportivo' => $id_campo,
                'Fecha' => $fecha,
                'HoraInicio' => $hora_inicio,
                'HoraFin' => $hora_fin,
                'Repeticion' => $repeticion,
                'FechaFinal' => $fecha_final,
                'ReservaEspecial' => self::RESERVA_ESPECIAL
            );
            $this->consultas->registrar_reserva($reserva);
        }
        $this->index();
    }

    /*
     * Funcion que determina si se puede realizar una reserva con los datos.
     */

    public function realizar_reserva($nombre, $id_campo, $fecha, $hora_inicio, 
            $hora_fin, $repeticion) {
        $mensaje = '';
        $mensaje .= $this->validador->datos_validos_reserva($nombre, $fecha, 
                                    $hora_inicio, $hora_fin);
        $mensaje .= $this->manejadorRepeticion->realizar($id_campo, $fecha, 
                $hora_inicio, $hora_fin, $repeticion);

        $valido = $mensaje == '';

        if (!$valido) {
            echo '<script>alert("' . $mensaje . '");</script>';
        }
        return $valido;
    }

    /*
     * Funcion que formatea la fecha a dd/mm/yyyy.
     */

    public function formatear_fecha($fecha) {
        return date_format(date_create($fecha), 'd/m/Y');
    }

}
