/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tucanchaya.facade;

import com.tucanchaya.entities.Centrodeportivo;
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
public class CentrodeportivoFacade extends AbstractFacade<Centrodeportivo> {

    @PersistenceContext(unitName = "TucanchayaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CentrodeportivoFacade() {
        super(Centrodeportivo.class);
    }
    
    public List<Centrodeportivo> findByCityOrderByName(Long ciuId)
    {
        Query query = getEntityManager().createNamedQuery("Centrodeportivo.findByCityOrderByColumnName");
        query.setParameter("ciuId", ciuId);
        List<Centrodeportivo> resultList = query.getResultList();
        return resultList;
    }
    
    public Centrodeportivo findByCenId(Long cenId){
        Query query = getEntityManager().createNamedQuery("Centrodeportivo.findByCenId");
        query.setParameter("cenId", cenId);
        List<Centrodeportivo> resultList = query.getResultList();
        if(resultList!=null && resultList.size()>0)
        {
            return resultList.get(0);
        }
        return null;
    }
    
    public List<Centrodeportivo> findByCityAndNameOrderByName(Long ciuId,String cenNombre)
    {
        Query query = getEntityManager().createNamedQuery("Centrodeportivo.findByCityAndNameOrderByName");
        query.setParameter("ciuId", ciuId);
        query.setParameter("cenNombre", "%" + cenNombre + "%");
        List<Centrodeportivo> resultList = query.getResultList();
        return resultList;
    }
    
    public boolean existSportCenterName(Long ciuId,String cenNombre)
    {
        Query query = getEntityManager().createNamedQuery("Centrodeportivo.findByCityAndName");
        query.setParameter("ciuId",  ciuId);
        query.setParameter("cenNombre",  cenNombre);
        List<Centrodeportivo> resultList = query.getResultList();
        return resultList!=null && resultList.size()>0;
    }
    
    public boolean existAddress(Long ciuId,String cenDireccion )
    {
        Query query = getEntityManager().createNamedQuery("Centrodeportivo.findByCityAndAddress");
        query.setParameter("ciuId",  ciuId);
        query.setParameter("cenDireccion",  cenDireccion);
        List<Centrodeportivo> resultList = query.getResultList();
        return resultList!=null && resultList.size()>0;
    }
    
}
