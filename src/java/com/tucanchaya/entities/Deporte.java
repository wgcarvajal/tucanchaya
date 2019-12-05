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
 * @author wilson Carvajal
 */
@Entity
@Table(name = "deporte", catalog = "tucanchaya", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Deporte.findAll", query = "SELECT d FROM Deporte d"),
    @NamedQuery(name = "Deporte.findByColumn", query = "SELECT d FROM Deporte d WHERE LOWER(d.depNombre) LIKE :depNombre Order by d.depNombre asc"),
    @NamedQuery(name = "Deporte.findByDepId", query = "SELECT d FROM Deporte d WHERE d.depId = :depId"),
    @NamedQuery(name = "Deporte.findByDepNombre", query = "SELECT d FROM Deporte d WHERE d.depNombre = :depNombre")})
public class Deporte implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "depId")
    private Long depId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "depNombre")
    private String depNombre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "depId")
    private List<Centrodeportivodeporte> centrodeportivodeporteList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "depId")
    private List<Escenario> escenarioList;

    public Deporte() {
    }

    public Deporte(Long depId) {
        this.depId = depId;
    }

    public Deporte(Long depId, String depNombre) {
        this.depId = depId;
        this.depNombre = depNombre;
    }

    public Long getDepId() {
        return depId;
    }

    public void setDepId(Long depId) {
        this.depId = depId;
    }

    public String getDepNombre() {
        return depNombre;
    }

    public void setDepNombre(String depNombre) {
        this.depNombre = depNombre;
    }

    @XmlTransient
    public List<Centrodeportivodeporte> getCentrodeportivodeporteList() {
        return centrodeportivodeporteList;
    }

    public void setCentrodeportivodeporteList(List<Centrodeportivodeporte> centrodeportivodeporteList) {
        this.centrodeportivodeporteList = centrodeportivodeporteList;
    }

    @XmlTransient
    public List<Escenario> getEscenarioList() {
        return escenarioList;
    }

    public void setEscenarioList(List<Escenario> escenarioList) {
        this.escenarioList = escenarioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (depId != null ? depId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Deporte)) {
            return false;
        }
        Deporte other = (Deporte) object;
        if ((this.depId == null && other.depId != null) || (this.depId != null && !this.depId.equals(other.depId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tucanchaya.entities.Deporte[ depId=" + depId + " ]";
    }
    
    
}
