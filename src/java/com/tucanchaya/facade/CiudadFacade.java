/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tucanchaya.facade;

import com.tucanchaya.entities.Ciudad;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author aranda
 */
@Stateless
public class CiudadFacade extends AbstractFacade<Ciudad> {

    @PersistenceContext(unitName = "TucanchayaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CiudadFacade() {
        super(Ciudad.class);
    }
    
    public List<Ciudad> findByColumn(String search)
    {
        Query query = getEntityManager().createNamedQuery("Ciudad.findByColumn");
        query.setParameter("ciunombre", "%" + search + "%");
        List<Ciudad> resultList = query.getResultList();
        return resultList;
    }
    
    public Ciudad findDefaultCity()
    {
        Query query = getEntityManager().createNamedQuery("Ciudad.findDefaultCity");
        List<Ciudad> resultList = query.getResultList();
        if(resultList != null && resultList.size() > 0)
            return resultList.get(0);
        return null;
    }
    
    public boolean existCity(String name)
    {
        Query query = getEntityManager().createNamedQuery("Ciudad.findByCiunombre");
        query.setParameter("ciunombre",  name);
        List<Ciudad> resultList = query.getResultList();
        return resultList!=null && resultList.size()>0;
    }
    
    public boolean isCityUsed(Long id)
    {
        Query query = getEntityManager().createNamedQuery("Centrodeportivo.findByCiudad");
        query.setParameter("ciuId",  id);
        List<Ciudad> resultList = query.getResultList();
        return resultList!=null && resultList.size()>0;
    }
}
