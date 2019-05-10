/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tucanchaya.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author wilson carvajal
 */
@Embeddable
public class EscenariodeportePK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "escId")
    private long escId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "depId")
    private long depId;

    public EscenariodeportePK() {
    }

    public EscenariodeportePK(long escId, long depId) {
        this.escId = escId;
        this.depId = depId;
    }

    public long getEscId() {
        return escId;
    }

    public void setEscId(long escId) {
        this.escId = escId;
    }

    public long getDepId() {
        return depId;
    }

    public void setDepId(long depId) {
        this.depId = depId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) escId;
        hash += (int) depId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EscenariodeportePK)) {
            return false;
        }
        EscenariodeportePK other = (EscenariodeportePK) object;
        if (this.escId != other.escId) {
            return false;
        }
        if (this.depId != other.depId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "test.EscenariodeportePK[ escId=" + escId + ", depId=" + depId + " ]";
    }
    
}
