/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tucanchaya.controller;

import com.tucanchaya.entities.Centrodeportivo;
import com.tucanchaya.entities.Ciudad;
import com.tucanchaya.facade.CentrodeportivoFacade;
import com.tucanchaya.facade.CiudadFacade;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;

/**
 *
 * @author aranda
 */
@ManagedBean(name="sportCenterController")
@ViewScoped
public class SportCenterController implements Serializable
{
    
    @EJB
    private CentrodeportivoFacade sportCenterEJB;
    @EJB
    private CiudadFacade cityEJB;
    
    private List<Ciudad> cities;
    private Ciudad city;
    
    private List<Centrodeportivo> sportCenters;
    
    @PostConstruct
    public void init()
    {
        if(cities== null)
        {
            cities = cityEJB.findAllOderByName("ciunombre");
        }
        for(Ciudad c: cities)
        {
            if(c.getCiuPorDefecto())
            {
                city = c;
                break;
            }
        }
    }
    
    public List<Centrodeportivo> getSportCenters()
    {
        if(sportCenters== null)
        {
            sportCenters = sportCenterEJB.findByCityOrderByName(city.getCiuId());
        }
        return sportCenters;
    }
    
    public List<Ciudad> getCities()
    {
        return cities;
    }

    public Ciudad getCity() {
        return city;
    }

    public void setCity(Ciudad city) {
        this.city = city;
    }
    
    
    public void changeCity(ValueChangeEvent event)
    {
        city = (Ciudad)event.getNewValue();
        sportCenters = null;
    }
    
}
