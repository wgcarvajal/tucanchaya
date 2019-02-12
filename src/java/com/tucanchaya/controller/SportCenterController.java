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
import com.tucanchaya.entities.Centrodeportivotelefono;
import com.tucanchaya.facade.BarrioFacade;
import com.tucanchaya.facade.CentrodeportivoFacade;
import com.tucanchaya.facade.CentrodeportivofotosFacade;
import com.tucanchaya.facade.CentrodeportivotelefonoFacade;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.json.JSONObject;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

/**
 *
 * @author Wilson Carvajal
 */
@ManagedBean
@ViewScoped
public class SportCenterController implements Serializable
{
    @EJB
    private CentrodeportivoFacade sportCenterEJB;
    @EJB
    private CentrodeportivofotosFacade sportCenterPhotosEJB;
    @EJB
    private CentrodeportivotelefonoFacade sportCenterPhonesEJB;
    @EJB
    private BarrioFacade neighborhoodEJB;
    
    private Centrodeportivo sportCenter;
    private Centrodeportivotelefono sportCenterPhoneSelected;
    private String sportCenterTitle;
    private String sportCenterNit;
    private String sportCenterAddress;
    private String sportCenterDescription;
    private String sportCenterPhoneNumber;
    private String sportCenterPhoneType;
    private String   idNeighborhoodSelected;
    private List<Centrodeportivofotos> sportCenterPhotos;
    private Centrodeportivofotos sportCenterPhoto;
    private List<Centrodeportivotelefono> sportCenterPhonesF;
    private List<Centrodeportivotelefono> sportCenterPhonesW;
    private List<Centrodeportivotelefono> sportCenterPhonesC;
    private List<Barrio> sportCenterNeighborhoods;
    
    private double lat;
    private double lng;
    
    
    private boolean editTitle = false;
    private boolean editPhotos = false;
    private boolean editNit = false;
    private boolean editAddress = false;
    private boolean editDescription = false;
    private boolean editNeighborhood = false;
    private boolean editMap = false;
    
    
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
                else
                {
                    initPhones();
                }
            } catch (NumberFormatException e) {
                goCentros();
            }
        } else {
            goCentros();
        }
    }
    
    private void initPhones() {
        sportCenterPhonesC = sportCenterPhonesEJB.findByCenIdAndTipo(sportCenter.getCenId(), "c");
        sportCenterPhonesF = sportCenterPhonesEJB.findByCenIdAndTipo(sportCenter.getCenId(), "f");
        sportCenterPhonesW = sportCenterPhonesEJB.findByCenIdAndTipo(sportCenter.getCenId(), "w");
    }
    
    private void goCentros() {
        try {
            String uri = Util.projectPath+"/superadmin/sportCenter/sportCenters.xhtml?i=1";
            FacesContext.getCurrentInstance().getExternalContext().redirect(uri);
        } catch (IOException ex) {
            Logger.getLogger(SportCentersController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Centrodeportivo getSportCenter() {
        return sportCenter;
    }

    public boolean isEditTitle() {
        return editTitle;
    }

    public boolean isEditPhotos() {
        return editPhotos;
    }

    public boolean isEditNit() {
        return editNit;
    }

    public boolean isEditAddress() {
        return editAddress;
    }

    public boolean isEditDescription() {
        return editDescription;
    }

    public boolean isEditNeighborhood() {
        return editNeighborhood;
    }
    
    public void setEditDescription(boolean editDescription) {
        this.editDescription = editDescription;
    }

    public boolean isEditMap() {
        return editMap;
    }
    
    public String getSportCenterTitle() {
        return sportCenterTitle;
    }

    public void setSportCenterTitle(String sportCenterTitle) {
        this.sportCenterTitle = sportCenterTitle;
    }

    public String getIdNeighborhoodSelected() {
        return idNeighborhoodSelected;
    }

    public void setIdNeighborhoodSelected(String idNeighborhoodSelected) {
        this.idNeighborhoodSelected = idNeighborhoodSelected;
    }

    public String getSportCenterNit() {
        return sportCenterNit;
    }

    public void setSportCenterNit(String sportCenterNit) {
        this.sportCenterNit = sportCenterNit;
    }

    public String getSportCenterAddress() {
        return sportCenterAddress;
    }

    public void setSportCenterAddress(String sportCenterAddress) {
        this.sportCenterAddress = sportCenterAddress;
    }

    public String getSportCenterDescription() {
        return sportCenterDescription;
    }

    public void setSportCenterDescription(String sportCenterDescription) {
        this.sportCenterDescription = sportCenterDescription;
    }

    public String getSportCenterPhoneNumber() {
        return sportCenterPhoneNumber;
    }

    public void setSportCenterPhoneNumber(String sportCenterPhoneNumber) {
        this.sportCenterPhoneNumber = sportCenterPhoneNumber;
    }

    public String getSportCenterPhoneType() {
        return sportCenterPhoneType;
    }

    public void setSportCenterPhoneType(String sportCenterPhoneType) {
        this.sportCenterPhoneType = sportCenterPhoneType;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public List<Centrodeportivotelefono> getSportCenterPhonesF() {
        return sportCenterPhonesF;
    }

    public List<Centrodeportivotelefono> getSportCenterPhonesW() {
        return sportCenterPhonesW;
    }

    public List<Centrodeportivotelefono> getSportCenterPhonesC() {
        return sportCenterPhonesC;
    }

    public List<Barrio> getSportCenterNeighborhoods() {
        if(sportCenterNeighborhoods==null)
        {
            sportCenterNeighborhoods = neighborhoodEJB.findByCityOrderByName(sportCenter.getBarId().getCiuId().getCiuId());
        }
        return sportCenterNeighborhoods;
    }
    
    public List<Centrodeportivofotos> getSportCenterPhotos() {
        if(sportCenterPhotos==null || sportCenterPhotos.isEmpty())
        {
            sportCenterPhotos = sportCenterPhotosEJB.findByCenId(sportCenter.getCenId());
            if(sportCenterPhotos==null || sportCenterPhotos.isEmpty())
            {
                Centrodeportivofotos c = new Centrodeportivofotos();
                c.setCenFoNombre("");
                sportCenterPhotos = new ArrayList<>();
                sportCenterPhotos.add(c);
            }
        }
        return sportCenterPhotos;
    }
    
    public List<Centrodeportivofotos> getEditSportCenterPhotos()
    {
        if(sportCenterPhotos==null || sportCenterPhotos.isEmpty())
        {
            sportCenterPhotos = sportCenterPhotosEJB.findByCenId(sportCenter.getCenId());
        }
        return sportCenterPhotos;
    }
    
    public void editTitleActive()
    {
        editTitle= true;
        sportCenterTitle = sportCenter.getCenNombre();
    }
    
    public void editTitleCancel(){
        editTitle = false;
        sportCenterTitle="";
    }
    
    public void editTitleOk()
    {
        if(!sportCenterTitle.equals(""))
        {
            sportCenterTitle = Util.formatText(sportCenterTitle);
            if(sportCenterTitle.equals(sportCenter.getCenNombre()))
            {
                editTitleCancel();
            }
            else
            {
                if(!sportCenterEJB.existSportCenterName(sportCenter.getBarId().getCiuId().getCiuId(), sportCenterTitle))
                {
                    sportCenter.setCenNombre(sportCenterTitle);
                    sportCenterEJB.edit(sportCenter);
                    editTitleCancel();
                }
                else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "El nombre del centro deportivo ya se encuentra registrado en la ciudad de " + sportCenter.getBarId().getCiuId().getCiuNombre() + "."));
                }
            }
        }
    }
    
    public void editPhotosActive()
    {
        editPhotos= true;
        sportCenterPhotos = null;
    }
    
    public void editPhotosOk(){
        editPhotos = false;
        sportCenterPhotos = null;
    }
    
    public void cargarFoto(FileUploadEvent event) {
        try {
            if(sportCenterPhotos.size()<6)
            {
                if(copyFile(event.getFile().getFileName(), event.getFile().getInputstream()))
                {
                    sportCenterPhotos = null;
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "Imagen subida con exito."));
                }
                else
                {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Ocurrio un problema al subir la imagen."));
                }
            }
            else
            {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "El numero maximo de imagenes permitidas es 6."));
            }
        } catch (IOException ex) {
            Logger.getLogger(SportCenterController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean copyFile(String fileName, InputStream in) {
        try {

            String filePath = Util.RUTAFOTOSCENTROSDEPORTIVOS + sportCenter.getCenId();
            File file = new File(filePath);
            if (!file.exists()) {
                file.mkdirs();
            }
            String[] split = fileName.split(Pattern.quote("."));
            int indice = split.length - 1;
            System.out.println("indice:" + indice);
            String extension = split[indice];

            Centrodeportivofotos c = new Centrodeportivofotos();
            c.setCenId(sportCenter);
            if (sportCenterPhotos == null || sportCenterPhotos.isEmpty()) {
                c.setCenFotPrincipal(true);
            } else {
                c.setCenFotPrincipal(false);
            }
            sportCenterPhotosEJB.create(c);

            filePath = filePath + File.separator + c.getCenFotId() + "." + extension;
            c.setCenFoNombre(sportCenter.getCenId() + File.separator + c.getCenFotId() + "." + extension);
            sportCenterPhotosEJB.edit(c);
            // write the inputStream to a FileOutputStream
            OutputStream out = new FileOutputStream(new File(filePath));

            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            in.close();
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    public void openDefaultImagenDialog(Centrodeportivofotos sportCenterPhoto)
    {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        this.sportCenterPhoto = sportCenterPhoto;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "", "Desea establecer la imagen por defecto?."));
        requestContext.execute("PF('defaulImagenDialog').show()");
    }
    
    public void setDefaultImage()
    {
        for(Centrodeportivofotos c : sportCenterPhotos)
        {
            if(c.getCenFotPrincipal())
            {
                c.setCenFotPrincipal(false);
                sportCenterPhoto.setCenFotPrincipal(true);
                sportCenterPhotosEJB.edit(c);
                sportCenterPhotosEJB.edit(sportCenterPhoto);
                sportCenterPhotos = null;
                break;
            }
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Imagen establecida por defecto."));
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.execute("PF('defaulImagenDialog').hide()");
        requestContext.execute("PF('defaulImagenResultDialog').show()");
    }
    
    public void openDeleteImagenDialog(Centrodeportivofotos sportCenterPhoto)
    {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        this.sportCenterPhoto = sportCenterPhoto;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "", "Desea eliminar la imagen?."));
        requestContext.execute("PF('deleteImagenDialog').show()");
    }
    
    public void deleteImagen()
    {   
        RequestContext requestContext = RequestContext.getCurrentInstance();
        File file = new File(Util.RUTAFOTOSCENTROSDEPORTIVOS+sportCenterPhoto.getCenFoNombre());
        file.delete();
        sportCenterPhotosEJB.remove(sportCenterPhoto);
        sportCenterPhotos = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Imagen eliminada."));
        requestContext.execute("PF('deleteImagenDialog').hide()");
        requestContext.execute("PF('deleteImageResultDialog').show()");
    }
    
    public void editNitActive()
    {
        editNit= true;
        sportCenterNit = sportCenter.getCenNit();
    }
    
    public void editNitCancel(){
        editNit = false;
        sportCenterNit="";
    }
    
    public void editNitOk()
    {
        if(!sportCenterNit.equals(""))
        {
            if(sportCenterNit.equals(sportCenter.getCenNit()))
            {
                editNitCancel();
            }
            else
            {
               sportCenter.setCenNit(sportCenterNit);
               sportCenterEJB.edit(sportCenter);
               editNitCancel();
            }
        }
    }
    
    public void editAddressActive()
    {
        editAddress= true;
        sportCenterAddress = sportCenter.getCenDireccion();
    }
    
    public void editAddressCancel(){
        editAddress = false;
        sportCenterAddress="";
    }
    
    public void editAddressOk()
    {
        if(!sportCenterAddress.equals(""))
        {
            sportCenterAddress = Util.formatText(sportCenterAddress);
            if(sportCenterAddress.equals(sportCenter.getCenDireccion()))
            {
                editAddressCancel();
            }
            else
            {
                if(!sportCenterEJB.existAddress(sportCenter.getBarId().getCiuId().getCiuId(), sportCenterAddress))
                {
                    sportCenter.setCenDireccion(sportCenterAddress);
                    sportCenterEJB.edit(sportCenter);
                    editAddressCancel();
                }
                else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "La dirección del centro deportivo ya se encuentra registrada en la ciudad de " + sportCenter.getBarId().getCiuId().getCiuNombre() + "."));
                }
            }
        }
    }
    
    public void editNeighborhoodActive()
    {
        editNeighborhood= true;
        idNeighborhoodSelected = sportCenter.getBarId().getBarId() +"";
    }
    
    public void editNeighborhoodCancel(){
        editNeighborhood = false;
        idNeighborhoodSelected="";
        sportCenterNeighborhoods = null;
    }
    
    public void editNeighborhoodOk()
    {
        Long id = Long.parseLong(idNeighborhoodSelected);
        
        if(id.equals(sportCenter.getBarId().getBarId()))
        {
            editNeighborhoodCancel();
        }
        else
        {
            Barrio b = neighborhoodEJB.find(id);
            sportCenter.setBarId(b);
            sportCenterEJB.edit(sportCenter);
            editNeighborhoodCancel();
            
        }
    }
    
    
    public void editDescriptionActive()
    {
        editDescription= true;
        sportCenterDescription = sportCenter.getCenDescripcion();
    }
    
    public void editDescriptionCancel(){
        editDescription = false;
        sportCenterDescription="";
    }
    
    public void editDescriptionOk()
    {
        if(!sportCenterDescription.equals(""))
        {
            sportCenterDescription = Util.formatText(sportCenterDescription);
            if(sportCenterDescription.equals(sportCenter.getCenDescripcion()))
            {
                editDescriptionCancel();
            }
            else
            {
                sportCenter.setCenDescripcion(sportCenterDescription);
                sportCenterEJB.edit(sportCenter);
                editDescriptionCancel();
            }
        }
    }
    
    public int getRowSpanPhone()
    {
        int count = 0;
        if(sportCenterPhonesC!=null)
        {
            count = sportCenterPhonesC.size();
        }
        
        if(sportCenterPhonesF!=null)
        {
            count = count + sportCenterPhonesF.size();
        }
        
        if(sportCenterPhonesW!=null)
        {
            count = count + sportCenterPhonesW.size();
        }
        
        return count + 4;
    }
    
    public int getRowSpanPhoneF()
    {
        int count = 0;
        
        if(sportCenterPhonesF!=null)
        {
            count =sportCenterPhonesF.size();
        }
        
        return count + 1;
    }
    
    public int getRowSpanPhoneC()
    {
        int count = 0;
        
        if(sportCenterPhonesC!=null)
        {
            count =sportCenterPhonesC.size();
        }
        
        return count + 1;
    }
    
    public int getRowSpanPhoneW()
    {
        int count = 0;
        
        if(sportCenterPhonesW!=null)
        {
            count =sportCenterPhonesW.size();
        }
        
        return count + 1;
    }
    
    public void openRegisterPhoneDialog()
    {
       RequestContext requestContext = RequestContext.getCurrentInstance();
       this.sportCenterPhoneNumber = "";
       this.sportCenterPhoneType="";
       requestContext.execute("PF('addPhoneDialog').show()");
    }
    
    public void savePhone()
    {
        if(!sportCenterPhoneNumber.equals(""))
        {
            if(!sportCenterPhoneType.equals("s"))
            {
                Centrodeportivotelefono c = new Centrodeportivotelefono();
                c.setCenTelNumero(sportCenterPhoneNumber);
                c.setCenTelTipo(sportCenterPhoneType);
                c.setCenId(sportCenter);
                sportCenterPhonesEJB.create(c);
                initPhones();
                sportCenterPhoneNumber = "";
                sportCenterPhoneType = "";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "Registro exitoso."));
            }
            else
            {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Seleccioné un tipo."));
            }
        }
    }
    
    public void openEditPhoneDialog(Centrodeportivotelefono sporCenterPhone)
    {
       RequestContext requestContext = RequestContext.getCurrentInstance();
       this.sportCenterPhoneSelected = sporCenterPhone;
       this.sportCenterPhoneNumber = sporCenterPhone.getCenTelNumero();
       this.sportCenterPhoneType=sporCenterPhone.getCenTelTipo();
       requestContext.execute("PF('editPhoneDialog').show()");
    }
    
    public void editPhone()
    {
        if(!sportCenterPhoneNumber.equals(""))
        {
            sportCenterPhoneSelected.setCenTelNumero(sportCenterPhoneNumber);
            sportCenterPhoneSelected.setCenTelTipo(sportCenterPhoneType);
            sportCenterPhonesEJB.edit(sportCenterPhoneSelected);
            initPhones();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "Actualización exitosa."));
            
        }
    }
    
    public void openDeletePhoneDialog(Centrodeportivotelefono sporCenterPhone)
    {
       RequestContext requestContext = RequestContext.getCurrentInstance();  
       this.sportCenterPhoneSelected = sporCenterPhone;
       FacesContext.getCurrentInstance().addMessage("deleteMessage", new FacesMessage(FacesMessage.SEVERITY_WARN, "", "Esta seguro que desea eliminar el teléfono?"));
       requestContext.execute("PF('deteletPhoneDialog').show()");
    }
    
    public void deletePhone()
    {
        RequestContext requestContext = RequestContext.getCurrentInstance(); 
        sportCenterPhonesEJB.remove(sportCenterPhoneSelected);
        initPhones();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Teléfono eliminado correctamente."));
        requestContext.execute("PF('deteletPhoneDialog').hide()");
        requestContext.execute("PF('deteletPhoneResultDialog').show()");
    }
    
    public String getMapCenter()
    {
        if(sportCenter.getCenUbicacion()!=null && !sportCenter.getCenUbicacion().equals(""))
        {
            JSONObject jSONObject = new JSONObject(sportCenter.getCenUbicacion());
            String latitude = jSONObject.getString("lat");
            String longitude = jSONObject.getString("long");
            
            return latitude + ","+longitude;
        }
        return "4.6482837,-74.2478964";
    }
    
    public int getMapZom()
    {
        if(sportCenter.getCenUbicacion()!=null && !sportCenter.getCenUbicacion().equals(""))
        {
            return 17;
        }
        return 6;
    }
    
    public MapModel getMark() {
        
        if(sportCenter.getCenUbicacion()!=null && !sportCenter.getCenUbicacion().equals(""))
        {
            
            JSONObject jSONObject = new JSONObject(sportCenter.getCenUbicacion());
            String latitude = jSONObject.getString("lat");
            String longitude = jSONObject.getString("long");
            DefaultMapModel mapModel = new DefaultMapModel();
            LatLng coord = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
            Marker mark = new Marker(coord, sportCenter.getCenNombre());
            mapModel.addOverlay(mark);
            return mapModel;
            
        }
        return new DefaultMapModel();
    }
    
    public MapModel getMarkEdit()
    {
        if(sportCenter.getCenUbicacion()!=null && !sportCenter.getCenUbicacion().equals(""))
        {
            
            JSONObject jSONObject = new JSONObject(sportCenter.getCenUbicacion());
            String latitude = jSONObject.getString("lat");
            String longitude = jSONObject.getString("long");
            DefaultMapModel mapModel = new DefaultMapModel();
            LatLng coord = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
            Marker mark = new Marker(coord, "Localización actual del centro de portivo.");
            mark.setIcon("../../resources/image/location_greey.png");
            mapModel.addOverlay(mark);
            return mapModel;
            
        }
        return new DefaultMapModel();
    }
    
    public void editMapActive()
    {
        editMap= true;
        lat = 0;
        lng = 0;
    }
    
    public void editMapAceptar()
    {
        if(lat!=0 && lng!=0)
        {
            String ubicacion = "{\"lat\":\""+lat+"\",\"long\": \""+lng+"\"}";
            sportCenter.setCenUbicacion(ubicacion);
            sportCenterEJB.edit(sportCenter);
            
        }
        editMap = false;
    }
    
    public void goToStages() {
        try {
            //String uri = Util.projectPath+"/sa/centro?i=1&s="+sportCenter.getCenId();
            String uri = Util.projectPath+"/superadmin/stage/stages.xhtml?i=1&s="+sportCenter.getCenId();
            FacesContext.getCurrentInstance().getExternalContext().redirect(uri);
        } catch (IOException ex) {
            Logger.getLogger(SportCentersController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}

