/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tucanchaya.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Wilson Carvajal
 */
@Entity
@Table(name = "escenariofotos", catalog = "tucanchaya", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Escenariofotos.findAll", query = "SELECT e FROM Escenariofotos e"),
    @NamedQuery(name = "Escenariofotos.findByEscId", query = "SELECT e FROM Escenariofotos e WHERE e.escId.escId = :escId"),
    @NamedQuery(name = "Escenariofotos.findByEscFotId", query = "SELECT e FROM Escenariofotos e WHERE e.escFotId = :escFotId"),
    @NamedQuery(name = "Escenariofotos.findByEscFotNombre", query = "SELECT e FROM Escenariofotos e WHERE e.escFotNombre = :escFotNombre")})
public class Escenariofotos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "escFotId")
    private Long escFotId;
    @Size(max = 200)
    @Column(name = "escFotNombre")
    private String escFotNombre;
    @JoinColumn(name = "escId", referencedColumnName = "escId")
    @ManyToOne(optional = false)
    private Escenario escId;

    public Escenariofotos() {
    }

    public Escenariofotos(Long escFotId) {
        this.escFotId = escFotId;
    }

    public Long getEscFotId() {
        return escFotId;
    }

    public void setEscFotId(Long escFotId) {
        this.escFotId = escFotId;
    }

    public String getEscFotNombre() {
        return escFotNombre;
    }

    public void setEscFotNombre(String escFotNombre) {
        this.escFotNombre = escFotNombre;
    }

    public Escenario getEscId() {
        return escId;
    }

    public void setEscId(Escenario escId) {
        this.escId = escId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (escFotId != null ? escFotId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Escenariofotos)) {
            return false;
        }
        Escenariofotos other = (Escenariofotos) object;
        if ((this.escFotId == null && other.escFotId != null) || (this.escFotId != null && !this.escFotId.equals(other.escFotId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tucanchaya.entities.Escenariofotos[ escFotId=" + escFotId + " ]";
    }
    
}

