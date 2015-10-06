<?php
/**
 * Description of ModeloReportes
 *
 * @author Beimar
 */
class ModeloReportes extends CI_Model {
    
    public function __construct() {
        parent::__construct();
        $this->load->database();
    }
    
    public function reportesGanancias() {
        $consulta = $this->db->query('SELECT sum("Precio") as totalGanado, count("IdReserva") as numeroReservas,EXTRACT(MONTH FROM "Fecha") as mes
FROM "Reserva"  
where EXTRACT(YEAR FROM "Fecha") = 2015 AND "Confirmado" = true
group by EXTRACT(MONTH FROM "Fecha")
order by EXTRACT(MONTH FROM "Fecha")');
        return $consulta->result();
    }
    
    public function obtenerGestiones() {
        $consulta = $this->db->query('SELECT DISTINCT EXTRACT(YEAR FROM "Fecha") as gestion
FROM "Reserva"');
        return $consulta->result();
    }
    
    public function reporteCanchaPopular() {
        $consulta = $this->db->query('SELECT c."IdCampoDeportivo", c."Nombre",sum("Precio") as totalGanado, count("IdReserva") as numeroReservas
FROM "Reserva" as r, "CampoDeportivo" as c
where r."IdCampoDeportivo"=c."IdCampoDeportivo" and EXTRACT(YEAR FROM "Fecha") = 2015 AND "Confirmado" = true
group by c."IdCampoDeportivo"');
        return $consulta->result();
    }
}
