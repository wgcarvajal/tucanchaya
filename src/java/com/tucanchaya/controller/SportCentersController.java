/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tucanchaya.controller;

import com.tucanchaya.controller.util.Util;
import com.tucanchaya.entities.Barrio;
import com.tucanchaya.entities.Centrodeportivo;
import com.tucanchaya.entities.Centrodeportivofotos;
import com.tucanchaya.entities.Ciudad;
import com.tucanchaya.facade.BarrioFacade;
import com.tucanchaya.facade.CentrodeportivoFacade;
import com.tucanchaya.facade.CentrodeportivofotosFacade;
import com.tucanchaya.facade.CiudadFacade;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Wilson Carvajal
 */
@ManagedBean(name = "sportCentersController")
@ViewScoped
public class SportCentersController implements Serializable {

    @EJB
    private CentrodeportivoFacade sportCenterEJB;
    @EJB
    private CiudadFacade cityEJB;
    @EJB
    private CentrodeportivofotosFacade sportCenterPhotosEJB;
    @EJB
    private BarrioFacade neighborhoodEJB;

    private List<Ciudad> cities;
    private List<Barrio> neighborhoods;
    private Ciudad city;
    private String neighborhoodId;
    private String sportCenterName;

    private String nombre;
    private String direccion;

    private Centrodeportivo sportCenterSelected;

    private List<Centrodeportivo> sportCenters;

    @PostConstruct
    public void init() {
        if (cities == null) {
            cities = cityEJB.findAllOderByName("ciuNombre");
        }
        for (Ciudad c : cities) {
            if (c.getCiuPorDefecto()) {
                city = c;
                break;
            }
        }
    }

    public void searchSportCenter() {
        sportCenters = sportCenterEJB.findByCityAndNameOrderByName(city.getCiuId(), sportCenterName);
    }

    public List<Centrodeportivo> getSportCenters() {
        if (sportCenters == null) {
            sportCenters = sportCenterEJB.findByCityOrderByName(city.getCiuId());
        }
        return sportCenters;
    }

    public List<Ciudad> getCities() {
        return cities;
    }

    public List<Barrio> getNeighborhoods() {

        neighborhoods = neighborhoodEJB.findByCityOrderByName(city.getCiuId());
        return neighborhoods;
    }

    public Ciudad getCity() {
        return city;
    }

    public void setCity(Ciudad city) {
        this.city = city;
    }

    public String getNeighborhoodId() {
        return neighborhoodId;
    }

    public void setNeighborhoodId(String neighborhoodId) {
        this.neighborhoodId = neighborhoodId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getSportCenterName() {
        return sportCenterName;
    }

    public void setSportCenterName(String sportCenterName) {
        this.sportCenterName = sportCenterName;
    }

    public void changeCity(ValueChangeEvent event) {
        city = (Ciudad) event.getNewValue();
        neighborhoods = null;
        neighborhoodId = null;
        sportCenters = null;
    }

    public String getDefaultPhoto(Long cenId) {
        Centrodeportivofotos sportCenterPhoto = sportCenterPhotosEJB.findByCenIdDefaultPhoto(cenId);
        if (sportCenterPhoto != null) {
            return sportCenterPhoto.getCenFoNombre();
        }
        return "";
    }

    public void openRegisterSportCenterDialog() {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        this.nombre = "";
        this.direccion = "";
        this.neighborhoodId = null;
        this.neighborhoods = null;
        requestContext.execute("PF('addSportCenterDialog').show()");
    }

    public void saveSportCenter() {
        if (!nombre.equals("") && !direccion.equals("")) {
            nombre = Util.formatText(nombre);
            direccion = Util.formatText(direccion);
            if (!sportCenterEJB.existSportCenterName(city.getCiuId(), nombre)) {
                if (!sportCenterEJB.existAddress(city.getCiuId(), direccion)) {

                    if (!neighborhoodId.equals("0")) {
                        Barrio b = neighborhoodEJB.find(Long.parseLong(neighborhoodId));
                        Centrodeportivo c = new Centrodeportivo();
                        c.setCenNombre(nombre);
                        c.setCenDireccion(direccion);
                        c.setBarId(b);
                        sportCenterEJB.create(c);
                        sportCenters = null;
                        nombre = "";
                        direccion = "";
                        neighborhoodId = null;
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "Centro deportivo registrado correctamente."));
                        RequestContext requestContext = RequestContext.getCurrentInstance();
                        requestContext.update("addSportCenterForm:sportCenterPanel");
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Seleccione un barrio."));
                        RequestContext requestContext = RequestContext.getCurrentInstance();
                        requestContext.update("addSportCenterForm:sportCenterPanel");
                    }

                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "La direccion ya se encuentra registrada en la ciudad de" + city.getCiuNombre() + "."));
                    RequestContext requestContext = RequestContext.getCurrentInstance();
                    requestContext.update("addSportCenterForm:sportCenterPanel");
                }

            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "El nombre del centro deportivo ya se encuentra registrado en la ciudad de " + city.getCiuNombre() + "."));
                RequestContext requestContext = RequestContext.getCurrentInstance();
                requestContext.update("addSportCenterForm:sportCenterPanel");
            }
        }
    }

    public void openEditarSportCenterDialog(Centrodeportivo sportCenter) {
        this.sportCenterSelected = sportCenter;
        RequestContext requestContext = RequestContext.getCurrentInstance();
        this.nombre = sportCenter.getCenNombre();
        this.direccion = sportCenter.getCenDireccion();
        this.neighborhoodId = sportCenterSelected.getBarId().getBarId() + "";
        this.neighborhoods = null;
        requestContext.execute("PF('editSportCenterDialog').show()");
    }

    public void editSportCenter() {
        if (!nombre.equals("") && !direccion.equals("")) {
            nombre = Util.formatText(nombre);
            direccion = Util.formatText(direccion);
            if (!nombre.equals(sportCenterSelected.getCenNombre())) {
                if (!sportCenterEJB.existSportCenterName(city.getCiuId(), nombre)) {
                    if (!direccion.equals(sportCenterSelected.getCenDireccion())) {

                        if (!sportCenterEJB.existAddress(city.getCiuId(), direccion)) {
                            if (!neighborhoodId.equals("0")) {

                                if (!sportCenterSelected.getBarId().getBarId().equals(Long.parseLong(neighborhoodId))) {
                                    Barrio b = neighborhoodEJB.find(Long.parseLong(neighborhoodId));
                                    sportCenterSelected.setCenNombre(nombre);
                                    sportCenterSelected.setCenDireccion(direccion);
                                    sportCenterSelected.setBarId(b);
                                    sportCenterEJB.edit(sportCenterSelected);
                                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "Centro deportivo actualizado correctamente."));
                                    RequestContext requestContext = RequestContext.getCurrentInstance();
                                    requestContext.update("editSportCenter:editSportCenterPanel");
                                } else {
                                    sportCenterSelected.setCenNombre(nombre);
                                    sportCenterSelected.setCenDireccion(direccion);
                                    sportCenterEJB.edit(sportCenterSelected);
                                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "Centro deportivo actualizado correctamente."));
                                    RequestContext requestContext = RequestContext.getCurrentInstance();
                                    requestContext.update("editSportCenter:editSportCenterPanel");
                                }

                            } else {
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Seleccione un barrio."));
                                RequestContext requestContext = RequestContext.getCurrentInstance();
                                requestContext.update("editSportCenter:editSportCenterPanel");
                            }

                        } else {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "La direccion ya se encuentra registrada en la ciudad de" + city.getCiuNombre() + "."));
                            RequestContext requestContext = RequestContext.getCurrentInstance();
                            requestContext.update("editSportCenter:editSportCenterPanel");
                        }
                    } else if (!neighborhoodId.equals("0")) {
                        if (!sportCenterSelected.getBarId().getBarId().equals(Long.parseLong(neighborhoodId))) {
                            Barrio b = neighborhoodEJB.find(Long.parseLong(neighborhoodId));
                            sportCenterSelected.setCenNombre(nombre);
                            sportCenterSelected.setBarId(b);
                            sportCenterEJB.edit(sportCenterSelected);
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "Centro deportivo actualizado correctamente."));
                            RequestContext requestContext = RequestContext.getCurrentInstance();
                            requestContext.update("editSportCenter:editSportCenterPanel");
                        } else {
                            sportCenterSelected.setCenNombre(nombre);
                            sportCenterEJB.edit(sportCenterSelected);
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "Centro deportivo actualizado correctamente."));
                            RequestContext requestContext = RequestContext.getCurrentInstance();
                            requestContext.update("editSportCenter:editSportCenterPanel");
                        }

                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Seleccione un barrio."));
                        RequestContext requestContext = RequestContext.getCurrentInstance();
                        requestContext.update("editSportCenter:editSportCenterPanel");
                    }

                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "El nombre del centro deportivo ya se encuentra registrado en la ciudad de " + city.getCiuNombre() + "."));
                    RequestContext requestContext = RequestContext.getCurrentInstance();
                    requestContext.update("editSportCenter:editSportCenterPanel");
                }
            } else if (!direccion.equals(sportCenterSelected.getCenDireccion())) {
                if (!sportCenterEJB.existAddress(city.getCiuId(), direccion)) {
                    if (!neighborhoodId.equals("0")) {
                        if (!sportCenterSelected.getBarId().getBarId().equals(Long.parseLong(neighborhoodId))) {
                            Barrio b = neighborhoodEJB.find(Long.parseLong(neighborhoodId));
                            sportCenterSelected.setCenDireccion(direccion);
                            sportCenterSelected.setBarId(b);
                            sportCenterEJB.edit(sportCenterSelected);
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "Centro deportivo actualizado correctamente."));
                            RequestContext requestContext = RequestContext.getCurrentInstance();
                            requestContext.update("form:editSportCenterPanel");
                        } else {
                            sportCenterSelected.setCenDireccion(direccion);
                            sportCenterEJB.edit(sportCenterSelected);
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "Centro deportivo actualizado correctamente."));
                            RequestContext requestContext = RequestContext.getCurrentInstance();
                            requestContext.update("form:editSportCenterPanel");
                        }
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Seleccione un barrio."));
                        RequestContext requestContext = RequestContext.getCurrentInstance();
                        requestContext.update("editSportCenter:editSportCenterPanel");
                    }

                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "La direccion ya se encuentra registrada en la ciudad de" + city.getCiuNombre() + "."));
                    RequestContext requestContext = RequestContext.getCurrentInstance();
                    requestContext.update("form:editSportCenterPanel");
                }
            } else if (!neighborhoodId.equals("0")) {
                if (!sportCenterSelected.getBarId().getBarId().equals(Long.parseLong(neighborhoodId))) {
                    Barrio b = neighborhoodEJB.find(Long.parseLong(neighborhoodId));
                    sportCenterSelected.setBarId(b);
                    sportCenterEJB.edit(sportCenterSelected);
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "Centro deportivo actualizado correctamente."));
                    RequestContext requestContext = RequestContext.getCurrentInstance();
                    requestContext.update("form:editSportCenterPanel");
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Seleccione un barrio."));
                RequestContext requestContext = RequestContext.getCurrentInstance();
                requestContext.update("editSportCenter:editSportCenterPanel");
            }
        }
    }

    public void openDeleteSportCenterDialog(Centrodeportivo sportCenter) {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        this.sportCenterSelected = sportCenter;
        FacesContext.getCurrentInstance().addMessage("deleteMessage", new FacesMessage(FacesMessage.SEVERITY_WARN, "", "Esta seguro que desea eliminar el centro deportivo " + sportCenterSelected.getCenNombre() + "?"));
        requestContext.execute("PF('deteletSportCenterDialog').show()");
    }

    public void deleteSportCenter() {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        /*if(!sportCenterEJB.isColorUsed(colorSelected.getColorId()))
        {*/
        sportCenters = null;
        sportCenterEJB.remove(sportCenterSelected);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Centro deportivo eliminado correctamente."));
        /*}
        else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No se puede eliminar el color."));
        }*/
        requestContext.execute("PF('deteletSportCenterDialog').hide()");
        requestContext.execute("PF('deteletSportCenterResultDialog').show()");
    }

    public void getSportCenter(Centrodeportivo sportCenter) {
        try {
            //String uri = Util.projectPath+"/sa/centro?i=1&s="+sportCenter.getCenId();
            String uri = Util.projectPath+"/superadmin/sportCenter/sportCenter.xhtml?i=1&s="+sportCenter.getCenId();
            FacesContext.getCurrentInstance().getExternalContext().redirect(uri);
        } catch (IOException ex) {
            Logger.getLogger(SportCentersController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
