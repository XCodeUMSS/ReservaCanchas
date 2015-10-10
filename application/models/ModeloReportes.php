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
    
    public function reportesGanancias($gestion) {
        $consulta = $this->db->query('SELECT sum("Precio") as totalGanado, sum("NumeroReservas") as numeroReservas,EXTRACT(MONTH FROM "Fecha") as mes
FROM "Recibo"  
where EXTRACT(YEAR FROM "Fecha") = '.$gestion.'
group by EXTRACT(MONTH FROM "Fecha")
order by EXTRACT(MONTH FROM "Fecha")');
        return $consulta->result();
    }
    
    public function obtenerGestionesRecibo() {
        $consulta = $this->db->query('SELECT DISTINCT EXTRACT(YEAR FROM "Fecha") as gestion
FROM "Recibo" order by EXTRACT(YEAR FROM "Fecha") desc');
        return $consulta->result();
    }
    
    public function obtenerGestiones() {
        $consulta = $this->db->query('SELECT DISTINCT EXTRACT(YEAR FROM "Fecha") as gestion
FROM "Reserva" order by EXTRACT(YEAR FROM "Fecha") desc');
        return $consulta->result();
    }
    
    public function reporteCanchaPopular($gestion) {
        $consulta = $this->db->query('SELECT c."IdCampoDeportivo", c."Nombre",sum("Precio") as totalGanado, count("IdReserva") as numeroReservas
FROM "Reserva" as r, "CampoDeportivo" as c
where r."IdCampoDeportivo"=c."IdCampoDeportivo" and EXTRACT(YEAR FROM "Fecha") = '.$gestion.' AND "Confirmado" = true
group by c."IdCampoDeportivo"');
        return $consulta->result();
    }
}
