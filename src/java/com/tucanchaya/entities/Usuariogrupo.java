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
@Table(name = "usuariogrupo", catalog = "tucanchaya", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuariogrupo.findAll", query = "SELECT u FROM Usuariogrupo u"),
    @NamedQuery(name = "Usuariogrupo.findByUsuNombreUsuario", query = "SELECT u FROM Usuariogrupo u WHERE u.usuNombreUsuario = :usuNombreUsuario")})
public class Usuariogrupo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 75)
    @Column(name = "usuNombreUsuario")
    private String usuNombreUsuario;
    @JoinColumn(name = "cenId", referencedColumnName = "cenId")
    @ManyToOne
    private Centrodeportivo cenId;
    @JoinColumn(name = "gruId", referencedColumnName = "gruId")
    @ManyToOne(optional = false)
    private Grupo gruId;
    @JoinColumn(name = "usuId", referencedColumnName = "usuId")
    @ManyToOne(optional = false)
    private Usuario usuId;

    public Usuariogrupo() {
    }

    public Usuariogrupo(String usuNombreUsuario) {
        this.usuNombreUsuario = usuNombreUsuario;
    }

    public String getUsuNombreUsuario() {
        return usuNombreUsuario;
    }

    public void setUsuNombreUsuario(String usuNombreUsuario) {
        this.usuNombreUsuario = usuNombreUsuario;
    }

    public Centrodeportivo getCenId() {
        return cenId;
    }

    public void setCenId(Centrodeportivo cenId) {
        this.cenId = cenId;
    }

    public Grupo getGruId() {
        return gruId;
    }

    public void setGruId(Grupo gruId) {
        this.gruId = gruId;
    }

    public Usuario getUsuId() {
        return usuId;
    }

    public void setUsuId(Usuario usuId) {
        this.usuId = usuId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usuNombreUsuario != null ? usuNombreUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuariogrupo)) {
            return false;
        }
        Usuariogrupo other = (Usuariogrupo) object;
        if ((this.usuNombreUsuario == null && other.usuNombreUsuario != null) || (this.usuNombreUsuario != null && !this.usuNombreUsuario.equals(other.usuNombreUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tucanchaya.entities.Usuariogrupo[ usuNombreUsuario=" + usuNombreUsuario + " ]";
    }
    
}
