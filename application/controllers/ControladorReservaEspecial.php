<?php

defined('BASEPATH') OR exit('No direct script access allowed');

/**
 * Clase encargada de las de Reservas Especiales Basada en la Clase Controlador
 * Reserva
 * @version: 1.0
 * @modificado: 11 de Agosto del 2015
 * @author: Walter & Alison
 */
class ControladorReservaEspecial extends CI_Controller {

    private $validador;
    var $reservas;
    var $mensaje;

    //Constantes a ser usadas como valores por defecto en los campos necesarios
    const TELEFONO_EVENTO = 0;
    const PRECIO_EVENTO = 0;
    const RESERVA_ESPECIAL = true;
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

    /*
     * Funcion que carga la vista principal de realizar reserva
     * Y se recupera de consultas, los campos deportivos registrados y
     * las reservas registradas.
     */

    public function index() {
        session_start();
        $datos['eventos'] = $this->consultas->tipos_evento();
        $datos['canchas'] = $this->consultas->campos_registrados();
        $datos['reservas'] = $this->consultas->reservas_registradas();
        $datos['repeticiones'] = $this->consultas->tipos_repeticion();
        $datos['mensaje'] = $this->mensaje;
        $datos['menus'] = $this->consultas->menus($_SESSION['rol']);
        $this->load->view('vista_realizar_reserva_especial', $datos);
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
        $nombre = $this->input->post('nombre_evento');
        $telefono = self::TELEFONO_EVENTO;
        $id_campo = $this->input->post('campo_deportivo');
        $fecha = $this->formatear_fecha($this->input->post('fecha_reserva'));
        $hora_inicio = $this->input->post('hora_inicio') . ':00';
        $hora_fin = $this->input->post('hora_fin') . ':00';
        $repeticion = $this->input->post('repeticion');
        $precio = self::PRECIO_EVENTO;

        $mensaje = '';
        $mensaje .= $this->validador->datos_validos_reserva($nombre, $fecha, 
                $hora_inicio, $hora_fin);
        $mensaje .= $this->realizar($nombre, $telefono, $id_campo, $fecha, 
                $hora_inicio, $hora_fin, $precio, $repeticion);

        $valido = $mensaje == '';
        $this->mensaje = $mensaje;
        return $valido;
    }

    /*
     * Funcion que verifica si existe colision entre reserva que se desea
     * registrar y sus repeticiones.
     */

    public function realizar($nombre, $telefono, $id_campo, $fecha, 
            $hora_inicio, $hora_fin, $precio, $repeticion) {

        $mensaje = $this->consultas->existe_reserva($id_campo, $fecha, 
                $hora_inicio, $hora_fin) ? "- Existe una reserva." : '';
        $reserva = new Reserva();
        $reserva->actualizar($nombre, $telefono, $id_campo, $fecha, 
                $hora_inicio, $hora_fin, $precio, self::RESERVA_ESPECIAL);
        $this->reservas->append($reserva);
        $fecha_formato = DateTime::createFromFormat("d/m/Y", $fecha);
        if($mensaje == '' && $repeticion != self::REPETICION_NINGUNA){
            switch ($repeticion) {
                case self::REPETICION_DIARIA:
                    $fecha_limite = date_add($fecha_formato, new DateInterval('P6D'));
                    $mensaje = $this->realizar_repeticion($nombre, $telefono, 
                            $precio, $id_campo, $fecha, $fecha_limite, 'P1D',
                            $hora_inicio, $hora_fin);
                    break;
                case self::REPETICION_SEMANAL:
                    $fecha_limite = date_add($fecha_formato, new DateInterval('P1M'));
                    $mensaje = $this->realizar_repeticion($nombre, $telefono, 
                            $precio, $id_campo, $fecha, $fecha_limite, 'P7D', 
                            $hora_inicio, $hora_fin);
                    break;
                case self::REPETICION_MENSUAL:
                    $fecha_limite = date_add($fecha_formato, new DateInterval('P4M'));
                    $mensaje = $this->realizar_repeticion($nombre, $telefono, 
                            $precio, $id_campo, $fecha, $fecha_limite, 'P1M', 
                            $hora_inicio, $hora_fin);
                    break;
            }
        }
        return $mensaje;
    }

    /*
     * Funcion que verifica si las repeticiones colisionan con las reservas ya
     * registradas.
     */

    public function realizar_repeticion($nombre, $telefono, $precio, $id_campo, 
            $fecha, $fecha_limite, $intervalo, $hora_inicio, $hora_fin) {
        
        $mensaje = '';
        $fecha_formato = DateTime::createFromFormat("d/m/Y", $fecha);
        $fecha_siguiente = date_add($fecha_formato, new DateInterval($intervalo));
        while ($fecha_siguiente <= $fecha_limite && $mensaje == '') {
            $mensaje = $this->consultas->existe_reserva($id_campo, 
                    $fecha_siguiente->format("d/m/Y"), $hora_inicio, 
                    $hora_fin) ? "- En la repeticion: fecha " . 
                    $fecha_siguiente->format("Y/m/d"). ' existe una reserva.': '';
            $reserva_otra = new Reserva();
            $reserva_otra->actualizar($nombre, $telefono, $id_campo, $fecha, 
                    $hora_inicio, $hora_fin, $precio, self::RESERVA_ESPECIAL);
            $reserva_otra->cambiar_fecha($fecha_siguiente->format("d/m/Y"));
            $this->reservas->append($reserva_otra);
            $fecha_siguiente = date_add($fecha_siguiente, 
                    new DateInterval($intervalo));
        }

        return $mensaje;
    }

    /*
     * Funcion que formatea la fecha a dd/mm/yyyy.
     */

    public function formatear_fecha($fecha) {
        return date_format(date_create($fecha), 'd/m/Y');
    }

}
