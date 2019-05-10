/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tucanchaya.controller;

import com.tucanchaya.controller.util.Cifrar;
import com.tucanchaya.controller.util.Util;
import com.tucanchaya.entities.Grupo;
import com.tucanchaya.entities.Usuario;
import com.tucanchaya.entities.Usuariogrupo;
import com.tucanchaya.facade.GrupoFacade;
import com.tucanchaya.facade.UsuarioFacade;
import com.tucanchaya.facade.UsuariogrupoFacade;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@ManagedBean(name="superAdminsController")
@ViewScoped
public class SuperAdminsController implements Serializable
{
    @EJB
    private UsuarioFacade userEJB;
    @EJB
    private UsuariogrupoFacade userGroupEJB;
    @EJB
    private GrupoFacade groupEJB;
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
    
    private List<Usuario> superAdminUsers;

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
    
    public void searchUser()
    {
        superAdminUsers = userEJB.findByUsuRolAndName("superAdmin", search);
    }

    public List<Usuario> getSuperAdminUsers() {
        if(superAdminUsers == null)
        {
            superAdminUsers = userEJB.findByUsuRol("superAdmin");
        }
        return superAdminUsers;
    }
    
    public void saveUser()
    {
        names = names.trim();
        lastNames = lastNames.trim();
        address = address.trim();
        Usuario user = new Usuario();
        Usuariogrupo userGroup = new Usuariogrupo();
        user.setUsuNombres(names);
        user.setUsuApellidos(lastNames);
        user.setUsuIdentificacion(identification);
        user.setUsuEmail(email);
        user.setUsuNombreUsuario(userName);
        user.setUsuTelefono(phone);
        user.setUsuDireccion(address);
        user.setUsuActivo(false);
        user.setUsuContrasena(Cifrar.sha256(this.password));
        Grupo grupo = groupEJB.findByGruId("superAdmin");
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
        superAdminUsers=null;
        RequestContext requestContext = RequestContext.getCurrentInstance();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Registro Exitoso."));
        requestContext.execute("PF('mensajeRegistroExitoso').show()");
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
    
    public void gotUserSA(Usuario user) {
        try {
            String uri = Util.projectPath+"/superadmin/user/superAdminDetail.xhtml?i=8&u="+user.getUsuId();
            FacesContext.getCurrentInstance().getExternalContext().redirect(uri);
        } catch (IOException ex) {
            Logger.getLogger(SportCentersController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
