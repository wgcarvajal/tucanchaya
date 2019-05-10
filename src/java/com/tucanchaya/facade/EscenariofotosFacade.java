/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tucanchaya.facade;

import com.tucanchaya.entities.Escenariofotos;
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
public class EscenariofotosFacade extends AbstractFacade<Escenariofotos> {

    @PersistenceContext(unitName = "TucanchayaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EscenariofotosFacade() {
        super(Escenariofotos.class);
    }
    
    public List<Escenariofotos> findByEscId(Long escId)
    {
        Query query = getEntityManager().createNamedQuery("Escenariofotos.findByEscId");
        query.setParameter("escId", escId);
        List<Escenariofotos> resultList = query.getResultList();
        return resultList;
    }
}
