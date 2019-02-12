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
@Table(name = "centrodeportivotelefono", catalog = "tucanchaya", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Centrodeportivotelefono.findAll", query = "SELECT c FROM Centrodeportivotelefono c"),
    @NamedQuery(name = "Centrodeportivotelefono.findByCenTelId", query = "SELECT c FROM Centrodeportivotelefono c WHERE c.cenTelId = :cenTelId"),
    @NamedQuery(name = "Centrodeportivotelefono.findByCenTelNumero", query = "SELECT c FROM Centrodeportivotelefono c WHERE c.cenTelNumero = :cenTelNumero"),
    @NamedQuery(name = "Centrodeportivotelefono.findByCenIdAndTipo", query = "SELECT c FROM Centrodeportivotelefono c WHERE c.cenId.cenId = :cenId AND c.cenTelTipo = :cenTelTipo"),
    @NamedQuery(name = "Centrodeportivotelefono.findByCentTelTipo", query = "SELECT c FROM Centrodeportivotelefono c WHERE c.cenTelTipo = :cenTelTipo")})
public class Centrodeportivotelefono implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cenTelId")
    private Integer cenTelId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "cenTelNumero")
    private String cenTelNumero;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "cenTelTipo")
    private String cenTelTipo;
    @JoinColumn(name = "cenId", referencedColumnName = "cenId")
    @ManyToOne(optional = false)
    private Centrodeportivo cenId;

    public Centrodeportivotelefono() {
    }

    public Centrodeportivotelefono(Integer cenTelId) {
        this.cenTelId = cenTelId;
    }

    public Centrodeportivotelefono(Integer cenTelId, String cenTelNumero, String cenTelTipo) {
        this.cenTelId = cenTelId;
        this.cenTelNumero = cenTelNumero;
        this.cenTelTipo = cenTelTipo;
    }

    public Integer getCenTelId() {
        return cenTelId;
    }

    public void setCenTelId(Integer cenTelId) {
        this.cenTelId = cenTelId;
    }

    public String getCenTelNumero() {
        return cenTelNumero;
    }

    public void setCenTelNumero(String cenTelNumero) {
        this.cenTelNumero = cenTelNumero;
    }

    public String getCenTelTipo() {
        return cenTelTipo;
    }

    public void setCenTelTipo(String cenTelTipo) {
        this.cenTelTipo = cenTelTipo;
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
        hash += (cenTelId != null ? cenTelId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Centrodeportivotelefono)) {
            return false;
        }
        Centrodeportivotelefono other = (Centrodeportivotelefono) object;
        if ((this.cenTelId == null && other.cenTelId != null) || (this.cenTelId != null && !this.cenTelId.equals(other.cenTelId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Centrodeportivotelefono[ cenTelId=" + cenTelId + " ]";
    }
    
}
