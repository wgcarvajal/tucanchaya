/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tucanchaya.controller;

import com.tucanchaya.entities.Ciudad;
import com.tucanchaya.facade.CiudadFacade;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Wilson Carvajal
 */
@ManagedBean(name="cityController")
@ViewScoped
public class CityController implements Serializable
{
    
    @EJB
    private CiudadFacade cityEJB;
    private List<Ciudad> cities;
    private String city;
    private String nombre;
    private Ciudad citySelected;
    
    public List<Ciudad> getCities()
    {
        if(cities== null)
        {
            cities = cityEJB.findAllOderByName("ciunombre");
        }
        return cities;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
    
    public void openRegisterCityDialog()
    {
       RequestContext requestContext = RequestContext.getCurrentInstance();          
       FacesContext context = FacesContext.getCurrentInstance();
       Application application = context.getApplication();
       ViewHandler viewHandler = application.getViewHandler();
       UIViewRoot viewRoot = viewHandler.createView(context, context.getViewRoot().getViewId());
       context.setViewRoot(viewRoot);       
       context.renderResponse(); 
       this.nombre = "";
       requestContext.execute("PF('addCityDialog').show()");
    }
    
    public void saveCity()
    {
        if(!nombre.equals(""))
        {
           nombre = nombre.substring(0,1).toUpperCase() + nombre.substring(1).toLowerCase();
           if(!cityEJB.existCity(nombre))
           {
               Ciudad ciudad = new Ciudad();
               ciudad.setCiuPorDefecto(false);
               ciudad.setCiunombre(nombre);
               cityEJB.create(ciudad);
               cities = null;
               nombre = "";
               FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "Ciudad registrada correctamente."));
               RequestContext requestContext = RequestContext.getCurrentInstance();
               requestContext.update("cityForm:cityPanel");
           }
           else
           {
              FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "La Ciudad ya se encuentra registrada."));
              RequestContext requestContext = RequestContext.getCurrentInstance();
              requestContext.update("cityForm:cityPanel"); 
           }
        }
    }
   
    
    public void openEditarCityDialog(Ciudad ciudad)
    {
       RequestContext requestContext = RequestContext.getCurrentInstance();          
       this.nombre = ciudad.getCiunombre();
       this.citySelected = ciudad;
       requestContext.execute("PF('editCityDialog').show()");
    }
    
    public void editCity()
    {
        if(!nombre.equals(""))
        {
           nombre = nombre.substring(0,1).toUpperCase() + nombre.substring(1).toLowerCase();
           if(!nombre.equals(citySelected.getCiunombre()))
           {
               if(!cityEJB.existCity(nombre))
                {
                    citySelected.setCiunombre(nombre);
                    cityEJB.edit(citySelected);
                    cities = null;
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "Ciudad actualizada correctamente."));
                    RequestContext requestContext = RequestContext.getCurrentInstance();
                    requestContext.update("editCityForm:editCityPanel");
                }
                else
                {
                   FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "La Ciudad ya se encuentra registrada."));
                   RequestContext requestContext = RequestContext.getCurrentInstance();
                   requestContext.update("editCityForm:editCityPanel"); 
                }
           }
        }
    }
    
    public void openDeleteCityDialog(Ciudad ciudad)
    {
       RequestContext requestContext = RequestContext.getCurrentInstance();  
       this.citySelected = ciudad;
       FacesContext.getCurrentInstance().addMessage("deleteMessage", new FacesMessage(FacesMessage.SEVERITY_WARN, "", "Esta seguro que desea eliminar la ciudad de "+ciudad.getCiunombre()+"?"));
       requestContext.execute("PF('deteletCityDialog').show()");
    }
    
    public void deleteCity()
    {
        RequestContext requestContext = RequestContext.getCurrentInstance();  
        cities=null;
        if(!cityEJB.isCityUsed(citySelected.getCiuId()))
        {
            cityEJB.remove(citySelected);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Ciudad eliminada correctamente."));
        }
        else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No se puede eliminar la ciudad."));
        }
        requestContext.execute("PF('deteletCityDialog').hide()");
        requestContext.execute("PF('deteletCityResultDialog').show()");
    }
    
    
}
