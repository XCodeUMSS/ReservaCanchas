<?php

defined('BASEPATH') OR exit('No direct script access allowed');

/**
 * Esta clase realizara todo lo relacionado con las reservas
 * @version: 1.0
 * @modificado: 10 de Agosto del 2015
 * @author: Beimar & Alison
 */
class ControladorReserva extends CI_Controller {

    private $validador;

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
        $datos['canchas'] = $this->consultas->campos_registrados();
        $datos['reservas'] = $this->consultas->reservas_registradas();
        $this->load->view('vista_realizar_reserva', $datos);
    }

    /*
     * Funcion que recupera datos del formulario, en caso de que se pueda 
     * realizar la reserva, registra la reserva, caso contrario informa al
     * usuario porque no se registra la reserva.
     */

    public function reservar() {
        $nombre = $this->input->post('nombre_cliente');
        $telefono = $this->input->post('telefono_referencia');
        $id_campo = $this->input->post('campo_deportivo');
        $fecha = $this->formatear_fecha($this->input->post('fecha_reserva'));
        $hora_inicio = $this->input->post('hora_inicio') . ':00';
        $hora_fin = $this->input->post('hora_fin') . ':00';

        if ($this->realizar_reserva($nombre, $id_campo, $fecha, $hora_inicio, 
                $hora_fin)) {
            $precio = $this->calcular_precio($id_campo, $hora_inicio, $hora_fin);
            $reserva = array(
                'NombreCliente' => $nombre,
                'TelefonoReferencia' => $telefono,
                'Precio' => $precio,
                'IdCampoDeportivo' => $id_campo,
                'Fecha' => $fecha,
                'HoraInicio' => $hora_inicio,
                'HoraFin' => $hora_fin
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
        $mensaje .= $this->dentro_horarios_atencion($id_campo, $hora_inicio, 
                        $hora_fin);

        $valido = $mensaje == '';

        if (!$valido) {
            echo '<script>alert("' . $mensaje . '");</script>';
        }
        return $valido;
    }

    /*
     * Funcion que verifica si las horas de la reserva se encuentran dentro del
     * horario de atencion del campo. Si la reserva esta fuera del horario de
     * atencion se retorna un mensaje con el aviso.
     */

    public function dentro_horarios_atencion($campo, $hora_inicio, $hora_fin) {
        $horarios = $this->consultas->horarios($campo);
        $horario_inicio = $horarios->HoraInicio;
        $horario_fin = $horarios->HoraFin;
        $mensajeAlerta = '- Las horas no estan dentro de los horarios de'
                . ' atencion.';

        if ($hora_inicio >= $horario_inicio && $hora_inicio < $horario_fin &&
                $hora_fin > $horario_inicio && $hora_fin <= $horario_fin) {
            $mensajeAlerta = '';
        }

        return $mensajeAlerta;
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
     * Funcion que calcula el precio de la reserva de acuerdo al precio por 
     * hora del campo
     */

    public function calcular_precio($id_campo, $hora_inicio, $hora_fin) {
        $precio_hora = $this->consultas->precio_campo($id_campo);
        $tiempo_inicio = explode(":", $hora_inicio);
        $tiempo_fin = explode(":", $hora_fin);
        $minutos_inicio = $tiempo_inicio[0] * 60 + $tiempo_inicio[1];
        $minutos_fin = $tiempo_fin[0] * 60 + $tiempo_fin[1];
        $diferencia = $minutos_fin - $minutos_inicio;
        $precio = $precio_hora * $diferencia / 60;
        return $precio;
    }

    /*
     * Funcion que formatea la fecha a dd/mm/yyyy.
     */

    public function formatear_fecha($fecha) {
        return date_format(date_create($fecha), 'd/m/Y');
    }

}
