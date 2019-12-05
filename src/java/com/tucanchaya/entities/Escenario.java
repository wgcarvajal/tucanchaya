/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tucanchaya.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author aranda
 */
@Entity
@Table(name = "escenario", catalog = "tucanchaya", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Escenario.findAll", query = "SELECT e FROM Escenario e"),
    @NamedQuery(name = "Escenario.findByEscId", query = "SELECT e FROM Escenario e WHERE e.escId = :escId"),
    @NamedQuery(name = "Escenario.findByEscNombre", query = "SELECT e FROM Escenario e WHERE e.escNombre = :escNombre"),
    @NamedQuery(name = "Escenario.findByEscMedidaRealAncho", query = "SELECT e FROM Escenario e WHERE e.escMedidaRealAncho = :escMedidaRealAncho"),
    @NamedQuery(name = "Escenario.findByEscMedidaRealLargo", query = "SELECT e FROM Escenario e WHERE e.escMedidaRealLargo = :escMedidaRealLargo"),
    @NamedQuery(name = "Escenario.findByEscImagenAncho", query = "SELECT e FROM Escenario e WHERE e.escImagenAncho = :escImagenAncho"),
    @NamedQuery(name = "Escenario.findByEscImagenLargo", query = "SELECT e FROM Escenario e WHERE e.escImagenLargo = :escImagenLargo"),
    @NamedQuery(name = "Escenario.findByEscImagenAngulo", query = "SELECT e FROM Escenario e WHERE e.escImagenAngulo = :escImagenAngulo"),
    @NamedQuery(name = "Escenario.findByEscImagenMagenSuperior", query = "SELECT e FROM Escenario e WHERE e.escImagenMagenSuperior = :escImagenMagenSuperior"),
    @NamedQuery(name = "Escenario.findByEscImagenMagenIzquierda", query = "SELECT e FROM Escenario e WHERE e.escImagenMagenIzquierda = :escImagenMagenIzquierda"),
    @NamedQuery(name = "Escenario.findByEscImagen", query = "SELECT e FROM Escenario e WHERE e.escImagen = :escImagen"),
    @NamedQuery(name = "Escenario.findbySportCenterId", query = "SELECT e FROM Escenario e WHERE e.cenId.cenId = :cenId"),
    @NamedQuery(name = "Escenario.findbyStageNameInSporcenter", query = "SELECT e FROM Escenario e WHERE e.cenId.cenId = :cenId AND e.escNombre = :escNombre"),
    @NamedQuery(name = "Escenario.findByEscEstado", query = "SELECT e FROM Escenario e WHERE e.escEstado = :escEstado")})
public class Escenario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "escId")
    private Long escId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "escNombre")
    private String escNombre;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "escMedidaRealAncho")
    private Float escMedidaRealAncho;
    @Column(name = "escMedidaRealLargo")
    private Float escMedidaRealLargo;
    @Column(name = "escImagenAncho")
    private Integer escImagenAncho;
    @Column(name = "escImagenLargo")
    private Integer escImagenLargo;
    @Column(name = "escImagenAngulo")
    private Integer escImagenAngulo;
    @Column(name = "escImagenMagenSuperior")
    private Integer escImagenMagenSuperior;
    @Column(name = "escImagenMagenIzquierda")
    private Integer escImagenMagenIzquierda;
    @Size(max = 200)
    @Column(name = "escImagen")
    private String escImagen;
    @Column(name = "escEstado")
    private Integer escEstado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "escEsReservable")
    private boolean escEsReservable;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "escId")
    private List<Escenariofotos> escenariofotosList;
    @JoinColumn(name = "cenId", referencedColumnName = "cenId")
    @ManyToOne(optional = false)
    private Centrodeportivo cenId;
    @JoinColumn(name = "depId", referencedColumnName = "depId")
    @ManyToOne(optional = false)
    private Deporte depId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "escId")
    private List<Escenariocompuesto> escenariocompuestoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "escCompId")
    private List<Escenariocompuesto> escenariocompuestoList1;

    public Escenario() {
    }

    public Escenario(Long escId) {
        this.escId = escId;
    }

    public Escenario(Long escId, String escNombre, boolean escEsReservable) {
        this.escId = escId;
        this.escNombre = escNombre;
        this.escEsReservable = escEsReservable;
    }

    public Long getEscId() {
        return escId;
    }

    public void setEscId(Long escId) {
        this.escId = escId;
    }

    public String getEscNombre() {
        return escNombre;
    }

    public void setEscNombre(String escNombre) {
        this.escNombre = escNombre;
    }

    public Float getEscMedidaRealAncho() {
        return escMedidaRealAncho;
    }

    public void setEscMedidaRealAncho(Float escMedidaRealAncho) {
        this.escMedidaRealAncho = escMedidaRealAncho;
    }

    public Float getEscMedidaRealLargo() {
        return escMedidaRealLargo;
    }

    public void setEscMedidaRealLargo(Float escMedidaRealLargo) {
        this.escMedidaRealLargo = escMedidaRealLargo;
    }

    public Integer getEscImagenAncho() {
        return escImagenAncho;
    }

    public void setEscImagenAncho(Integer escImagenAncho) {
        this.escImagenAncho = escImagenAncho;
    }

    public Integer getEscImagenLargo() {
        return escImagenLargo;
    }

    public void setEscImagenLargo(Integer escImagenLargo) {
        this.escImagenLargo = escImagenLargo;
    }

    public Integer getEscImagenAngulo() {
        return escImagenAngulo;
    }

    public void setEscImagenAngulo(Integer escImagenAngulo) {
        this.escImagenAngulo = escImagenAngulo;
    }

    public Integer getEscImagenMagenSuperior() {
        return escImagenMagenSuperior;
    }

    public void setEscImagenMagenSuperior(Integer escImagenMagenSuperior) {
        this.escImagenMagenSuperior = escImagenMagenSuperior;
    }

    public Integer getEscImagenMagenIzquierda() {
        return escImagenMagenIzquierda;
    }

    public void setEscImagenMagenIzquierda(Integer escImagenMagenIzquierda) {
        this.escImagenMagenIzquierda = escImagenMagenIzquierda;
    }

    public String getEscImagen() {
        return escImagen;
    }

    public void setEscImagen(String escImagen) {
        this.escImagen = escImagen;
    }

    public Integer getEscEstado() {
        return escEstado;
    }

    public void setEscEstado(Integer escEstado) {
        this.escEstado = escEstado;
    }

    public boolean getEscEsReservable() {
        return escEsReservable;
    }

    public void setEscEsReservable(boolean escEsReservable) {
        this.escEsReservable = escEsReservable;
    }

    @XmlTransient
    public List<Escenariofotos> getEscenariofotosList() {
        return escenariofotosList;
    }

    public void setEscenariofotosList(List<Escenariofotos> escenariofotosList) {
        this.escenariofotosList = escenariofotosList;
    }

    public Centrodeportivo getCenId() {
        return cenId;
    }

    public void setCenId(Centrodeportivo cenId) {
        this.cenId = cenId;
    }

    public Deporte getDepId() {
        return depId;
    }

    public void setDepId(Deporte depId) {
        this.depId = depId;
    }

    @XmlTransient
    public List<Escenariocompuesto> getEscenariocompuestoList() {
        return escenariocompuestoList;
    }

    public void setEscenariocompuestoList(List<Escenariocompuesto> escenariocompuestoList) {
        this.escenariocompuestoList = escenariocompuestoList;
    }

    @XmlTransient
    public List<Escenariocompuesto> getEscenariocompuestoList1() {
        return escenariocompuestoList1;
    }

    public void setEscenariocompuestoList1(List<Escenariocompuesto> escenariocompuestoList1) {
        this.escenariocompuestoList1 = escenariocompuestoList1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (escId != null ? escId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Escenario)) {
            return false;
        }
        Escenario other = (Escenario) object;
        if ((this.escId == null && other.escId != null) || (this.escId != null && !this.escId.equals(other.escId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tucanchaya.entities.tucanchaya.entities.Escenario[ escId=" + escId + " ]";
    }
}
