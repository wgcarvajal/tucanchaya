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
 * @author aranda
 */
@Entity
@Table(name = "ciudad", catalog = "tucanchaya", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ciudad.findAll", query = "SELECT c FROM Ciudad c"),
    @NamedQuery(name = "Ciudad.findByCiuId", query = "SELECT c FROM Ciudad c WHERE c.ciuId = :ciuId"),
    @NamedQuery(name = "Ciudad.findByColumn", query = "SELECT c FROM Ciudad c WHERE LOWER(c.ciunombre) LIKE :ciunombre Order by c.ciunombre asc"),
    @NamedQuery(name = "Ciudad.findDefaultCity", query = "SELECT c FROM Ciudad c WHERE c.ciuPorDefecto = true"),
    @NamedQuery(name = "Ciudad.findByCiunombre", query = "SELECT c FROM Ciudad c WHERE c.ciunombre = :ciunombre")})
public class Ciudad implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ciuId")
    private List<Centrodeportivo> centrodeportivoList;

    @Basic(optional = false)
    @NotNull
    @Column(name = "ciuPorDefecto")
    private boolean ciuPorDefecto;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ciuId")
    private Long ciuId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "ciunombre")
    private String ciunombre;

    public Ciudad() {
    }

    public Ciudad(Long ciuId) {
        this.ciuId = ciuId;
    }

    public Ciudad(Long ciuId, String ciunombre) {
        this.ciuId = ciuId;
        this.ciunombre = ciunombre;
    }

    public Long getCiuId() {
        return ciuId;
    }

    public void setCiuId(Long ciuId) {
        this.ciuId = ciuId;
    }

    public String getCiunombre() {
        return ciunombre;
    }

    public void setCiunombre(String ciunombre) {
        this.ciunombre = ciunombre;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ciuId != null ? ciuId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ciudad)) {
            return false;
        }
        Ciudad other = (Ciudad) object;
        if ((this.ciuId == null && other.ciuId != null) || (this.ciuId != null && !this.ciuId.equals(other.ciuId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tucanchaya.entities.Ciudad[ ciuId=" + ciuId + " ]";
    }

    public boolean getCiuPorDefecto() {
        return ciuPorDefecto;
    }

    public void setCiuPorDefecto(boolean ciuPorDefecto) {
        this.ciuPorDefecto = ciuPorDefecto;
    }

    @XmlTransient
    public List<Centrodeportivo> getCentrodeportivoList() {
        return centrodeportivoList;
    }

    public void setCentrodeportivoList(List<Centrodeportivo> centrodeportivoList) {
        this.centrodeportivoList = centrodeportivoList;
    }
    
}
