/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tucanchaya.controller;

import com.tucanchaya.controller.util.Util;
import com.tucanchaya.entities.Barrio;
import com.tucanchaya.entities.Ciudad;
import com.tucanchaya.facade.BarrioFacade;
import com.tucanchaya.facade.CiudadFacade;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Wilson Carvajal
 */
@ManagedBean(name="neighborhoodController")
@ViewScoped
public class NeighborhoodController implements Serializable
{
    @EJB
    private BarrioFacade neighborhoodEJB;
    @EJB
    private CiudadFacade cityEJB;
    
    private List<Ciudad> cities;
    private Ciudad city;
    
    private List<Barrio> neighborhoods;
    
    private String neighborhoodName;
    private String nombre;
    
    private Barrio neighborhoodSelected;
    
    
    @PostConstruct
    public void init()
    {
        if(cities== null)
        {
            cities = cityEJB.findAllOderByName("ciuNombre");
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

    public String getNeighborhoodName() {
        return neighborhoodName;
    }

    public void setNeighborhoodName(String neighborhoodName) {
        this.neighborhoodName = neighborhoodName;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public void changeCity(ValueChangeEvent event)
    {
        city = (Ciudad)event.getNewValue();
        neighborhoods = null;
    }
    
    public List<Barrio> getNeighborhoods()
    {
        if(neighborhoods== null)
        {
            neighborhoods = neighborhoodEJB.findByCityOrderByName(city.getCiuId());
        }
        return neighborhoods;
    }
    
    public void searchNeighborhood()
    {
       neighborhoods = neighborhoodEJB.findByCityAndNameOrderByName(city.getCiuId(),neighborhoodName);
    }
    
    public void openRegisterNeighborhoodDialog()
    {
       RequestContext requestContext = RequestContext.getCurrentInstance();
       this.nombre = "";
       requestContext.execute("PF('addNeighborhoodDialog').show()");
    }
    
    public void saveNeighborhood()
    {
        if(!nombre.equals(""))
        {
            nombre = Util.formatText(nombre);
            if (!neighborhoodEJB.existNeighborhoodName(city.getCiuId(),nombre)) {
                
                Barrio b = new Barrio();
                b.setBarNombre(nombre);
                b.setCiuId(city);
                neighborhoodEJB.create(b);
                neighborhoods = null;
                nombre = "";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "Barrio registrado correctamente."));
                RequestContext requestContext = RequestContext.getCurrentInstance();
                requestContext.update("addSportCenterForm:sportCenterPanel");
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "El nombre del barrio ya se encuentra registrado en la ciudad de "+city.getCiuNombre()+"."));
                RequestContext requestContext = RequestContext.getCurrentInstance();
                requestContext.update("addSportCenterForm:sportCenterPanel");
            }
        }
    }
    
    public void openEditarNeighborhoodDialog(Barrio neighborhood)
    {
       RequestContext requestContext = RequestContext.getCurrentInstance();          
       this.nombre = neighborhood.getBarNombre();
       this.neighborhoodSelected = neighborhood;
       requestContext.execute("PF('editNeighborhoodDialog').show()");
    }
    
    public void editNeighborhood()
    {
        if(!nombre.equals(""))
        {
            nombre = Util.formatText(nombre);
            if(!nombre.equals(neighborhoodSelected.getBarNombre()))
            {
                if (!neighborhoodEJB.existNeighborhoodName(city.getCiuId(), nombre)) 
                {
                    neighborhoodSelected.setBarNombre(nombre);
                    neighborhoodEJB.edit(neighborhoodSelected);
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "Barrio actualizado correctamente."));
                    RequestContext requestContext = RequestContext.getCurrentInstance();
                    requestContext.update("editNeighborhood:editNeighborhoodPanel");
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "El nombre del barrio ya se encuentra registrado en la ciudad de " + city.getCiuNombre()+ "."));
                    RequestContext requestContext = RequestContext.getCurrentInstance();
                    requestContext.update("editNeighborhood:editNeighborhoodPanel");
                }
            }
        }
    }
    
    public void openDeleteNeighborhoodDialog(Barrio neighborhood)
    {
       RequestContext requestContext = RequestContext.getCurrentInstance();  
       this.neighborhoodSelected = neighborhood;
       FacesContext.getCurrentInstance().addMessage("deleteMessage", new FacesMessage(FacesMessage.SEVERITY_WARN, "", "Esta seguro que desea eliminar el barrio "+neighborhoodSelected.getBarNombre()+"?"));
       requestContext.execute("PF('deteletNeighborhoodDialog').show()");
    }
    
    public void deleteNeighborhood()
    {
        RequestContext requestContext = RequestContext.getCurrentInstance();  
        if(!neighborhoodEJB.isNeighborhoodUsed(neighborhoodSelected.getBarId()))
        {
            neighborhoods=null;
            neighborhoodEJB.remove(neighborhoodSelected);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Barrio eliminado correctamente."));
        }
        else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No se puede eliminar el Barrio."));
        }
        requestContext.execute("PF('deteletNeighborhoodDialog').hide()");
        requestContext.execute("PF('deteletNeighborhoodResultDialog').show()");
    }
}
