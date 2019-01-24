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
 * @author aranda
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
    
}
