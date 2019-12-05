/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tucanchaya.controller;

import com.tucanchaya.entities.Deporte;
import com.tucanchaya.entities.Escenario;
import com.tucanchaya.facade.DeporteFacade;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

/**
 *
 * @author wilson carvajal
 */
@ManagedBean(name="sportController")
@ViewScoped
public class SportController implements Serializable{
    
    @EJB
    private DeporteFacade sportEJB;
    private List<Deporte> sports;
    private String sport;
    private String nombre;
    private Deporte sportSelected;
    
    public List<Deporte> getSports()
    {
        if(sports== null)
        {
            sports = sportEJB.findAllOderByName("depNombre");
        }
        return sports;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }
    
    public void searchSport()
    {
       sports = sportEJB.findByColumn(sport);
    }
    
    public void openRegisterSportDialog()
    {
       RequestContext requestContext = RequestContext.getCurrentInstance(); 
       this.nombre = "";
       requestContext.execute("PF('addSportDialog').show()");
    }
    
    public void saveSport()
    {
        if(!nombre.equals(""))
        {
           nombre = nombre.substring(0,1).toUpperCase() + nombre.substring(1).toLowerCase();
           if(!sportEJB.existSport(nombre))
           {
               Deporte deporte = new Deporte();
               deporte.setDepNombre(nombre);
               sportEJB.create(deporte);
               sports = null;
               nombre = "";
               FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "Deporte registrado correctamente."));
               RequestContext requestContext = RequestContext.getCurrentInstance();
               requestContext.update("sportForm:sportPanel");
           }
           else
           {
              FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "El deporte ya se encuentra registrada."));
              RequestContext requestContext = RequestContext.getCurrentInstance();
              requestContext.update("sportForm:sportPanel"); 
           }
        }
    }
   
    
    public void openEditarSportDialog(Deporte sport)
    {
       RequestContext requestContext = RequestContext.getCurrentInstance();          
       this.nombre = sport.getDepNombre();
       this.sportSelected = sport;
       requestContext.execute("PF('editSportDialog').show()");
    }
    
    public void editSport()
    {
        if(!nombre.equals(""))
        {
           nombre = nombre.substring(0,1).toUpperCase() + nombre.substring(1).toLowerCase();
           if(!nombre.equals(sportSelected.getDepNombre()))
           {
               if(!sportEJB.existSport(nombre))
                {
                    sportSelected.setDepNombre(nombre);
                    sportEJB.edit(sportSelected);
                    sports = null;
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "Deporte actualizado correctamente."));
                    RequestContext requestContext = RequestContext.getCurrentInstance();
                    requestContext.update("editSportForm:editSportPanel");
                }
                else
                {
                   FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "El deporte ya se encuentra registrada."));
                   RequestContext requestContext = RequestContext.getCurrentInstance();
                   requestContext.update("editSportForm:editSportPanel"); 
                }
           }
        }
    }
    
    public void openDeleteSportDialog(Deporte deporte)
    {
       RequestContext requestContext = RequestContext.getCurrentInstance();  
       this.sportSelected = deporte;
       FacesContext.getCurrentInstance().addMessage("deleteMessage", new FacesMessage(FacesMessage.SEVERITY_WARN, "", "Esta seguro que desea eliminar el deporte "+deporte.getDepNombre()+"?"));
       requestContext.execute("PF('deteletSportDialog').show()");
    }
    
    public void deleteSport()
    {
        RequestContext requestContext = RequestContext.getCurrentInstance();  
        sports=null;
        sportEJB.remove(sportSelected);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Deporte eliminado correctamente."));
        requestContext.execute("PF('deteletSportDialog').hide()");
        requestContext.execute("PF('deteletSportResultDialog').show()");
    }
    
}
