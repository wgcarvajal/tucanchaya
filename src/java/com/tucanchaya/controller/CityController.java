/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tucanchaya.controller;

import com.tucanchaya.entities.Ciudad;
import com.tucanchaya.entities.Ciudad_;
import com.tucanchaya.facade.CiudadFacade;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.PrimeFaces;

/**
 *
 * @author Wilson Carvajal
 */
@ManagedBean
@ViewScoped
public class CityController implements Serializable
{
    
    @EJB
    private CiudadFacade cityEJB;
    private List<Ciudad> cities;
    private String city;
    
    public List<Ciudad> getCities()
    {
        if(cities== null)
        {
            cities = cityEJB.findAllOderByName("ciunombre");
        }
        return cities;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
    
    public void searchCity()
    {
       cities = cityEJB.findByColumn(city);
    }
    
}
