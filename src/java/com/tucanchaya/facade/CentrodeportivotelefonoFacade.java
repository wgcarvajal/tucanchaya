/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tucanchaya.facade;

import com.tucanchaya.entities.Centrodeportivotelefono;
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
public class CentrodeportivotelefonoFacade extends AbstractFacade<Centrodeportivotelefono> {

    @PersistenceContext(unitName = "TucanchayaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CentrodeportivotelefonoFacade() {
        super(Centrodeportivotelefono.class);
    }
    
    public List<Centrodeportivotelefono> findByCenIdAndTipo(Long cenId,String cenTelTipo)
    {
        Query query = getEntityManager().createNamedQuery("Centrodeportivotelefono.findByCenIdAndTipo");
        query.setParameter("cenId", cenId);
        query.setParameter("cenTelTipo", cenTelTipo);
        List<Centrodeportivotelefono> resultList = query.getResultList();
        return resultList;
    }
}
