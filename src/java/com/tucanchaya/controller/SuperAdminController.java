/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tucanchaya.controller;

import com.tucanchaya.controller.util.Util;
import com.tucanchaya.entities.Usuario;
import com.tucanchaya.facade.UsuarioFacade;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Wilson Carvajal
 */
@ManagedBean(name = "superAdminController")
@ViewScoped
public class SuperAdminController implements Serializable
{  
    @EJB
    private UsuarioFacade userEJB;
    private Usuario user;
    
    @PostConstruct
    public void init()
    {
        
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> paramMap = context.getExternalContext().getRequestParameterMap();
        String id = paramMap.get("u");
        
        if (id != null && !id.equals("")) {
            try {
                Long usuId = Long.parseLong(id);
                List<Usuario>users = userEJB.findByUsuRolAndUsuId("superAdmin",usuId);
                if (users == null || users.isEmpty()) {
                    goSuperAdmins();
                }
                else
                {
                    user = users.get(0);
                }
            } catch (NumberFormatException e) {
                goSuperAdmins();
            }
        } else {
            goSuperAdmins();
        }
    }
    
    public void test()
    {
        
    }
    
    private void goSuperAdmins() {
        try {
            String uri = Util.projectPath+"/superadmin/user/superadmin.xhtml?i=8";
            FacesContext.getCurrentInstance().getExternalContext().redirect(uri);
        } catch (IOException ex) {
            Logger.getLogger(SportCentersController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
