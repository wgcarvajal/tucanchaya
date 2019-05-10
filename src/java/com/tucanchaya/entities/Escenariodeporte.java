/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tucanchaya.entities;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author wilson carvajal
 */
@Entity
@Table(name = "escenariodeporte", catalog = "tucanchaya", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Escenariodeporte.findAll", query = "SELECT e FROM Escenariodeporte e"),
    @NamedQuery(name = "Escenariodeporte.findByEscId", query = "SELECT e FROM Escenariodeporte e WHERE e.escenariodeportePK.escId = :escId"),
    @NamedQuery(name = "Escenariodeporte.findByDepId", query = "SELECT e FROM Escenariodeporte e WHERE e.escenariodeportePK.depId = :depId"),
    @NamedQuery(name = "Escenariodeporte.findEscByDepId", query = "SELECT e.escenario FROM Escenariodeporte e WHERE e.escenariodeportePK.depId = :depId"),
    @NamedQuery(name = "Escenariodeporte.findDepByEscId", query = "SELECT e.deporte FROM Escenariodeporte e WHERE e.escenariodeportePK.escId = :escId")})
public class Escenariodeporte implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EscenariodeportePK escenariodeportePK;
    @JoinColumn(name = "depId", referencedColumnName = "depId", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Deporte deporte;
    @JoinColumn(name = "escId", referencedColumnName = "escId", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Escenario escenario;

    public Escenariodeporte() {
    }

    public Escenariodeporte(EscenariodeportePK escenariodeportePK) {
        this.escenariodeportePK = escenariodeportePK;
    }

    public Escenariodeporte(long escId, long depId) {
        this.escenariodeportePK = new EscenariodeportePK(escId, depId);
    }

    public EscenariodeportePK getEscenariodeportePK() {
        return escenariodeportePK;
    }

    public void setEscenariodeportePK(EscenariodeportePK escenariodeportePK) {
        this.escenariodeportePK = escenariodeportePK;
    }

    public Deporte getDeporte() {
        return deporte;
    }

    public void setDeporte(Deporte deporte) {
        this.deporte = deporte;
    }

    public Escenario getEscenario() {
        return escenario;
    }

    public void setEscenario(Escenario escenario) {
        this.escenario = escenario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (escenariodeportePK != null ? escenariodeportePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Escenariodeporte)) {
            return false;
        }
        Escenariodeporte other = (Escenariodeporte) object;
        if ((this.escenariodeportePK == null && other.escenariodeportePK != null) || (this.escenariodeportePK != null && !this.escenariodeportePK.equals(other.escenariodeportePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "test.Escenariodeporte[ escenariodeportePK=" + escenariodeportePK + " ]";
    }
}
