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

    //Constantes a ser usadas como valores por defecto en los campos necesarios
    const TELEFONO_EVENTO = 0;
    const PRECIO_EVENTO = 0;
    const RESERVA_ESPECIAL = true;
    
    /*
     * Constructor de la clase en el cual se carga el modelo consultas
     * Y el controlador ValidadorDatos.
     */

    public function __construct() {
        parent::__construct();
        $this->load->model('consultas');
        include(APPPATH . 'controllers/ValidadorDatos.php');
        $this->validador = new ValidadorDatos();
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
        $this->load->view('vista_realizar_reserva_especial', $datos);
    }

    /*
     * Funcion que recupera datos del formulario, en caso de que se pueda 
     * realizar la reserva, registra la reserva, caso contrario informa al
     * usuario porque no se registra la reserva.
     */

    public function reservar() {
        $id_nombre_evento = $this->input->post('nombre_evento');
        $telefono = self::TELEFONO_EVENTO;
        $id_campo = $this->input->post('campo_deportivo');
        $fecha = $this->formatear_fecha($this->input->post('fecha_reserva'));
        $hora_inicio = $this->input->post('hora_inicio') . ':00';
        $hora_fin = $this->input->post('hora_fin') . ':00';
        
        $nombre_evento = $this->consultas->nombre_evento($id_nombre_evento);

        if ($this->realizar_reserva($nombre_evento, $id_campo, $fecha, $hora_inicio, 
                $hora_fin)) {
            $precio = self::PRECIO_EVENTO;
            $reserva = array(
                'NombreCliente' => $nombre_evento,
                'TelefonoReferencia' => $telefono,
                'Precio' => $precio,
                'IdCampoDeportivo' => $id_campo,
                'Fecha' => $fecha,
                'HoraInicio' => $hora_inicio,
                'HoraFin' => $hora_fin,
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
            $hora_fin) {
        $mensaje = '';
        $mensaje .= $this->validador->datos_validos_reserva($nombre, $fecha, 
                                    $hora_inicio, $hora_fin);
        $mensaje .= $this->existe_reserva($id_campo, $fecha, $hora_inicio, 
                        $hora_fin);

        $valido = $mensaje == '';

        if (!$valido) {
            echo '<script>alert("' . $mensaje . '");</script>';
        }
        return $valido;
    }

    /*
     * Funcion que verifica si existe una reserva para el campo deseado, el
     * mismo dia y horas deseadas. Si ya existe reserva, se retorna un mensaje
     * con el aviso.
     */

    public function existe_reserva($id_campo, $fecha, $hora_inicio, $hora_fin) {
        $mensajeAlerta = '';
        if ($this->consultas->existe_reserva($id_campo, $fecha, $hora_inicio, 
                $hora_fin)) {
            $mensajeAlerta = '- Existe una reserva.';
        }

        return $mensajeAlerta;
    }

    /*
     * Funcion que formatea la fecha a dd/mm/yyyy.
     */

    public function formatear_fecha($fecha) {
        return date_format(date_create($fecha), 'd/m/Y');
    }

}
