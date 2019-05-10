/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tucanchaya.facade;

import com.tucanchaya.entities.Usuario;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 *
 * @author Wilson Carvajal
 */
@Stateless
public class UsuarioFacade extends AbstractFacade<Usuario> {

    @PersistenceContext(unitName = "TucanchayaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioFacade() {
        super(Usuario.class);
    }
    
    public List<Usuario> findByUsuRol(String gruId)
    {
        Query query = getEntityManager().createNamedQuery("Usuario.findByUsuRol");
        query.setParameter("gruId",  gruId);
        List<Usuario> resultList = query.getResultList();
        return resultList;
    }
    
    public List<Usuario> findByUsuRolAndCenId(String gruId,Long cenId)
    {
        Query query = getEntityManager().createNamedQuery("Usuario.findByUsuRolAndCenId");
        query.setParameter("gruId",  gruId);
        query.setParameter("cenId",  cenId);
        List<Usuario> resultList = query.getResultList();
        return resultList;
    }
    
    public List<Usuario> findByUsuRolAndName(String gruId,String search)
    {
        Query query = getEntityManager().createNamedQuery("Usuario.findByUsuRolAndName");
        query.setParameter("gruId",  gruId);
        query.setParameter("search", "%" + search + "%");
        List<Usuario> resultList = query.getResultList();
        return resultList;
    }
    
    public List<Usuario> findByUsuRolAndCenIdAndName(String gruId,String search,Long cenId)
    {
        Query query = getEntityManager().createNamedQuery("Usuario.findByUsuRolAndCenIdAndName");
        query.setParameter("gruId",  gruId);
        query.setParameter("cenId",  cenId);
        query.setParameter("search", "%" + search + "%");
        List<Usuario> resultList = query.getResultList();
        return resultList;
    }
    
    public boolean isRegisterUsuRolAndIdentification(String gruId,String usuIdentificacion)
    {
        Query query = getEntityManager().createNamedQuery("Usuario.findByUsuRolAndIdentification");
        query.setParameter("gruId",  gruId);
        query.setParameter("usuIdentificacion", usuIdentificacion);
        List<Usuario> resultList = query.getResultList();
        return !resultList.isEmpty();
    }
    
    public boolean isRegisterUserName(String usuNombreUsuario)
    {
        Query query = getEntityManager().createNamedQuery("Usuario.findByUserName");
        query.setParameter("usuNombreUsuario",  usuNombreUsuario);
        List<Usuario> resultList = query.getResultList();
        return !resultList.isEmpty();
    }
    
    public  List<Usuario> findByUsuRolAndUsuId(String gruId,Long usuId)
    {
        Query query = getEntityManager().createNamedQuery("Usuario.findByUsuRolAndUsuId");
        query.setParameter("usuId",  usuId);
        query.setParameter("gruId",  gruId);
        List<Usuario> users = query.getResultList();
        return users;
    }
}
