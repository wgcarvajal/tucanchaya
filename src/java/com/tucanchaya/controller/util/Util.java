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
    
    
    public static String formatText(String value)
    {
        value = value.trim();
        value = value.substring(0, 1).toUpperCase() + value.substring(1).toLowerCase();
        return value;
        
    }
    
    
}
