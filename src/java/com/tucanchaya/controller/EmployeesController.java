/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tucanchaya.controller;

import com.tucanchaya.controller.util.Cifrar;
import com.tucanchaya.controller.util.Util;
import com.tucanchaya.entities.Centrodeportivo;
import com.tucanchaya.entities.Grupo;
import com.tucanchaya.entities.Usuario;
import com.tucanchaya.entities.Usuariogrupo;
import com.tucanchaya.facade.CentrodeportivoFacade;
import com.tucanchaya.facade.GrupoFacade;
import com.tucanchaya.facade.UsuarioFacade;
import com.tucanchaya.facade.UsuariogrupoFacade;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import org.primefaces.context.RequestContext;

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
    @EJB
    private UsuariogrupoFacade userGroupEJB;
    @EJB
    private GrupoFacade groupEJB;
    private Centrodeportivo sportCenter;
    private String identification;
    private String names;
    private String lastNames;
    private String userName;
    private String password;
    private String repeatPassword;
    private String email;
    private String address;
    private String phone;
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

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getLastNames() {
        return lastNames;
    }

    public void setLastNames(String lastNames) {
        this.lastNames = lastNames;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
    
    public void openAddUser()
    {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        identification = "";
        names = "";
        lastNames = "";
        userName = "";
        password = "";
        repeatPassword = "";
        email = "";
        address = "";
        phone = "";
        requestContext.execute("PF('addUserDialog').show()");
    }
    
    public void validatePassword(FacesContext arg0, UIComponent arg1, Object arg2)throws ValidatorException {
      
        this.password=String.valueOf(arg2);
    }
    
   public void validateRepeatPassword(FacesContext arg0, UIComponent arg1, Object arg2)throws ValidatorException 
   {
      String texto = String.valueOf(arg2);      
      if (!(texto.equals(this.password))) {
         throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,"","Las contraseñas no coinciden."));
      }
   } 
   
   public void saveUser()
    {
        names = names.trim();
        lastNames = lastNames.trim();
        address = address.trim();
        Usuario user = new Usuario();
        Usuariogrupo userGroup = new Usuariogrupo();
        user.setUsuNombres(names);
        user.setCenId(sportCenter);
        user.setUsuApellidos(lastNames);
        user.setUsuIdentificacion(identification);
        user.setUsuEmail(email);
        user.setUsuNombreUsuario(userName);
        user.setUsuTelefono(phone);
        user.setUsuDireccion(address);
        user.setUsuActivo(false);
        user.setUsuContrasena(Cifrar.sha256(this.password));
        Grupo grupo = groupEJB.findByGruId("user");
        userEJB.create(user);
        userGroup.setGruId(grupo);
        userGroup.setUsuId(user);
        userGroup.setUsuNombreUsuario(userName);
        userGroupEJB.create(userGroup);
        identification = "";
        names = "";
        lastNames = "";
        userName = "";
        password = "";
        repeatPassword = "";
        email = "";
        address = "";
        phone = "";
        employees=null;
        RequestContext requestContext = RequestContext.getCurrentInstance();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Registro Exitoso."));
        requestContext.execute("PF('mensajeRegistroExitoso').show()");
    }
    
}
