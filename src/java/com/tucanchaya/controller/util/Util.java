/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tucanchaya.controller.util;

/**
 *
 * @author aranda
 */
public class Util {
    
    //public static String projectPath = "/tucanchaya";
    //public static String RUTAFOTOSCENTROSDEPORTIVOS= "/home/daniel/Escritorio/filesTucanchaya/fotosCentrosdeportivos/";
    //public static String RUTAFOTOSPRODUCTOS= "/home/daniel/Escritorio/filesTucanchaya/fotosProductos/";
    
    public static String projectPath = "/Tucanchaya";
    public static String RUTAFOTOSCENTROSDEPORTIVOS= "/Users/aranda/filesTucanchaya/fotosCentrosdeportivos/";
    public static String RUTAFOTOSPRODUCTOS= "/Users/aranda/filesTucanchaya/fotosProductos/";
    
    public static String formatText(String value)
    {
        value = value.trim();
        value = value.substring(0, 1).toUpperCase() + value.substring(1).toLowerCase();
        return value;
        
    }
    
    
}
