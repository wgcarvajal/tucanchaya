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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author aranda
 */
@Entity
@Table(name = "escenariocompuesto", catalog = "tucanchaya", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Escenariocompuesto.findAll", query = "SELECT e FROM Escenariocompuesto e"),
    @NamedQuery(name = "Escenariocompuesto.findById", query = "SELECT e FROM Escenariocompuesto e WHERE e.id = :id")})
public class Escenariocompuesto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Long id;
    @JoinColumn(name = "escId", referencedColumnName = "escId")
    @ManyToOne(optional = false)
    private Escenario escId;
    @JoinColumn(name = "escCompId", referencedColumnName = "escId")
    @ManyToOne(optional = false)
    private Escenario escCompId;

    public Escenariocompuesto() {
    }

    public Escenariocompuesto(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Escenario getEscId() {
        return escId;
    }

    public void setEscId(Escenario escId) {
        this.escId = escId;
    }

    public Escenario getEscCompId() {
        return escCompId;
    }

    public void setEscCompId(Escenario escCompId) {
        this.escCompId = escCompId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Escenariocompuesto)) {
            return false;
        }
        Escenariocompuesto other = (Escenariocompuesto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tucanchaya.entities.Escenariocompuesto[ id=" + id + " ]";
    }
    
}
