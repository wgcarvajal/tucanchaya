/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tucanchaya.controller;

import com.tucanchaya.entities.Color;
import com.tucanchaya.facade.ColorFacade;
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
@ManagedBean(name="colorController")
@ViewScoped
public class ColorController implements Serializable
{
    
    @EJB
    private ColorFacade colorEJB;
    private List<Color> colors;
    private String color;
    private String nombre;
    private String hexadecimal;
    private Color colorSelected;
    
    public List<Color> getColors()
    {
        if(colors== null)
        {
            colors = colorEJB.findAllOderByName("colorNombre");
        }
        return colors;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getHexadecimal() {
        return hexadecimal;
    }

    public void setHexadecimal(String hexadecimal) {
        this.hexadecimal = hexadecimal;
    }
    
    
    
    public void searchColor()
    {
       colors = colorEJB.findByColorName(color);
    }
    
    public void openRegisterColorDialog()
    {
       RequestContext requestContext = RequestContext.getCurrentInstance();
       this.nombre = "";
       this.hexadecimal="";
       requestContext.execute("PF('addColorDialog').show()");
    }
    
    public void saveColor()
    {
        if(!nombre.equals("") && !hexadecimal.equals(""))
        {
            if (evaluteCodigoColor()) {
                nombre = nombre.substring(0, 1).toUpperCase() + nombre.substring(1).toLowerCase();
                hexadecimal = hexadecimal.substring(0, 1) + hexadecimal.substring(1).toUpperCase();
                if (!colorEJB.existColorName(nombre)) {
                    if (!colorEJB.existHexCode(hexadecimal)) {
                        Color c = new Color();
                        c.setColorNombre(nombre);
                        c.setColorHexadecimal(hexadecimal);
                        colorEJB.create(c);
                        colors = null;
                        nombre = "";
                        hexadecimal = "";
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "Color registrada correctamente."));
                        RequestContext requestContext = RequestContext.getCurrentInstance();
                        requestContext.update("colorForm:colorPanel");
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "El codigo del color ya se encuentra registrado."));
                        RequestContext requestContext = RequestContext.getCurrentInstance();
                        requestContext.update("colorForm:colorPanel");
                    }

                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "El nombre del color ya se encuentra registrado."));
                    RequestContext requestContext = RequestContext.getCurrentInstance();
                    requestContext.update("colorForm:colorPanel");
                }
            } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Formato del codigo de color invalido."));
                    RequestContext requestContext = RequestContext.getCurrentInstance();
                    requestContext.update("colorForm:colorPanel");
            }
        }
    }
    
    private boolean evaluteCodigoColor()
    {
        if(hexadecimal.length()==7)
        {
            String hex = hexadecimal.substring(0, 1);
            if(hex.equals("#"))
            {
                hex = hexadecimal.substring(1,2);
                if(evaluateChar(hex))
                {
                    hex = hexadecimal.substring(2, 3);
                    if (evaluateChar(hex)) {
                        hex = hexadecimal.substring(3, 4);
                        if (evaluateChar(hex)) {
                            hex = hexadecimal.substring(4, 5);
                            if (evaluateChar(hex)) {
                                hex = hexadecimal.substring(5, 6);
                                if (evaluateChar(hex)) {
                                    hex = hexadecimal.substring(6);
                                    if (evaluateChar(hex)) {
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                    
                }
            }
        }
        
        return false;
    }
    
    private boolean evaluateChar(String hex)
    {
        return  hex.equals("0") 
                || hex.equals("1") 
                || hex.equals("2") 
                || hex.equals("3") 
                || hex.equals("4")
                || hex.equals("5")
                || hex.equals("6")
                || hex.equals("7")
                || hex.equals("8")
                || hex.toUpperCase().equals("A")
                || hex.toUpperCase().equals("B") 
                || hex.toUpperCase().equals("C")
                || hex.toUpperCase().equals("D") 
                || hex.toUpperCase().equals("E")
                || hex.toUpperCase().equals("F") ;
    }
    
    public void openEditarColorDialog(Color color)
    {
       RequestContext requestContext = RequestContext.getCurrentInstance();          
       this.nombre = color.getColorNombre();
       this.hexadecimal = color.getColorHexadecimal();
       this.colorSelected = color;
       requestContext.execute("PF('editColorDialog').show()");
    }
    
    public void editColor()
    {
        if(!nombre.equals("") && !hexadecimal.equals(""))
        {
            if (evaluteCodigoColor()) {
                nombre = nombre.substring(0, 1).toUpperCase() + nombre.substring(1).toLowerCase();
                hexadecimal = hexadecimal.substring(0, 1) + hexadecimal.substring(1).toUpperCase();
                if (!nombre.equals(colorSelected.getColorNombre()) && !hexadecimal.equals(colorSelected.getColorHexadecimal())) {
                    if (!colorEJB.existColorName(nombre)) {
                        if (!colorEJB.existHexCode(hexadecimal)) {
                            colorSelected.setColorNombre(nombre);
                            colorSelected.setColorHexadecimal(hexadecimal);
                            colorEJB.edit(colorSelected);
                            colors = null;
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "Color Actualizado correctamente."));
                            RequestContext requestContext = RequestContext.getCurrentInstance();
                            requestContext.update("editCityForm:editCityPanel");
                        } else {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "El codigo del color ya se encuentra registrado."));
                            RequestContext requestContext = RequestContext.getCurrentInstance();
                            requestContext.update("editCityForm:editCityPanel");
                        }

                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "El nombre del color ya se encuentra registrado."));
                        RequestContext requestContext = RequestContext.getCurrentInstance();
                        requestContext.update("editCityForm:editCityPanel");
                    }
                }
                else if(!nombre.equals(colorSelected.getColorNombre()))
                {
                    if (!colorEJB.existColorName(nombre)) {
                        colorSelected.setColorNombre(nombre);
                        colorEJB.edit(colorSelected);
                        colors = null;
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "Color Actualizado correctamente."));
                        RequestContext requestContext = RequestContext.getCurrentInstance();
                        requestContext.update("editCityForm:editCityPanel");
                    }
                    else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "El nombre del color ya se encuentra registrado."));
                        RequestContext requestContext = RequestContext.getCurrentInstance();
                        requestContext.update("editCityForm:editCityPanel");
                    }
                }
                else if (!hexadecimal.equals(colorSelected.getColorHexadecimal())) {

                    if (!colorEJB.existHexCode(hexadecimal)) {
                        colorSelected.setColorHexadecimal(hexadecimal);
                        colorEJB.edit(colorSelected);
                        colors = null;
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "Color Actualizado correctamente."));
                        RequestContext requestContext = RequestContext.getCurrentInstance();
                        requestContext.update("editCityForm:editCityPanel");
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "El codigo del color ya se encuentra registrado."));
                        RequestContext requestContext = RequestContext.getCurrentInstance();
                        requestContext.update("editCityForm:editCityPanel");
                    }
                }
                    
                
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Formato del codigo de color invalido."));
                RequestContext requestContext = RequestContext.getCurrentInstance();
                requestContext.update("editCityForm:editCityPanel");
            }
        }
    }
    
    public void openDeleteColorDialog(Color color)
    {
       RequestContext requestContext = RequestContext.getCurrentInstance();  
       this.colorSelected = color;
       FacesContext.getCurrentInstance().addMessage("deleteMessage", new FacesMessage(FacesMessage.SEVERITY_WARN, "", "Esta seguro que desea eliminar el color "+color.getColorNombre()+"?"));
       requestContext.execute("PF('deteletColorDialog').show()");
    }
    
    public void deleteColor()
    {
        RequestContext requestContext = RequestContext.getCurrentInstance();  
        colors=null;
        if(!colorEJB.isColorUsed(colorSelected.getColorId()))
        {
            colorEJB.remove(colorSelected);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Color eliminado correctamente."));
        }
        else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No se puede eliminar el color."));
        }
        requestContext.execute("PF('deteletColorDialog').hide()");
        requestContext.execute("PF('deteletColorResultDialog').show()");
    }
}

