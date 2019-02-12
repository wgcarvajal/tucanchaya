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
 * @author daniel
 */
@Entity
@Table(name = "precio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Precio.findAll", query = "SELECT p FROM Precio p"),
    @NamedQuery(name = "Precio.findByPrecId", query = "SELECT p FROM Precio p WHERE p.precId = :precId"),
    @NamedQuery(name = "Precio.findByPrecValor", query = "SELECT p FROM Precio p WHERE p.precValor = :precValor"),
    @NamedQuery(name = "Precio.findByPrecioFechaInicial", query = "SELECT p FROM Precio p WHERE p.precioFechaInicial = :precioFechaInicial"),
    @NamedQuery(name = "Precio.findByProdId", query = "SELECT p FROM Precio p WHERE p.prodId.prodId = :prodId"),
    @NamedQuery(name = "Precio.findByPrecioFechaFinal", query = "SELECT p FROM Precio p WHERE p.precioFechaFinal = :precioFechaFinal")})
public class Precio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "precId")
    private Long precId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "precValor")
    private long precValor;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "precioFechaInicial")
    private String precioFechaInicial;
    @Size(max = 20)
    @Column(name = "precioFechaFinal")
    private String precioFechaFinal;
    @JoinColumn(name = "prodId", referencedColumnName = "prodId")
    @ManyToOne(optional = false)
    private Producto prodId;

    public Precio() {
    }

    public Precio(Long precId) {
        this.precId = precId;
    }

    public Precio(Long precId, long precValor, String precioFechaInicial) {
        this.precId = precId;
        this.precValor = precValor;
        this.precioFechaInicial = precioFechaInicial;
    }

    public Long getPrecId() {
        return precId;
    }

    public void setPrecId(Long precId) {
        this.precId = precId;
    }

    public long getPrecValor() {
        return precValor;
    }

    public void setPrecValor(long precValor) {
        this.precValor = precValor;
    }

    public String getPrecioFechaInicial() {
        return precioFechaInicial;
    }

    public void setPrecioFechaInicial(String precioFechaInicial) {
        this.precioFechaInicial = precioFechaInicial;
    }

    public String getPrecioFechaFinal() {
        return precioFechaFinal;
    }

    public void setPrecioFechaFinal(String precioFechaFinal) {
        this.precioFechaFinal = precioFechaFinal;
    }

    public Producto getProdId() {
        return prodId;
    }

    public void setProdId(Producto prodId) {
        this.prodId = prodId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (precId != null ? precId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Precio)) {
            return false;
        }
        Precio other = (Precio) object;
        if ((this.precId == null && other.precId != null) || (this.precId != null && !this.precId.equals(other.precId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ejemplo.modelo.Precio[ precId=" + precId + " ]";
    }
    
}
