/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tucanchaya.facade;

import com.tucanchaya.entities.Barrio;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Wilson carvajal
 */
@Stateless
public class BarrioFacade extends AbstractFacade<Barrio> {

    @PersistenceContext(unitName = "TucanchayaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BarrioFacade() {
        super(Barrio.class);
    }
    
    public List<Barrio> findByCityOrderByName(Long ciuId)
    {
        Query query = getEntityManager().createNamedQuery("Barrio.findByCityOrderByColumnName");
        query.setParameter("ciuId", ciuId);
        List<Barrio> resultList = query.getResultList();
        return resultList;
    }
    
    public List<Barrio> findByCityAndNameOrderByName(Long ciuId,String barNombre)
    {
        Query query = getEntityManager().createNamedQuery("Barrio.findByCityAndNameOrderByName");
        query.setParameter("ciuId", ciuId);
        query.setParameter("barNombre", "%" + barNombre + "%");
        List<Barrio> resultList = query.getResultList();
        return resultList;
    }
    
    public boolean existNeighborhoodName(Long ciuId,String barNombre)
    {
        Query query = getEntityManager().createNamedQuery("Barrio.findByCityAndName");
        query.setParameter("ciuId",  ciuId);
        query.setParameter("barNombre",  barNombre);
        List<Barrio> resultList = query.getResultList();
        return resultList!=null && resultList.size()>0;
    }
    
    public boolean isNeighborhoodUsed(Long barId)
    {
        Query query = getEntityManager().createNamedQuery("Centrodeportivo.findByBarrio");
        query.setParameter("barId",  barId);
        List<Barrio> resultList = query.getResultList();
        return resultList!=null && resultList.size()>0;
    }
}
