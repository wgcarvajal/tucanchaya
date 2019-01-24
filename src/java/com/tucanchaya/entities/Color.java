/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tucanchaya.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
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
@Table(name = "color", catalog = "tucanchaya", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Color.findAll", query = "SELECT c FROM Color c"),
    @NamedQuery(name = "Color.findByColorId", query = "SELECT c FROM Color c WHERE c.colorId = :colorId"),
    @NamedQuery(name = "Color.findByColorName", query = "SELECT c FROM Color c WHERE LOWER(c.colorNombre) LIKE :colorNombre Order by c.colorNombre asc"),
    @NamedQuery(name = "Color.findByColorHexadecimal", query = "SELECT c FROM Color c WHERE c.colorHexadecimal = :colorHexadecimal"),
    @NamedQuery(name = "Color.findByColorNombre", query = "SELECT c FROM Color c WHERE c.colorNombre = :colorNombre")})
public class Color implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "colorId")
    private Integer colorId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "colorHexadecimal")
    private String colorHexadecimal;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "colorNombre")
    private String colorNombre;
    @OneToMany(mappedBy = "colorId")
    private List<Centrodeportivo> centrodeportivoList;

    public Color() {
    }

    public Color(Integer colorId) {
        this.colorId = colorId;
    }

    public Color(Integer colorId, String colorHexadecimal, String colorNombre) {
        this.colorId = colorId;
        this.colorHexadecimal = colorHexadecimal;
        this.colorNombre = colorNombre;
    }

    public Integer getColorId() {
        return colorId;
    }

    public void setColorId(Integer colorId) {
        this.colorId = colorId;
    }

    public String getColorHexadecimal() {
        return colorHexadecimal;
    }

    public void setColorHexadecimal(String colorHexadecimal) {
        this.colorHexadecimal = colorHexadecimal;
    }

    public String getColorNombre() {
        return colorNombre;
    }

    public void setColorNombre(String colorNombre) {
        this.colorNombre = colorNombre;
    }

    @XmlTransient
    public List<Centrodeportivo> getCentrodeportivoList() {
        return centrodeportivoList;
    }

    public void setCentrodeportivoList(List<Centrodeportivo> centrodeportivoList) {
        this.centrodeportivoList = centrodeportivoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (colorId != null ? colorId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Color)) {
            return false;
        }
        Color other = (Color) object;
        if ((this.colorId == null && other.colorId != null) || (this.colorId != null && !this.colorId.equals(other.colorId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tucanchaya.entities.Color[ colorId=" + colorId + " ]";
    }
    
}
