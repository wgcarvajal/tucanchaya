/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tucanchaya.controller;

import com.tucanchaya.controller.util.Util;
import com.tucanchaya.entities.Centrodeportivo;
import com.tucanchaya.entities.Color;
import com.tucanchaya.entities.Deporte;
import com.tucanchaya.entities.Escenario;
import com.tucanchaya.entities.Escenariodeporte;
import com.tucanchaya.entities.EscenariodeportePK;
import com.tucanchaya.entities.Escenariofotos;
import com.tucanchaya.facade.CentrodeportivoFacade;
import com.tucanchaya.facade.ColorFacade;
import com.tucanchaya.facade.DeporteFacade;
import com.tucanchaya.facade.EscenarioFacade;
import com.tucanchaya.facade.EscenariodeporteFacade;
import com.tucanchaya.facade.EscenariofotosFacade;
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
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Wilson Carvajal
 */
@ManagedBean
@ViewScoped
public class StageController implements Serializable{
    
    @EJB
    private CentrodeportivoFacade sportCenterEJB;
    @EJB
    private ColorFacade colorEJB;
    @EJB
    private EscenarioFacade stageEJB;
    @EJB
    private DeporteFacade sportEJB;
    @EJB
    private EscenariodeporteFacade stageSportEJB;
    @EJB
    private EscenariofotosFacade stagePhotosEJB;
    
    private Centrodeportivo sportCenter;
    private boolean editColor = false;
    private boolean editHeight = false;
    private boolean editNombre = false;
    private boolean editMeasure = false;
    private boolean editImage = false;
    private boolean editWidthMeasure = false;
    private boolean editLongMeasure = false;
    private boolean editPhotos = false;
    private List<Color> colors;
    private Color colorSelected;
    private int heightCurrent;
    private int height;
    private Float widthMeasure;
    private Float longMeasure;
    private List<Escenario> stageList;
    private List<Deporte> sports;
    private List<Deporte> sportsStageSelected;
    private List<Escenariofotos> stagePhotos;
    private String nombre;
    private Escenario  stageSelected;
    private int stageWidthCurrent;
    private int stageHeightCurrent;
    private int stageAngleCurrent;
    private int stageMarginUpCurrent;
    private int stageMarginLeftCurrent;
    private int stageWidth;
    private int stageHeight;
    private int stageAngle;
    private int stageMarginUp;
    private int stageMarginLeft;
    private UploadedFile stageImage;
    
    private Deporte sport;
    private Deporte sportSelected;
    private Escenariofotos stagePhoto; 
    
    
    
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

    public Escenario getStageSelected() {
        return stageSelected;
    }

    
    
    public boolean isEditColor() {
        return editColor;
    }

    public boolean isEditHeight() {
        return editHeight;
    }

    public boolean isEditImage() {
        return editImage;
    }
    
    public int getHeight() {
        return height;
    }

    public boolean isEditNombre() {
        return editNombre;
    }

    public boolean isEditMeasure() {
        return editMeasure;
    }

    public boolean isEditWidthMeasure() {
        return editWidthMeasure;
    }

    public boolean isEditLongMeasure() {
        return editLongMeasure;
    }
    
    public boolean isEditPhotos() {
        return editPhotos;
    }
    
    public List<Color> getColors() {
        
        if(colors==null)
        {
            colors = colorEJB.findAll();
        }
        return colors;
    }

    public List<Deporte> getSports() {
        sports = sportEJB.findAll();
        return sports;
    }

    public UploadedFile getStageImage() {
        return stageImage;
    }

    public Deporte getSport() {
        return sport;
    }

    public void setSport(Deporte sport) {
        this.sport = sport;
    }

    public int getStageWidth() {
        return stageWidth;
    }

    public int getStageHeight() {
        return stageHeight;
    }

    public int getStageAngle() {
        return stageAngle;
    }

    public int getStageMarginUp() {
        return stageMarginUp;
    }

    public int getStageMarginLeft() {
        return stageMarginLeft;
    }

    public Float getWidthMeasure() {
        return widthMeasure;
    }
    
    public void setWidthMeasure(Float widthMeasure) {
        this.widthMeasure = widthMeasure;
    }

    public Float getLongMeasure() {
        return longMeasure;
    }

    public void setLongMeasure(Float longMeasure) {
        this.longMeasure = longMeasure;
    }
    
    public Color getColorSelected() {
        return colorSelected;
    }

    public void setColorSelected(Color colorSelected) {
        this.colorSelected = colorSelected;
    }

    public List<Escenario> getStageList() {
        if(stageList == null)
        {
            stageList = stageEJB.findbySportCenterId(sportCenter.getCenId());
        }
        return stageList;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    
    
    public Centrodeportivo getSportCenter() {
        return sportCenter;
    }
    
    private void goCentros() {
        try {
            String uri = Util.projectPath+"/superadmin/sportCenter/sportCenters.xhtml?i=1";
            FacesContext.getCurrentInstance().getExternalContext().redirect(uri);
        } catch (IOException ex) {
            Logger.getLogger(SportCentersController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String getColor()
    {
        if(sportCenter.getColorId()!=null)
        {
            return sportCenter.getColorId().getColorHexadecimal();
        }
        return "#BDBDBD";
    }

    public List<Deporte> getSportsStageSelected() {
        if(sportsStageSelected == null)
        {
            sportsStageSelected = stageSportEJB.findDepByEscId(stageSelected.getEscId());
        }
        return sportsStageSelected;
    }
    
    
    
    public void goEditColor()
    {
        editColor = true; 
    }
    
    public void editOk()
    {
        sportCenter.setColorId(colorSelected);
        sportCenterEJB.edit(sportCenter);
        colorSelected = null;
        editColor = false;
    }
    
    public void editCancel()
    {
         editColor = false;
         colorSelected = null;
    }
    
    public void goEditHeight()
    {
        heightCurrent = sportCenter.getCenAlto();
        height = sportCenter.getCenAlto();
        editHeight = true;
    }
    
    public void increaseHeight()
    {
        height++;
        sportCenter.setCenAlto(height);
    }
    
    public void decreaseHeight()
    {
        height --;
        sportCenter.setCenAlto(height);
    }
    
    public void editHeightOk()
    {
        if(heightCurrent!= height)
        {
            sportCenterEJB.edit(sportCenter);
        }
        editHeight = false;
    }
    
    public void editHeightCancel()
    {
        sportCenter.setCenAlto(heightCurrent);
        editHeight = false;
        
    }
    
    public void openAddStageDialog()
    {
       RequestContext requestContext = RequestContext.getCurrentInstance();
       this.nombre = "";
       requestContext.execute("PF('addStageDialog').show()");
    }
    
    public void saveStage()
    {
        if(!nombre.equals(""))
        {
            nombre = Util.formatText(nombre);
            
            if(!stageEJB.existStageNameInSporcenter(sportCenter.getCenId(), nombre))
            {
                Escenario esc = new Escenario();
                esc.setEscNombre(nombre);
                esc.setCenId(sportCenter);
                esc.setEscImagenAncho(150);
                esc.setEscImagenLargo(100);
                esc.setEscImagenAngulo(0);
                esc.setEscImagenMagenSuperior(0);
                esc.setEscImagenMagenIzquierda(0);
                esc.setEscEsReservable(true);
                stageEJB.create(esc);
                stageList = null;
                nombre = "";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Escenario registrado correctamente."));
            }
            else
            {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "El nombre del escenario ya se encuentra registrado."));
            }
        }
    }
    
    public void openAddObjectDialog()
    {
       RequestContext requestContext = RequestContext.getCurrentInstance();
       this.nombre = "";
       requestContext.execute("PF('addObjectDialog').show()");
    }
    
    public void saveObject()
    {
        if(!nombre.equals(""))
        {
            nombre = Util.formatText(nombre);
            
            if(!stageEJB.existStageNameInSporcenter(sportCenter.getCenId(), nombre))
            {
                Escenario esc = new Escenario();
                esc.setEscNombre(nombre);
                esc.setCenId(sportCenter);
                esc.setEscImagenAncho(150);
                esc.setEscImagenLargo(100);
                esc.setEscImagenAngulo(0);
                esc.setEscImagenMagenSuperior(0);
                esc.setEscImagenMagenIzquierda(0);
                esc.setEscEsReservable(false);
                stageEJB.create(esc);
                stageList = null;
                nombre = "";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Objeto registrado correctamente."));
            }
            else
            {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "El nombre del objecto ya se encuentra registrado."));
            }
        }
    }
    
    public boolean renderedEditStage()
    {
        return stageSelected!=null;
    }
    
    public boolean isStagedSelected(Escenario stage)
    {
        return stageSelected!=null && stageSelected.getEscId().equals(stage.getEscId());
    }
    
    public void stagedSelected(Escenario stageSelected)
    {
        if(this.stageSelected!=null)
        {
            Escenario esc = stageEJB.find(this.stageSelected.getEscId());
            this.stageSelected.setEscNombre(esc.getEscNombre());
            this.stageSelected.setEscImagenAncho(esc.getEscImagenAncho());
            this.stageSelected.setEscImagenLargo(esc.getEscImagenLargo());
            this.stageSelected.setEscImagenAngulo(esc.getEscImagenAngulo());
            this.stageSelected.setEscImagenMagenSuperior(esc.getEscImagenMagenSuperior());
            this.stageSelected.setEscImagenMagenIzquierda(esc.getEscImagenMagenIzquierda());
        }
        this.stageSelected = stageSelected;
        this.stagePhotos = null;
        this.sportsStageSelected = null;
        editNombre = false;
        editMeasure = false;
        editImage = false;
        editWidthMeasure = false;
        editLongMeasure = false;
        editPhotos = false;
    }
    
    public void goEditName()
    {
        editNombre = true;
        nombre = stageSelected.getEscNombre();
    }
    
    public void editNameOK()
    {
        if(!nombre.equals(""))
        {
            nombre = Util.formatText(nombre);
            if (!nombre.equals(stageSelected.getEscNombre())) {
                if (!stageEJB.existStageNameInSporcenter(sportCenter.getCenId(), nombre)) {
                    stageSelected.setEscNombre(nombre);
                    stageEJB.edit(stageSelected);
                    nombre = "";
                    editNombre = false;
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "El nombre del escenario ya se encuentra registrado."));
                }
            }
        }
    }
    
    public void editNameCancel()
    {
        nombre = "";
        editNombre = false;
    }
    
    public void goEditMeasure()
    {
        editMeasure = true;
        stageWidthCurrent = stageSelected.getEscImagenAncho();
        stageHeightCurrent = stageSelected.getEscImagenLargo();
        stageAngleCurrent = stageSelected.getEscImagenAngulo();
        stageMarginUpCurrent = stageSelected.getEscImagenMagenSuperior();
        stageMarginLeftCurrent = stageSelected.getEscImagenMagenIzquierda();
        stageWidth = stageSelected.getEscImagenAncho();
        stageHeight = stageSelected.getEscImagenLargo();
        stageAngle = stageSelected.getEscImagenAngulo();
        stageMarginUp = stageSelected.getEscImagenMagenSuperior();
        stageMarginLeft = stageSelected.getEscImagenMagenIzquierda();
    }
    
    public void increaseWidthStage()
    {
        stageWidth++;
        stageSelected.setEscImagenAncho(stageWidth);
    }
    
    public void decreaseWidthStage()
    {
        stageWidth--;
        stageSelected.setEscImagenAncho(stageWidth);
    }
    
    public void increaseHeightStage()
    {
        stageHeight++;
        stageSelected.setEscImagenLargo(stageHeight);
    }
    
    public void decreaseHeightStage()
    {
        stageHeight--;
        stageSelected.setEscImagenLargo(stageHeight);
    }
    
    public void increaseAngleStage()
    {
        if(stageAngle==359)
        {
            stageAngle = 0;
        }
        else
        {
            stageAngle++;
        }
        stageSelected.setEscImagenAngulo(stageAngle);
    }
    
    public void decreaseAngleStage()
    {
        if(stageAngle==0)
        {
            stageAngle = 359;
        }
        else
        {
            stageAngle--;
        }
        stageSelected.setEscImagenAngulo(stageAngle);
    }
    
    public void increaseMarginUpStage()
    {
        stageMarginUp++;
        stageSelected.setEscImagenMagenSuperior(stageMarginUp);
    }
    
    public void decreaseMarginUpStage()
    {
        stageMarginUp--;
        stageSelected.setEscImagenMagenSuperior(stageMarginUp);
    }
    
    public void goUpMarginUpStage()
    {
        stageMarginUp =0;
        stageSelected.setEscImagenMagenSuperior(stageMarginUp);
    }
    
    public void goCenterMarginUpStage()
    {
        int tamanoReal= sportCenter.getCenAlto()- stageSelected.getEscImagenLargo();
        stageMarginUp=tamanoReal/2;
        stageSelected.setEscImagenMagenSuperior(stageMarginUp);
    }
    
    public void goDownMarginUpStage()
    {
       stageMarginUp=sportCenter.getCenAlto()- stageSelected.getEscImagenLargo();
       stageSelected.setEscImagenMagenSuperior(stageMarginUp); 
    }
    
    public void increaseMarginLeftStage()
    {
        stageMarginLeft++;
        stageSelected.setEscImagenMagenIzquierda(stageMarginLeft);
    }
    
    public void decreaseMarginLeftStage()
    {
        stageMarginLeft--;
        stageSelected.setEscImagenMagenIzquierda(stageMarginLeft);
    }
    
    public void goLeftMarginleftStage()
    {
        stageMarginLeft =0;
        stageSelected.setEscImagenMagenIzquierda(stageMarginLeft);
    }
    
    public void goCenterMarginLeftStage()
    {
        int tamanoReal= 900 - stageSelected.getEscImagenAncho();
        stageMarginLeft=tamanoReal/2;
        stageSelected.setEscImagenMagenIzquierda(stageMarginLeft);
    }
    
    public void goRightMarginLeftStage()
    {
       stageMarginLeft= 900 - stageSelected.getEscImagenAncho();
       stageSelected.setEscImagenMagenIzquierda(stageMarginLeft);
    }
    
    public void editMeasureOk()
    {
        if(stageWidthCurrent != stageWidth ||
           stageHeightCurrent != stageHeight ||
           stageAngleCurrent != stageAngle ||
           stageMarginUpCurrent != stageMarginUp ||
           stageMarginLeftCurrent != stageMarginLeft){
            stageEJB.edit(stageSelected);
        }
        editMeasure = false;
    }
    
    public void editMeasureCancel()
    {
        stageSelected.setEscImagenAncho(stageWidthCurrent);
        stageSelected.setEscImagenLargo(stageHeightCurrent);
        stageSelected.setEscImagenAngulo(stageAngleCurrent);
        stageSelected.setEscImagenMagenSuperior(stageMarginUpCurrent);
        stageSelected.setEscImagenMagenIzquierda(stageMarginLeftCurrent);
        editMeasure = false;
    }
    
    public void endEditStage()
    {
        editNombre = false;
        editMeasure = false;
        editImage = false;
        editWidthMeasure = false;
        editLongMeasure = false;
        stageSelected = null;
        stageList = null;
    }
    
    public void goChangeImageStage()
    {
        editImage = true;
        stageImage = null;
    }
    
    public void changeImageStageEvent(FileUploadEvent event)
    {
        this.stageImage = event.getFile();
    }
    
    public void changeImageStageOk()
    {
        if(stageImage!=null)
        {
            try {
                copyFile(stageImage.getFileName(), stageImage.getInputstream());
                editImage = false;
            } catch (IOException ex) {
                Logger.getLogger(StageController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void changeImageStageCancel()
    {
        editImage = false;
    }
    
    public boolean copyFile(String fileName, InputStream in) {
        try {

            String filePath = Util.RUTAIMAGENESESCENARIOS + stageSelected.getEscId();
            File file = new File(filePath);
            if (!file.exists()) {
                file.mkdirs();
            }
            String[] split = fileName.split(Pattern.quote("."));
            int indice = split.length - 1;
            System.out.println("indice:" + indice);
            String extension = split[indice];
            
            String name;
            
            if(stageSelected.getEscImagen()!=null && !stageSelected.getEscImagen().equals(""))
            {
                File image = new File(Util.RUTAIMAGENESESCENARIOS + stageSelected.getEscImagen());
                if(image.exists())
                {
                    image.delete();
                }
                
                String currentImage = stageSelected.getEscImagen();
                String[] splitCurrentImage = currentImage.split(File.separator);
                String[] splitNumber = splitCurrentImage[1].split(Pattern.quote("."));
                int number =Integer.parseInt(splitNumber[0]);
                number++;
                name = number +"."+extension;
                
                
            }
            else
            {
                name = "1" + "." + extension;  
            }
            
            
            
            filePath = filePath + File.separator + name;
            
            
            
            // write the inputStream to a FileOutputStream
            OutputStream out = new FileOutputStream(new File(filePath));

            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            
            stageSelected.setEscImagen(stageSelected.getEscId() + File.separator +name);
            stageEJB.edit(stageSelected);
            
            in.close();
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    public void goEditWidthMeasure()
    {
        widthMeasure = stageSelected.getEscMedidaRealAncho();
        editWidthMeasure = true;
    }
    
    public void saveWidthMeasure()
    {
        if(widthMeasure!= null)
        {
            if(!widthMeasure.equals(stageSelected.getEscMedidaRealAncho()))
            {
                stageSelected.setEscMedidaRealAncho(widthMeasure);
                stageEJB.edit(stageSelected);
            }
            widthMeasure = null;
            editWidthMeasure = false;
        }
    }
    
    public void cancelWidthMeasure()
    {
        widthMeasure = null;
        editWidthMeasure = false;
    }
    
    public void goEditLongMeasure()
    {
        longMeasure = stageSelected.getEscMedidaRealLargo();
        editLongMeasure = true;
    }
    
    public void saveLongMeasure()
    {
        if(longMeasure!= null)
        {
            if(!longMeasure.equals(stageSelected.getEscMedidaRealLargo()))
            {
                stageSelected.setEscMedidaRealLargo(longMeasure);
                stageEJB.edit(stageSelected);
            }
            longMeasure = null;
            editLongMeasure = false;
        }
    }
    
    public void cancelLongMeasure()
    {
        longMeasure = null;
        editLongMeasure = false;
    }
    
    public void openDialogAddSport(){
       RequestContext requestContext = RequestContext.getCurrentInstance();
       this.sport = null;
       requestContext.execute("PF('addSportDialog').show()");  
    }
    
    public void addSportOK()
    {
        if(sportsStageSelected!= null && !sportsStageSelected.isEmpty())
        {
            for(Deporte s: sportsStageSelected)
            {
                if(s.getDepId().equals(sport.getDepId()))
                {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El deporte ya se ecuentra en la lista."));
                    return;
                }
            }
        }
        Escenariodeporte escenariodeporte = new Escenariodeporte();
        EscenariodeportePK escenariodeportePK = new EscenariodeportePK(stageSelected.getEscId(),sport.getDepId());
        escenariodeporte.setEscenariodeportePK(escenariodeportePK);
        escenariodeporte.setDeporte(sport);
        escenariodeporte.setEscenario(stageSelected);
        stageSportEJB.create(escenariodeporte);
        sportsStageSelected = null;
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.execute("PF('addSportDialog').hide()");
        requestContext.update("formSports");
    }
    
    public void openDeleteSport(Deporte sport)
    {
        sportSelected = sport;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "", "Desea eliminar el deporte de la lista?."));
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.execute("PF('deleteSportDialog').show()");
    }
    
    public void deleteSport(){
        EscenariodeportePK escenariodeportePK = new EscenariodeportePK(stageSelected.getEscId(),sportSelected.getDepId());
        Escenariodeporte escenariodeporte = new Escenariodeporte(escenariodeportePK);
        stageSportEJB.remove(escenariodeporte);
        sportsStageSelected = null;
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.execute("PF('deleteSportDialog').hide()");
        requestContext.update("formSports");
    }
    
    public List<Escenariofotos> getStagePhotos() {
        if(stagePhotos==null || stagePhotos.isEmpty())
        {
            stagePhotos = stagePhotosEJB.findByEscId(stageSelected.getEscId());
            if(stagePhotos==null || stagePhotos.isEmpty())
            {
                Escenariofotos s = new Escenariofotos();
                s.setEscFotNombre("");
                stagePhotos = new ArrayList<>();
                stagePhotos.add(s);
            }
        }
        return stagePhotos;
    }
    
    public List<Escenariofotos> getEditStagePhotos()
    {
        if(stagePhotos==null || stagePhotos.isEmpty())
        {
            stagePhotos = stagePhotosEJB.findByEscId(stageSelected.getEscId());
        }
        return stagePhotos;
    }
    
    public void editPhotosActive()
    {
        editPhotos= true;
        stagePhotos = null;
    }
    
    public void editPhotosOk(){
        editPhotos = false;
        stagePhotos = null;
    }
    
    public void cargarFoto(FileUploadEvent event) {
        try {
            if(stagePhotos.size()<3)
            {
                if(copyPhotoFile(event.getFile().getFileName(), event.getFile().getInputstream()))
                {
                    stagePhotos = null;
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "Imagen subida con exito."));
                }
                else
                {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Ocurrio un problema al subir la imagen."));
                }
            }
            else
            {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "El numero maximo de imagenes permitidas es 3."));
            }
        } catch (IOException ex) {
            Logger.getLogger(SportCenterController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean copyPhotoFile(String fileName, InputStream in) {
        try {

            String filePath = Util.RUTAFOTOSESCENARIOS + stageSelected.getEscId();
            File file = new File(filePath);
            if (!file.exists()) {
                file.mkdirs();
            }
            String[] split = fileName.split(Pattern.quote("."));
            int indice = split.length - 1;
            System.out.println("indice:" + indice);
            String extension = split[indice];
            Escenariofotos e = new Escenariofotos();
            e.setEscId(stageSelected);
            stagePhotosEJB.create(e);
            
            filePath = filePath + File.separator + e.getEscFotId()+ "." + extension;
            e.setEscFotNombre(stageSelected.getEscId() + File.separator + e.getEscFotId() + "." + extension);
            stagePhotosEJB.edit(e);
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
    
    public void openDeleteImagenDialog(Escenariofotos stagePhoto)
    {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        this.stagePhoto = stagePhoto;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "", "Desea eliminar la Foto?."));
        requestContext.execute("PF('deleteImagenDialog').show()");
    }
    
    public void deleteImagen()
    {   
        RequestContext requestContext = RequestContext.getCurrentInstance();
        File file = new File(Util.RUTAFOTOSESCENARIOS+stagePhoto.getEscFotNombre());
        file.delete();
        stagePhotosEJB.remove(stagePhoto);
        stagePhotos = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "foto eliminada."));
        requestContext.execute("PF('deleteImagenDialog').hide()");
        requestContext.execute("PF('deleteImageResultDialog').show()");
    }
}
