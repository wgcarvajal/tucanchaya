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
 * @author aranda
 */
@Entity
@Table(name = "usuario", catalog = "tucanchaya", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u"),
    @NamedQuery(name = "Usuario.findByUsuId", query = "SELECT u FROM Usuario u WHERE u.usuId = :usuId"),
    @NamedQuery(name = "Usuario.findByUsuIdentificacion", query = "SELECT u FROM Usuario u WHERE u.usuIdentificacion = :usuIdentificacion"),
    @NamedQuery(name = "Usuario.findByUsuNombre", query = "SELECT u FROM Usuario u WHERE u.usuNombres = :usuNombres"),
    @NamedQuery(name = "Usuario.findByUserName", query = "SELECT u FROM Usuario u WHERE u.usuNombreUsuario = :usuNombreUsuario"),
    @NamedQuery(name = "Usuario.findByUsuContrasena", query = "SELECT u FROM Usuario u WHERE u.usuContrasena = :usuContrasena"),
    @NamedQuery(name = "Usuario.findByUsuEmail", query = "SELECT u FROM Usuario u WHERE u.usuEmail = :usuEmail"),
    @NamedQuery(name = "Usuario.findByUsuDireccion", query = "SELECT u FROM Usuario u WHERE u.usuDireccion = :usuDireccion"),
    @NamedQuery(name = "Usuario.findByUsuTelefono", query = "SELECT u FROM Usuario u WHERE u.usuTelefono = :usuTelefono"),
    @NamedQuery(name = "Usuario.findByUsuRol", query = "SELECT u FROM Usuario u JOIN Usuariogrupo ug WHERE ug.usuId.usuId= u.usuId And ug.gruId.gruId = :gruId"),
    @NamedQuery(name = "Usuario.findByUsuRolAndCenId", query = "SELECT u FROM Usuario u JOIN Usuariogrupo ug WHERE u.cenId.cenId= :cenId And  ug.usuId.usuId= u.usuId And ug.gruId.gruId = :gruId"),
    @NamedQuery(name = "Usuario.findByUsuRolAndName", query = "SELECT u FROM Usuario u JOIN Usuariogrupo ug WHERE ug.usuId.usuId = u.usuId And ug.gruId.gruId = :gruId and (CONCAT(CONCAT(LOWER(u.usuNombres),' '),LOWER(u.usuApellidos)) LIKE :search OR LOWER(u.usuNombreUsuario) LIKE :search OR LOWER(u.usuIdentificacion) LIKE :search)"),
    @NamedQuery(name = "Usuario.findByUsuRolAndCenIdAndName", query = "SELECT u FROM Usuario u JOIN Usuariogrupo ug WHERE u.cenId.cenId= :cenId And ug.usuId.usuId = u.usuId And ug.gruId.gruId = :gruId and (CONCAT(CONCAT(LOWER(u.usuNombres),' '),LOWER(u.usuApellidos)) LIKE :search OR LOWER(u.usuNombreUsuario) LIKE :search OR LOWER(u.usuIdentificacion) LIKE :search)"),
    @NamedQuery(name = "Usuario.findByUsuRolAndIdentification", query = "SELECT u FROM Usuario u JOIN Usuariogrupo ug WHERE ug.usuId.usuId = u.usuId And ug.gruId.gruId = :gruId and u.usuIdentificacion = :usuIdentificacion"),
    @NamedQuery(name = "Usuario.findByUsuRolAndUsuId", query = "SELECT u FROM Usuario u JOIN Usuariogrupo ug WHERE ug.usuId.usuId = u.usuId And ug.gruId.gruId = :gruId and u.usuId = :usuId")})
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "usuId")
    private Long usuId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "usuIdentificacion")
    private String usuIdentificacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "usuNombres")
    private String usuNombres;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "usuApellidos")
    private String usuApellidos;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 75)
    @Column(name = "usuNombreUsuario")
    private String usuNombreUsuario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "usuContrasena")
    private String usuContrasena;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "usuEmail")
    private String usuEmail;
    @Size(max = 200)
    @Column(name = "usuDireccion")
    private String usuDireccion;
    @Size(max = 20)
    @Column(name = "usuTelefono")
    private String usuTelefono;
    @Basic(optional = false)
    @NotNull
    @Column(name = "usuActivo")
    private boolean usuActivo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuId")
    private List<Usuariogrupo> usuariogrupoList;
    @JoinColumn(name = "cenId", referencedColumnName = "cenId")
    @ManyToOne
    private Centrodeportivo cenId;

    public Usuario() {
    }

    public Usuario(Long usuId) {
        this.usuId = usuId;
    }

    public Usuario(Long usuId, String usuIdentificacion, String usuNombres, String usuApellidos, String usuNombreUsuario, String usuContrasena, String usuEmail, boolean usuActivo) {
        this.usuId = usuId;
        this.usuIdentificacion = usuIdentificacion;
        this.usuNombres = usuNombres;
        this.usuApellidos = usuApellidos;
        this.usuNombreUsuario = usuNombreUsuario;
        this.usuContrasena = usuContrasena;
        this.usuEmail = usuEmail;
        this.usuActivo = usuActivo;
    }

    public Long getUsuId() {
        return usuId;
    }

    public void setUsuId(Long usuId) {
        this.usuId = usuId;
    }

    public String getUsuIdentificacion() {
        return usuIdentificacion;
    }

    public void setUsuIdentificacion(String usuIdentificacion) {
        this.usuIdentificacion = usuIdentificacion;
    }

    public String getUsuNombres() {
        return usuNombres;
    }

    public void setUsuNombres(String usuNombres) {
        this.usuNombres = usuNombres;
    }

    public String getUsuApellidos() {
        return usuApellidos;
    }

    public void setUsuApellidos(String usuApellidos) {
        this.usuApellidos = usuApellidos;
    }

    public String getUsuNombreUsuario() {
        return usuNombreUsuario;
    }

    public void setUsuNombreUsuario(String usuNombreUsuario) {
        this.usuNombreUsuario = usuNombreUsuario;
    }

    public String getUsuContrasena() {
        return usuContrasena;
    }

    public void setUsuContrasena(String usuContrasena) {
        this.usuContrasena = usuContrasena;
    }

    public String getUsuEmail() {
        return usuEmail;
    }

    public void setUsuEmail(String usuEmail) {
        this.usuEmail = usuEmail;
    }

    public String getUsuDireccion() {
        return usuDireccion;
    }

    public void setUsuDireccion(String usuDireccion) {
        this.usuDireccion = usuDireccion;
    }

    public String getUsuTelefono() {
        return usuTelefono;
    }

    public void setUsuTelefono(String usuTelefono) {
        this.usuTelefono = usuTelefono;
    }

    public boolean getUsuActivo() {
        return usuActivo;
    }

    public void setUsuActivo(boolean usuActivo) {
        this.usuActivo = usuActivo;
    }

    @XmlTransient
    public List<Usuariogrupo> getUsuariogrupoList() {
        return usuariogrupoList;
    }

    public void setUsuariogrupoList(List<Usuariogrupo> usuariogrupoList) {
        this.usuariogrupoList = usuariogrupoList;
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
        hash += (usuId != null ? usuId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.usuId == null && other.usuId != null) || (this.usuId != null && !this.usuId.equals(other.usuId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "test.Usuario[ usuId=" + usuId + " ]";
    }
}
