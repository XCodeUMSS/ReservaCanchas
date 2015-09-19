<?php

defined('BASEPATH') OR exit('No direct script access allowed');

/**
 * Esta clase realizara todo lo relacionado con las reservas
 * @version: 1.0
 * @modificado: 10 de Agosto del 2015
 * @author: Beimar & Alison & Walter
 */
class ControladorReserva extends CI_Controller {

    private $validador;
    var $reservas;
    var $mensaje;

    //Constantes a ser usadas como valores por defecto en los campos necesarios
    const RESERVA_ESPECIAL = false;
    const REPETICION_NINGUNA = 1;
    const REPETICION_DIARIA = 2;
    const REPETICION_SEMANAL = 3;
    const REPETICION_MENSUAL = 4;

    /*
     * Constructor de la clase en el cual se carga el modelo consultas
     * Y el controlador ValidadorDatos.
     */

    public function __construct() {
        parent::__construct();
        $this->load->model('consultas');
        $this->load->model('Reserva');
        include(APPPATH . 'controllers/ValidadorDatos.php');
        $this->validador = new ValidadorDatos();
        $this->mensaje = '';
    }

    public function hola() {
        echo "Beimar huarachi jdsklfjakljsldfasd";
    }


    /*
     * Funcion que carga la vista principal de realizar reserva
     * Y se recupera de consultas, los campos deportivos registrados y
     * las reservas registradas.
     */

    public function index() {
        session_start();
        $datos['canchas'] = $this->consultas->campos_registrados();
        $datos['reservas'] = $this->consultas->reservas_registradas();
        $datos['repeticiones'] = $this->consultas->tipos_repeticion();
        $datos['mensaje'] = $this->mensaje;
        $datos['menus'] = $this->consultas->menus($_SESSION['rol']);
        $this->load->view('vista_realizar_reserva', $datos);
    }

    /*
     * Funcion que registra las reservas en caso de que sus datos sean validos,
     * no existan colisiones con las reservas ya registradas.
     */

    public function reservar() {
        $this->reservas = new ArrayObject(array());
        if ($this->realizar_reserva()) {
            for ($i = 0; $i < count($this->reservas); $i++) {
                $this->consultas->registrar_reserva($this->reservas[$i]->datos());
            }
            $this->mensaje = "Su reserva fue exitosamente registrada.";
        }
        $this->index();
    }

    /*
     * Funcion que recupera los datos del formulario y 
     * determina si se puede realizar una reserva con los datos.
     */

    public function realizar_reserva() {
        $nombre = $this->input->post('nombre_cliente');
        $telefono = $this->input->post('telefono_referencia');
        $id_campo = $this->input->post('campo_deportivo');
        $fecha = $this->formatear_fecha($this->input->post('fecha_reserva'));
        $hora_inicio = $this->input->post('hora_inicio') . ':00';
        $hora_fin = $this->input->post('hora_fin') . ':00';
        $repeticion = $this->input->post('repeticion');
        $valido = false;
        if ($id_campo != 'nulo') {
            $precio = $this->calcular_precio($id_campo, $hora_inicio, $hora_fin);

            $mensaje = '';
            $mensaje .= $this->validador->datos_validos_reserva($nombre, $fecha, $hora_inicio, $hora_fin);
            $mensaje .= $this->dentro_horarios_atencion($id_campo, $hora_inicio, $hora_fin);
            $mensaje .= $this->realizar($nombre, $telefono, $id_campo, $fecha, $hora_inicio, $hora_fin, $precio, $repeticion);

            $valido = $mensaje == '';
        } else {
            $mensaje = '- Debe seleccionar un campo deportivo para realizar una reserva';
        }

        $this->mensaje = $mensaje;
        return $valido;
    }

    /*
     * Funcion que verifica si existe colision entre reserva que se desea
     * registrar y sus repeticiones.
     */

    public function realizar($nombre, $telefono, $id_campo, $fecha, $hora_inicio, $hora_fin, $precio, $repeticion) {

        $mensaje = $this->consultas->existe_reserva($id_campo, $fecha, $hora_inicio, $hora_fin) ? "- Existe una reserva." : '';
        $reserva = new Reserva();
        $reserva->actualizar($nombre, $telefono, $id_campo, $fecha, $hora_inicio, $hora_fin, $precio, self::RESERVA_ESPECIAL);
        $this->reservas->append($reserva);
        $fecha_formato = DateTime::createFromFormat("d/m/Y", $fecha);
        if ($mensaje == '' && $repeticion != self::REPETICION_NINGUNA) {
            switch ($repeticion) {
                case self::REPETICION_DIARIA:
                    $fecha_limite = date_add($fecha_formato, new DateInterval('P6D'));
                    $mensaje = $this->realizar_repeticion($nombre, $telefono, $precio, $id_campo, $fecha, $fecha_limite, 'P1D', $hora_inicio, $hora_fin);
                    break;
                case self::REPETICION_SEMANAL:
                    $fecha_limite = date_add($fecha_formato, new DateInterval('P1M'));
                    $mensaje = $this->realizar_repeticion($nombre, $telefono, $precio, $id_campo, $fecha, $fecha_limite, 'P7D', $hora_inicio, $hora_fin);
                    break;
                case self::REPETICION_MENSUAL:
                    $fecha_limite = date_add($fecha_formato, new DateInterval('P4M'));
                    $mensaje = $this->realizar_repeticion($nombre, $telefono, $precio, $id_campo, $fecha, $fecha_limite, 'P1M', $hora_inicio, $hora_fin);
                    break;
            }
        }
        return $mensaje;
    }

    /*
     * Funcion que verifica si las repeticiones colisionan con las reservas ya
     * registradas.
     */

    public function realizar_repeticion($nombre, $telefono, $precio, $id_campo, $fecha, $fecha_limite, $intervalo, $hora_inicio, $hora_fin) {

        $mensaje = '';
        $fecha_formato = DateTime::createFromFormat("d/m/Y", $fecha);
        $fecha_siguiente = date_add($fecha_formato, new DateInterval($intervalo));
        while ($fecha_siguiente <= $fecha_limite && $mensaje == '') {
            $mensaje = $this->consultas->existe_reserva($id_campo, $fecha_siguiente->format("d/m/Y"), $hora_inicio, $hora_fin) ? "- En la repeticion: fecha " .
                    $fecha_siguiente->format("Y/m/d") . ' existe una reserva.' : '';
            $reserva_otra = new Reserva();
            $reserva_otra->actualizar($nombre, $telefono, $id_campo, $fecha, $hora_inicio, $hora_fin, $precio, self::RESERVA_ESPECIAL);
            $reserva_otra->cambiar_fecha($fecha_siguiente->format("d/m/Y"));
            $this->reservas->append($reserva_otra);
            $fecha_siguiente = date_add($fecha_siguiente, new DateInterval($intervalo));
        }

        return $mensaje;
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
        $mensaje_alerta = '- Las horas no estan dentro de los horarios de'
                . ' atencion.';

        if ($hora_inicio >= $horario_inicio && $hora_inicio < $horario_fin &&
                $hora_fin > $horario_inicio && $hora_fin <= $horario_fin) {
            $mensaje_alerta = '';
        }

        return $mensaje_alerta;
    }

    /*
     * Funcion que calcula el precio de la reserva, dados los horarios
     */

    public function calcular_precio($campo, $hora_inicio, $hora_fin) {
        $precio_hora = $this->consultas->precio_campo($campo);
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
