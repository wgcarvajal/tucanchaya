/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tucanchaya.controller;

import com.tucanchaya.controller.util.Util;
import com.tucanchaya.entities.Centrodeportivo;
import com.tucanchaya.entities.Usuario;
import com.tucanchaya.facade.CentrodeportivoFacade;
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
@ManagedBean(name="employeesController")
@ViewScoped
public class EmployeesController implements Serializable{
    
    @EJB
    private CentrodeportivoFacade sportCenterEJB;
    @EJB
    private UsuarioFacade userEJB;
    private Centrodeportivo sportCenter;
    private String search;
    
    private List<Usuario> employees;
    
    @PostConstruct
    public void init()
    {
        
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> paramMap = context.getExternalContext().getRequestParameterMap();
        String sportCenterId = paramMap.get("s");
        
        if (sportCenterId != null && !sportCenterId.equals("")) {
            try {
                Long id = Long.parseLong(sportCenterId);
                sportCenter = sportCenterEJB.find(id);
                if (sportCenter == null) {
                    goCentros();
                }
            } catch (NumberFormatException e) {
                goCentros();
            }
        } else {
            goCentros();
        }
    }
    
    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
    
    public void searchEmployee()
    {
        employees = userEJB.findByUsuRolAndCenIdAndName("user", search,sportCenter.getCenId());
    }
    
    public List<Usuario> getEmployees() {
        if(employees == null)
        {
            employees = userEJB.findByUsuRolAndCenId("user",sportCenter.getCenId());
        }
        return employees;
    }
    
    private void goCentros() {
        try {
            String uri = Util.projectPath+"/superadmin/sportCenter/sportCenters.xhtml?i=1";
            FacesContext.getCurrentInstance().getExternalContext().redirect(uri);
        } catch (IOException ex) {
            Logger.getLogger(SportCentersController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
