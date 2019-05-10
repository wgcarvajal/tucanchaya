/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tucanchaya.facade;

import com.tucanchaya.entities.Escenario;
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
public class EscenarioFacade extends AbstractFacade<Escenario> {

    @PersistenceContext(unitName = "TucanchayaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EscenarioFacade() {
        super(Escenario.class);
    }
    
    public List<Escenario> findbySportCenterId(Long cenId)
    {
        Query query = getEntityManager().createNamedQuery("Escenario.findbySportCenterId");
        query.setParameter("cenId", cenId);
        List<Escenario> resultList = query.getResultList();
        return resultList;
    }
    
    
    public boolean existStageNameInSporcenter(Long cenId,String escNombre)
    {
        Query query = getEntityManager().createNamedQuery("Escenario.findbyStageNameInSporcenter");
        query.setParameter("cenId", cenId);
        query.setParameter("escNombre", escNombre);
        List<Escenario> resultList = query.getResultList();
        return resultList!=null && resultList.size()>0;
    }
}
