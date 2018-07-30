/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paquetepruebas;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class PruebaManagegbean implements Serializable
{
    
    public List<String> getlist()
    {
        List<String> list = new ArrayList();
        list.add("hola1");
        list.add("hola2");
        list.add("hola3");
        list.add("hola4");
        list.add("hola5");
        list.add("hola6");
        list.add("hola7");
        
        return list;
    }
}
