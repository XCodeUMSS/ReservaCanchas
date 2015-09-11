<?php

defined('BASEPATH') OR exit('No direct script access allowed');

/**
 * Clase encargada de administrar las prereservas
 *
 * @author Beimar & Alison
 */
class ControladorPrereservas extends CI_Controller {

    private $validador;
    var $reservas;
    var $mensaje;

    //Constantes a ser usadas como valores por defecto en los campos necesarios
    const RESERVA_ESPECIAL = false;
    const REPETICION_NINGUNA = 1;
    const REPETICION_DIARIA = 2;
    const REPETICION_SEMANAL = 3;
    const REPETICION_MENSUAL = 4;
    //Constante para la confirmacion de las reservas
    const CONFIRMADO = false;

    var $codigo;
    public $idcampo;

    public function __construct() {
        parent::__construct();
        $this->load->model('consultas');
        $this->load->model('Prereserva');
        include(APPPATH . 'controllers/ValidadorDatos.php');
        $this->validador = new ValidadorDatos();
        $this->mensaje = '';
    }
    
    
    public function index(){
        session_start();
        $this->mostrarFormulario();
    }

    /*
     * Funcion que registra las prereservas en caso de que sus datos sean validos,
     * no existan colisiones con las reservas ya registradas.
     */

    public function prereservar() {
        $this->reservas = new ArrayObject(array());
        if ($this->realizarPrereserva()) {
            for ($i = 0; $i < count($this->reservas); $i++) {
                $this->consultas->registrar_reserva($this->reservas[$i]->datos());
            }
            $this->mensaje = "Su reserva fue exitosamente registrada, usted debe "
                    . "pagar el precio de la reserva en un plazo de dos dias,"
                    . "caso contrario se eliminara su reserva.";
        }
        $this->mostrarFormulario();
    }

    /**
     * Realiza las prereservas
     * @return type
     */
    public function realizarPrereserva() {
        session_start();
        $id_campo = $this->input->post('campo_deportivo');
        $this->idcampo = $id_campo;
        $fecha = $this->formatear_fecha($this->input->post('fecha_reserva'));
        $hora_inicio = $this->input->post('hora_inicio') . ':00';
        $hora_fin = $this->input->post('hora_fin') . ':00';
        $repeticion = $this->input->post('repeticion');

        $valido = false;

        $precio = $this->calcular_precio($id_campo, $hora_inicio, $hora_fin);

        $mensaje = '';
        $mensaje .= $this->validador->datos_validos_reserva($_SESSION['usuario'],
                $fecha, $hora_inicio, $hora_fin);
        $mensaje .= $this->dentro_horarios_atencion($id_campo, $hora_inicio, 
                $hora_fin);
        $mensaje .= $this->realizar($id_campo, $fecha, $hora_inicio, $hora_fin, 
                $precio, $repeticion);

        $valido = $mensaje == '';

        $this->mensaje = $mensaje;
        return $valido;
    }
    
    /**
     * Muestra los Detalles de las canchas hacia los usuarios
     */
    public function mostrarDetallesCanchas() {
        session_start();
        $datos['canchas'] = $this->consultas->campos_registrados();
        $datos['menus'] = $this->consultas->menus($_SESSION['rol']);
        $this->load->view('vista_detalles_canchas', $datos);
    }

    /**
     * Realiza el renderizado de la vista de prereservas
     */
    public function mostrarFormulario() {
        if (isset($_REQUEST['cod'])) {
            $campo = $_REQUEST['cod'];
            $this->codigo = $campo;
        } else {
            $this->codigo = $this->idcampo;
        }

        
        $datos_usuario = $this->consultas->datos_usuario($_SESSION['usuario']);
        $_SESSION['telefono'] = $datos_usuario->telefono;
        $datos['repeticiones'] = $this->consultas->tipos_repeticion();
        $datos['mensaje'] = $this->mensaje;
        $datos['idCampo'] = $this->codigo;
        $datos['nombreCampo'] = $this->consultas->obtenerNombreCampo($this->codigo);
        $datos['menus'] = $this->consultas->menus($_SESSION['rol']);
        $datos['usuario'] = $_SESSION['usuario'];
        $datos['telefono'] = $datos_usuario->telefono;
        $this->load->view('vista_prereserva', $datos);
    }

    /*
     * Funcion que verifica si existe colision entre reserva que se desea
     * registrar y sus repeticiones.
     */

    public function realizar($id_campo, $fecha, $hora_inicio, $hora_fin, 
            $precio, $repeticion) {

        $mensaje = $this->consultas->existe_reserva($id_campo, $fecha, 
                $hora_inicio, $hora_fin) ? "- Existe una reserva." : '';
        $reserva = new Prereserva();

        $fechaExpiracion = $this->obtenerFechaExpiracion();
        $horaExpiracion = $this->obtenerHoraExpiracion();
        $reserva->actualizar($_SESSION['usuario'], $_SESSION['telefono'], 
                $id_campo, $fecha, $hora_inicio, $hora_fin, $precio, 
                self::RESERVA_ESPECIAL, $fechaExpiracion, $horaExpiracion, 
                self::CONFIRMADO);
        $this->reservas->append($reserva);
        $fecha_formato = DateTime::createFromFormat("d/m/Y", $fecha);
        $fecha_limite = date_add($fecha_formato, new DateInterval('P5M'));
        if ($mensaje == '' && $repeticion != self::REPETICION_NINGUNA) {
            switch ($repeticion) {
                case self::REPETICION_DIARIA:
                    $mensaje = $this->realizar_repeticion($precio, $id_campo, 
                            $fecha, $fecha_limite, 'P1D', $hora_inicio, $hora_fin);
                    break;
                case self::REPETICION_SEMANAL:
                    $mensaje = $this->realizar_repeticion($precio, $id_campo, 
                            $fecha, $fecha_limite, 'P7D', $hora_inicio, $hora_fin);
                    break;
                case self::REPETICION_MENSUAL:
                    $mensaje = $this->realizar_repeticion($precio, $id_campo, 
                            $fecha, $fecha_limite, 'P1M', $hora_inicio, $hora_fin);
                    break;
            }
        }
        return $mensaje;
    }

    /**
     * Obtiene la hora de la expiracion
     * @return hora
     */
    public function obtenerHoraExpiracion() {
        $today = date("H:i:s");

        return $today;
    }

    public function obtenerFechaExpiracion() {
        /**
         * zona horaria para bolivia
         */
        date_default_timezone_set('America/La_Paz');

        /**
         * crea un nuevo objeto con la fecha de hoy
         */
        $datetime = new DateTime('now');
        /*
         * Recorrer 2 dias para la expiracion
         */
        $datetime->add(new DateInterval('P2D'));

        return $datetime->format('d/m/y');
    }

    /*
     * Funcion que verifica si las repeticiones colisionan con las reservas ya
     * registradas.
     */

    public function realizar_repeticion($precio, $id_campo, $fecha, 
            $fecha_limite, $intervalo, $hora_inicio, $hora_fin) {

        $mensaje = '';
        $fecha_formato = DateTime::createFromFormat("d/m/Y", $fecha);
        $fecha_siguiente = date_add($fecha_formato, new DateInterval($intervalo));
        while ($fecha_siguiente <= $fecha_limite && $mensaje == '') {
            $mensaje = $this->consultas->existe_reserva($id_campo, 
                    $fecha_siguiente->format("d/m/Y"), $hora_inicio, 
                    $hora_fin) ? "- En la repeticion: fecha " .
                    $fecha_siguiente->format("Y/m/d") . ' existe una reserva.' : '';
            $reserva_otra = new Prereserva();
            $fechaExpiracion = $this->obtenerFechaExpiracion();
            $horaExpiracion = $this->obtenerHoraExpiracion();
            $reserva_otra->actualizar($_SESSION['usuario'], 
                    $_SESSION['telefono'], $id_campo, $fecha, $hora_inicio, 
                    $hora_fin, $precio, self::RESERVA_ESPECIAL, $fechaExpiracion,
                    $horaExpiracion, self::CONFIRMADO);
            $reserva_otra->cambiar_fecha($fecha_siguiente->format("d/m/Y"));
            $this->reservas->append($reserva_otra);
            $fecha_siguiente = date_add($fecha_siguiente, 
                    new DateInterval($intervalo));
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

    /**
     * Formatear fecha
     * @param type $fecha
     * @return type
     */
    public function formatear_fecha($fecha) {
        return date_format(date_create($fecha), 'd/m/Y');
    }

}
