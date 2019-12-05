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
    @NamedQuery(name = "Centrodeportivofotos.findByCenFotId", query = "SELECT c FROM Centrodeportivofotos c WHERE c.cenFotId = :cenFotId"),
    @NamedQuery(name = "Centrodeportivofotos.findByCenFoNombre", query = "SELECT c FROM Centrodeportivofotos c WHERE c.cenFoNombre = :cenFoNombre"),
    @NamedQuery(name = "Centrodeportivofotos.findByCenIdDefaultPhoto", query = "SELECT c FROM Centrodeportivofotos c WHERE c.cenId.cenId = :cenId AND c.cenFotPrincipal = TRUE"),
    @NamedQuery(name = "Centrodeportivofotos.findByCenId", query = "SELECT c FROM Centrodeportivofotos c WHERE c.cenId.cenId = :cenId"),
    @NamedQuery(name = "Centrodeportivofotos.findByCenFotPrincipal", query = "SELECT c FROM Centrodeportivofotos c WHERE c.cenFotPrincipal = :cenFotPrincipal")})
public class Centrodeportivofotos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cenFotId")
    private Long cenFotId;
    @Size(max = 200)
    @Column(name = "cenFoNombre")
    private String cenFoNombre;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cenFotPrincipal")
    private boolean cenFotPrincipal;
    @JoinColumn(name = "cenId", referencedColumnName = "cenId")
    @ManyToOne(optional = false)
    private Centrodeportivo cenId;

    public Centrodeportivofotos() {
    }

    public Centrodeportivofotos(Long cenFotId) {
        this.cenFotId = cenFotId;
    }

    public Centrodeportivofotos(Long cenFotId, boolean cenFotPrincipal) {
        this.cenFotId = cenFotId;
        this.cenFotPrincipal = cenFotPrincipal;
    }

    public Long getCenFotId() {
        return cenFotId;
    }

    public void setCenFotId(Long cenFotId) {
        this.cenFotId = cenFotId;
    }

    public String getCenFoNombre() {
        return cenFoNombre;
    }

    public void setCenFoNombre(String cenFoNombre) {
        this.cenFoNombre = cenFoNombre;
    }

    public boolean getCenFotPrincipal() {
        return cenFotPrincipal;
    }

    public void setCenFotPrincipal(boolean cenFotPrincipal) {
        this.cenFotPrincipal = cenFotPrincipal;
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
        hash += (cenFotId != null ? cenFotId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Centrodeportivofotos)) {
            return false;
        }
        Centrodeportivofotos other = (Centrodeportivofotos) object;
        if ((this.cenFotId == null && other.cenFotId != null) || (this.cenFotId != null && !this.cenFotId.equals(other.cenFotId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tucanchaya.entities.Centrodeportivofotos[ cenFotId=" + cenFotId + " ]";
    }
    
}
