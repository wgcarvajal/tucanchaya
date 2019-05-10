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
import javax.persistence.Lob;
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
 * @author aranda
 */
@Entity
@Table(name = "centrodeportivo", catalog = "tucanchaya", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Centrodeportivo.findAll", query = "SELECT c FROM Centrodeportivo c"),
    @NamedQuery(name = "Centrodeportivo.findByCenId", query = "SELECT c FROM Centrodeportivo c WHERE c.cenId = :cenId"),
    @NamedQuery(name = "Centrodeportivo.findByCenNombre", query = "SELECT c FROM Centrodeportivo c WHERE c.cenNombre = :cenNombre"),
    @NamedQuery(name = "Centrodeportivo.findByCenDireccion", query = "SELECT c FROM Centrodeportivo c WHERE c.cenDireccion = :cenDireccion"),
    @NamedQuery(name = "Centrodeportivo.findByCityAndNameOrderByName", query = "SELECT c FROM Centrodeportivo c WHERE c.barId.ciuId.ciuId = :ciuId AND LOWER(c.cenNombre) LIKE :cenNombre Order by c.cenNombre asc"),
    @NamedQuery(name = "Centrodeportivo.findByCenUbicacion", query = "SELECT c FROM Centrodeportivo c WHERE c.cenUbicacion = :cenUbicacion"),
    @NamedQuery(name = "Centrodeportivo.findByCiudad", query = "SELECT c FROM Centrodeportivo c WHERE c.barId.ciuId.ciuId = :ciuId"),
    @NamedQuery(name = "Centrodeportivo.findByColor", query = "SELECT c FROM Centrodeportivo c WHERE c.colorId.colorId = :colorId"),
    @NamedQuery(name = "Centrodeportivo.findByBarrio", query = "SELECT c FROM Centrodeportivo c WHERE c.barId.barId = :barId"),
    @NamedQuery(name = "Centrodeportivo.findByCityOrderByColumnName", query = "SELECT c FROM Centrodeportivo c WHERE c.barId.ciuId.ciuId = :ciuId Order by c.cenNombre asc"),
    @NamedQuery(name = "Centrodeportivo.findByCityAndName", query = "SELECT c FROM Centrodeportivo c WHERE c.barId.ciuId.ciuId = :ciuId AND c.cenNombre = :cenNombre"),
    @NamedQuery(name = "Centrodeportivo.findByCityAndAddress", query = "SELECT c FROM Centrodeportivo c WHERE c.barId.ciuId.ciuId = :ciuId AND c.cenDireccion = :cenDireccion"),
    @NamedQuery(name = "Centrodeportivo.findByCenAlto", query = "SELECT c FROM Centrodeportivo c WHERE c.cenAlto = :cenAlto")})
public class Centrodeportivo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cenId")
    private Long cenId;
    @Size(max = 30)
    @Column(name = "cenNit")
    private String cenNit;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "cenNombre")
    private String cenNombre;
    @Size(max = 200)
    @Column(name = "cenDireccion")
    private String cenDireccion;
    @Size(max = 200)
    @Column(name = "cenUbicacion")
    private String cenUbicacion;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "cenDescripcion")
    private String cenDescripcion;
    @Column(name = "cenAlto")
    private Integer cenAlto;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cenId")
    private List<Escenario> escenarioList;
    @JoinColumn(name = "barId", referencedColumnName = "barId")
    @ManyToOne(optional = false)
    private Barrio barId;
    @JoinColumn(name = "colorId", referencedColumnName = "colorId")
    @ManyToOne
    private Color colorId;
    @OneToMany(mappedBy = "cenId")
    private List<Usuario> usuarioList;

    public Centrodeportivo() {
    }

    public Centrodeportivo(Long cenId) {
        this.cenId = cenId;
    }

    public Centrodeportivo(Long cenId, String cenNombre) {
        this.cenId = cenId;
        this.cenNombre = cenNombre;
    }

    public Long getCenId() {
        return cenId;
    }

    public void setCenId(Long cenId) {
        this.cenId = cenId;
    }

    public String getCenNit() {
        return cenNit;
    }

    public void setCenNit(String cenNit) {
        this.cenNit = cenNit;
    }

    public String getCenNombre() {
        return cenNombre;
    }

    public void setCenNombre(String cenNombre) {
        this.cenNombre = cenNombre;
    }

    public String getCenDireccion() {
        return cenDireccion;
    }

    public void setCenDireccion(String cenDireccion) {
        this.cenDireccion = cenDireccion;
    }

    public String getCenUbicacion() {
        return cenUbicacion;
    }

    public void setCenUbicacion(String cenUbicacion) {
        this.cenUbicacion = cenUbicacion;
    }

    public String getCenDescripcion() {
        return cenDescripcion;
    }

    public void setCenDescripcion(String cenDescripcion) {
        this.cenDescripcion = cenDescripcion;
    }

    public Integer getCenAlto() {
        return cenAlto;
    }

    public void setCenAlto(Integer cenAlto) {
        this.cenAlto = cenAlto;
    }

    @XmlTransient
    public List<Escenario> getEscenarioList() {
        return escenarioList;
    }

    public void setEscenarioList(List<Escenario> escenarioList) {
        this.escenarioList = escenarioList;
    }

    public Barrio getBarId() {
        return barId;
    }

    public void setBarId(Barrio barId) {
        this.barId = barId;
    }

    public Color getColorId() {
        return colorId;
    }

    public void setColorId(Color colorId) {
        this.colorId = colorId;
    }
    
    @XmlTransient
    public List<Usuario> getUsuarioList() {
        return usuarioList;
    }

    public void setUsuarioList(List<Usuario> usuarioList) {
        this.usuarioList = usuarioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cenId != null ? cenId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Centrodeportivo)) {
            return false;
        }
        Centrodeportivo other = (Centrodeportivo) object;
        if ((this.cenId == null && other.cenId != null) || (this.cenId != null && !this.cenId.equals(other.cenId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Centrodeportivo[ cenId=" + cenId + " ]";
    }
}
