/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tucanchaya.controller;

import com.tucanchaya.controller.util.Util;
import com.tucanchaya.entities.Centrodeportivo;
import com.tucanchaya.entities.Centrodeportivofotos;
import com.tucanchaya.facade.CentrodeportivoFacade;
import com.tucanchaya.facade.CentrodeportivofotosFacade;
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
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;

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
    private Centrodeportivo sportCenter;
    private String sportCenterTitle;
    private List<Centrodeportivofotos> sportCenterPhotos;
    private Centrodeportivofotos sportCenterPhoto;
    
    
    private boolean editTitle = false;
    private boolean editPhotos = false;
    //private String sportCenterId;
    
    
    
    /*public void setSportCenterId(String s)
    {
       sportCenterId = s;
      
    }
    
    public String getSportCenterId()
    {
        return sportCenterId;
    }
    
    public void before(ComponentSystemEvent event){
        if (!FacesContext.getCurrentInstance().isPostback()){
            System.out.println("PreRenderView: view parameters are available here");
            System.out.println("Name: " + sportCenterId);
            
           initVars();
           if(sportCenterId!=null && !sportCenterId.equals(""))
           {
               try{
                   Long id = Long.parseLong(sportCenterId);
                   sportCenter = sportCenterEJB.find(id);
                   if(sportCenter==null)
                   {
                       sportCenterId = null;
                       goCentros();
                   }
                   sportCenterId = null;
               }
               catch(NumberFormatException e)
               {
                   sportCenterId = null;
                   goCentros();
               }
           }
           else{
             sportCenterId = null;
             goCentros();
           }
        }
    }
    
    private void initVars()
    {
        sportCenter = null;
        sportCenterTitle = null;
        sportCenterPhotos = null;
    }*/
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
    
    public String getSportCenterTitle() {
        return sportCenterTitle;
    }

    public void setSportCenterTitle(String sportCenterTitle) {
        this.sportCenterTitle = sportCenterTitle;
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
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.execute("PF('defaulImagenDialog').hide()");
    }
}

