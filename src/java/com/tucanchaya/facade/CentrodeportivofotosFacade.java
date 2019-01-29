/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tucanchaya.facade;

import com.tucanchaya.entities.Centrodeportivofotos;
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
public class CentrodeportivofotosFacade extends AbstractFacade<Centrodeportivofotos> {

    @PersistenceContext(unitName = "TucanchayaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CentrodeportivofotosFacade() {
        super(Centrodeportivofotos.class);
    }
    
    public List<Centrodeportivofotos> findByCenId(Long cenId)
    {
        Query query = getEntityManager().createNamedQuery("Centrodeportivofotos.findByCenId");
        query.setParameter("cenId", cenId);
        List<Centrodeportivofotos> resultList = query.getResultList();
        return resultList;
    }
    
    public Centrodeportivofotos findByCenIdDefaultPhoto(Long cenId)
    {
        Query query = getEntityManager().createNamedQuery("Centrodeportivofotos.findByCenIdDefaultPhoto");
        query.setParameter("cenId", cenId);
        List<Centrodeportivofotos> resultList = query.getResultList();
        if(resultList!=null && resultList.size()>0)
        {
            return resultList.get(0);
        }
        return null;
    }
}
