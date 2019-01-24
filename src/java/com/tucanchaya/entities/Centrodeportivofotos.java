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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author aranda
 */
@Entity
@Table(name = "centrodeportivofotos", catalog = "tucanchaya", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Centrodeportivofotos.findAll", query = "SELECT c FROM Centrodeportivofotos c"),
    @NamedQuery(name = "Centrodeportivofotos.findByCenfotId", query = "SELECT c FROM Centrodeportivofotos c WHERE c.cenfotId = :cenfotId"),
    @NamedQuery(name = "Centrodeportivofotos.findByCenfoNombre", query = "SELECT c FROM Centrodeportivofotos c WHERE c.cenfoNombre = :cenfoNombre")})
public class Centrodeportivofotos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cenfotId")
    private Long cenfotId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "cenfoNombre")
    private String cenfoNombre;
    @JoinColumn(name = "cenId", referencedColumnName = "cenId")
    @ManyToOne(optional = false)
    private Centrodeportivo cenId;

    public Centrodeportivofotos() {
    }

    public Centrodeportivofotos(Long cenfotId) {
        this.cenfotId = cenfotId;
    }

    public Centrodeportivofotos(Long cenfotId, String cenfoNombre) {
        this.cenfotId = cenfotId;
        this.cenfoNombre = cenfoNombre;
    }

    public Long getCenfotId() {
        return cenfotId;
    }

    public void setCenfotId(Long cenfotId) {
        this.cenfotId = cenfotId;
    }

    public String getCenfoNombre() {
        return cenfoNombre;
    }

    public void setCenfoNombre(String cenfoNombre) {
        this.cenfoNombre = cenfoNombre;
    }

    public Centrodeportivo getCenId() {
        return cenId;
    }

    public void setCenId(Centrodeportivo cenId) {
        this.cenId = cenId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cenfotId != null ? cenfotId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Centrodeportivofotos)) {
            return false;
        }
        Centrodeportivofotos other = (Centrodeportivofotos) object;
        if ((this.cenfotId == null && other.cenfotId != null) || (this.cenfotId != null && !this.cenfotId.equals(other.cenfotId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tucanchaya.entities.Centrodeportivofotos[ cenfotId=" + cenfotId + " ]";
    }
    
}
