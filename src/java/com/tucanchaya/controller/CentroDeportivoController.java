/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tucanchaya.controller;

import com.tucanchaya.entities.Ciudad;
import com.tucanchaya.facade.CiudadFacade;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 *
 * @author aranda
 */
@Named("centroDeportivoController")
@SessionScoped
public class CentroDeportivoController implements Serializable{
    
    private Ciudad city;
    @EJB
    private CiudadFacade cityEJB;

    public Ciudad getCity() {
        if(city == null)
            city= cityEJB.findDefaultCity();
        return city;
    }

    public void setCity(Ciudad city) {
        this.city = city;
    }
    
    public void changeCity(Ciudad city)
    {
        this.city = city;
        
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/Tucanchaya");
        } catch (IOException ex) {
            Logger.getLogger(CentroDeportivoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
