/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tucanchaya.facade;

import com.tucanchaya.entities.Deporte;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Wilson Carvajal
 */
@Stateless
public class DeporteFacade extends AbstractFacade<Deporte> {

    @PersistenceContext(unitName = "TucanchayaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DeporteFacade() {
        super(Deporte.class);
    }
    
    public List<Deporte> findByColumn(String search)
    {
        Query query = getEntityManager().createNamedQuery("Deporte.findByColumn");
        query.setParameter("depNombre", "%" + search + "%");
        List<Deporte> resultList = query.getResultList();
        return resultList;
    }
    
    public boolean existSport(String name)
    {
        Query query = getEntityManager().createNamedQuery("Deporte.findByDepNombre");
        query.setParameter("depNombre",  name);
        List<Deporte> resultList = query.getResultList();
        return resultList!=null && resultList.size()>0;
    }
    
}
