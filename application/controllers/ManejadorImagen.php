<?php

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Esta clase realizara todo lo relacionado con cargar imagen
 * @version: 1.0
 * @modificado: 10 de Agosto del 2015
 * @author: Alison
 */
class ManejadorImagen extends CI_Controller {

    public function __construct() {
        parent::__construct();
    }

    /*
     * Funcion que guarda una imagen en la carpeta ./assets/img/
     */

    public function guardar_imagen($nombre) {
        $extension = pathinfo($_FILES["imagen"]["name"], PATHINFO_EXTENSION);
        $ruta = './assets/img/' . $nombre . '.' . $extension;
        if (is_uploaded_file($_FILES["imagen"]["tmp_name"])) {
            move_uploaded_file($_FILES["imagen"]["tmp_name"], $ruta);
        } else {
            $ruta = './assets/img/default.png';
        }
        return $ruta;
    }

}
