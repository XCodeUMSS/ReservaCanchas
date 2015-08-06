<?php

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Description of ManejadorImagen
 *
 * @author Alison
 */
class ManejadorImagen extends CI_Controller{
    
    public function __construct() {
        parent::__construct();
    }
    
    public function cargarImagen($nombre) {
        $extension = pathinfo($_FILES["imagen"]["name"], PATHINFO_EXTENSION);
        $ruta = "./assets/img/".$nombre.".".$extension;
        if(is_uploaded_file($_FILES["imagen"]["tmp_name"])){
            move_uploaded_file ($_FILES["imagen"]["tmp_name"], $ruta);
        }
        else{
            $ruta = "./assets/img/default.png";
        }
        return $ruta;
    }
}
