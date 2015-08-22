<?php

/**
 * Clase que se encarga de comunicarse con la base de datos para luego asi retornar
 * los datos mediante ajax
 *
 * @author Beimar
 */
class ModeloFiltracionDatos extends CI_Model {
    
    /*
     * Constructor de la clase en el cual se carga la base de datos.
     */

    public function __construct() {
        parent::__construct();
        $this->load->database();
    }
    
    /*
     * Realiza la filtracin deacuerdo a la fecha de reserva y retorna los resultados
     * como un array asociativo.
     */
    
    public function fechas_registradas($fecha_reserva) {
        $fecha = $this->formatear_fecha($fecha_reserva);
        
        $this->db->select('r.NombreCliente AS nombre, '
                . 'r.TelefonoReferencia AS telefono, r.Fecha AS fecha, '
                . 'r.HoraInicio AS horaInicio, r.HoraFin AS horaFin, '
                . 'c.Nombre AS campo');
        $this->db->from('Reserva AS r, CampoDeportivo as c');
        $this->db->where("r.IdCampoDeportivo = c.IdCampoDeportivo".
                "AND r.Fecha = '".$fecha."'");
        $consulta = $this->db->get();
        
        
        
        return $consulta->result();
    }
    
    /*
     * Realiza la filtracin deacuerdo al campo y retorna los resultados
     * como un array asociativo.
     */
    
    public function reservas_registradas($id_campo) {
        
        $this->db->select('r.NombreCliente AS nombre, '
                . 'r.TelefonoReferencia AS telefono, r.Fecha AS fecha, '
                . 'r.HoraInicio AS horaInicio, r.HoraFin AS horaFin, '
                . 'c.Nombre AS campo');
        $this->db->from('Reserva AS r, CampoDeportivo as c');
        $this->db->where("r.IdCampoDeportivo = c.IdCampoDeportivo".
                "AND r.IdCampoDeportivo = '".$id_campo."'");
        $consulta = $this->db->get();
        
        
        
        return $consulta->result();
    }
    
    
    
    /*
     * Funcion que formatea la fecha a dd/mm/yyyy.
     */

    public function formatear_fecha($fecha) {
        return date_format(date_create($fecha), 'd/m/Y');
    }
}
