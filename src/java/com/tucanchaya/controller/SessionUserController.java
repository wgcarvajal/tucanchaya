/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tucanchaya.controller;

import com.tucanchaya.facade.UsuariogrupoFacade;
import java.io.IOException;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.context.RequestContext;

/**
 *
 * @author aranda
 */
/**
 *
 * @author wilson carvajal
 */
@Named("sessionUserController")
@SessionScoped
public class SessionUserController implements Serializable{
    
    @EJB
    private UsuariogrupoFacade usuarioGrupoEJB;
    String nombreDeUsuario;    
    String contrasena;
    
    private Boolean haySesion;
    
    public SessionUserController()
    {
        haySesion=false;
    }

    public Boolean getHaySesion() {
        return haySesion;
    }

    public void setHaySesion(Boolean haySesion) {
        this.haySesion = haySesion;
    }
    
    
    
    public String getNombreDeUsuario()
    {
        return nombreDeUsuario;
    }

    public void setNombreDeUsuario(String nombreDeUsuario) 
    {
        this.nombreDeUsuario = nombreDeUsuario;
    }

    public String getContrasena() 
    {
        return contrasena;
    }

    public void setContrasena(String contrasena)
    {
        this.contrasena = contrasena;
    }
       
    public void login()throws IOException, ServletException 
    {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest req = (HttpServletRequest) fc.getExternalContext().getRequest();        
        if (req.getUserPrincipal() == null) {
            try 
            {
                req.login(this.nombreDeUsuario, this.contrasena);
                req.getServletContext().log("Autenticacion exitosa");
                haySesion = true;
                if(true)//this.usuarioGrupoEJB.buscarPorNombreUsuario(req.getUserPrincipal().getName()).get(0).getUsuariogrupoPK().getGruid().equals("user"))
                {
                    FacesContext.getCurrentInstance().getExternalContext().redirect("/Hora_Saludable");
                }
                else
                {
                    FacesContext.getCurrentInstance().getExternalContext().redirect("/Hora_Saludable/faces/administrador/contenidos/principal.xhtml");
                }
            } 
            catch (ServletException e) 
            {
                fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nombre de usuario o contraseña incorrectos", "Nombre de usuario o contraseña incorrectos"));
                requestContext.update("formularioInicioSession");                
            }
        } 
        else 
        {
            req.getServletContext().log("El usuario ya estaba logueado:  ");
            requestContext.update("formularioInicioSession");
        }
    }
    
    public void logout() throws IOException, ServletException 
    {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest req = (HttpServletRequest) fc.getExternalContext().getRequest();
        try {
            req.logout();            
            req.getSession().invalidate();
            fc.getExternalContext().invalidateSession();
            FacesContext.getCurrentInstance().getExternalContext().redirect("/Hora_Saludable/");

        } catch (ServletException e) {            
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "FAILED", "Logout failed on backend"));            
        }
        
    }
    
    public void ventanaInicioSession()
    {
       RequestContext requestContext = RequestContext.getCurrentInstance();          
       FacesContext context = FacesContext.getCurrentInstance();
       Application application = context.getApplication();
       ViewHandler viewHandler = application.getViewHandler();
       UIViewRoot viewRoot = viewHandler.createView(context, context.getViewRoot().getViewId());
       context.setViewRoot(viewRoot);       
       context.renderResponse();
       this.contrasena=null;
       this.nombreDeUsuario=null;
       requestContext.update("formularioInicioSession");       
       requestContext.execute("PF('IniciarSesion').show()");
    }
    public boolean esusuarioSinSession()
    {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest req = (HttpServletRequest) fc.getExternalContext().getRequest();       
        if (req.getUserPrincipal() == null) 
        {
            return true;
        }
        return false;
    }
    
    public boolean esusuarioConSession()
    {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest req = (HttpServletRequest) fc.getExternalContext().getRequest();       
        if (req.getUserPrincipal() == null) 
        {
            return false;
            
        }
        else
        {
            if(true)//this.usuarioGrupoEJB.buscarPorNombreUsuario(req.getUserPrincipal().getName()).get(0).getUsuariogrupoPK().getGruid().equals("user"))
            {
                return true;
            }
            return false;
        }
        
    }
    public boolean esAdministrador()
    {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest req = (HttpServletRequest) fc.getExternalContext().getRequest();       
        if (req.getUserPrincipal() == null) 
        {
            return false;
            
        }
        else
        {
            if(true)//this.usuarioGrupoEJB.buscarPorNombreUsuario(req.getUserPrincipal().getName()).get(0).getUsuariogrupoPK().getGruid().equals("admin"))
            {
                return true;
            }
            return false;
        }
        
    }
    public String nombreUsuario()
    {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest req = (HttpServletRequest) fc.getExternalContext().getRequest();       
        if (req.getUserPrincipal() == null) 
        {
            return "";
        }
        else
        {
            return req.getUserPrincipal().getName();
        }
    }
}
