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
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "centrodeportivodeporte", catalog = "tucanchaya", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Centrodeportivodeporte.findAll", query = "SELECT c FROM Centrodeportivodeporte c"),
    @NamedQuery(name = "Centrodeportivodeporte.findById", query = "SELECT c FROM Centrodeportivodeporte c WHERE c.id = :id")})
public class Centrodeportivodeporte implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @JoinColumn(name = "cenId", referencedColumnName = "cenId")
    @ManyToOne(optional = false)
    private Centrodeportivo cenId;
    @JoinColumn(name = "depId", referencedColumnName = "depId")
    @ManyToOne(optional = false)
    private Deporte depId;

    public Centrodeportivodeporte() {
    }

    public Centrodeportivodeporte(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Centrodeportivo getCenId() {
        return cenId;
    }

    public void setCenId(Centrodeportivo cenId) {
        this.cenId = cenId;
    }

    public Deporte getDepId() {
        return depId;
    }

    public void setDepId(Deporte depId) {
        this.depId = depId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Centrodeportivodeporte)) {
            return false;
        }
        Centrodeportivodeporte other = (Centrodeportivodeporte) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tucanchaya.entities.Centrodeportivodeporte[ id=" + id + " ]";
    }
    
}
