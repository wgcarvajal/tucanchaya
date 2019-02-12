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
 * @author daniel
 */
@Entity
@Table(name = "producto")
@XmlRootElement
@NamedQueries({
@NamedQuery(name = "Producto.findAll", query = "SELECT p FROM Producto p"),
@NamedQuery(name = "Producto.findByProdId", query = "SELECT p FROM Producto p WHERE p.prodId = :prodId"),
@NamedQuery(name = "Producto.findByCatId", query = "SELECT p FROM Producto p WHERE p.catId.catId = :catId"),
@NamedQuery(name = "Producto.findByColumn", query = "SELECT p FROM Producto p WHERE LOWER(p.prodNombre) LIKE :prodNombre Order by p.prodNombre asc"),
@NamedQuery(name = "Producto.findByProdNombre", query = "SELECT p FROM Producto p WHERE p.prodNombre = :prodNombre")})
public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "prodId")
    private Long prodId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "prodNombre")
    private String prodNombre;
    @Size(max = 200)
    @Column(name = "prodImagen")
    private String prodImagen;
    @JoinColumn(name = "catId", referencedColumnName = "catId")
    @ManyToOne(optional = false)
    private Categoria catId;

    public Producto() {
    }

    public Producto(Long prodId) {
        this.prodId = prodId;
    }

    public Producto(Long prodId, String prodNombre) {
        this.prodId = prodId;
        this.prodNombre = prodNombre;
    }

    public Long getProdId() {
        return prodId;
    }

    public void setProdId(Long prodId) {
        this.prodId = prodId;
    }

    public String getProdNombre() {
        return prodNombre;
    }

    public void setProdNombre(String prodNombre) {
        this.prodNombre = prodNombre;
    }

    public String getProdImagen() {
        return prodImagen;
    }

    public void setProdImagen(String prodImagen) {
        this.prodImagen = prodImagen;
    }

    public Categoria getCatId() {
        return catId;
    }

    public void setCatId(Categoria catId) {
        this.catId = catId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (prodId != null ? prodId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Producto)) {
            return false;
        }
        Producto other = (Producto) object;
        if ((this.prodId == null && other.prodId != null) || (this.prodId != null && !this.prodId.equals(other.prodId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ejemplo.modelo.Producto[ prodId=" + prodId + " ]";
    }
}
