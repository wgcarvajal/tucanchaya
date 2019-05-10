/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tucanchaya.facade;

import com.tucanchaya.entities.Deporte;
import com.tucanchaya.entities.Escenario;
import com.tucanchaya.entities.Escenariodeporte;
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
public class EscenariodeporteFacade extends AbstractFacade<Escenariodeporte> {

    @PersistenceContext(unitName = "TucanchayaPU")
    private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    public EscenariodeporteFacade()
    {
        super(Escenariodeporte.class);
    }
    
    public List<Escenario> findEscByDepId(Long depId)
    {
        Query query = getEntityManager().createNamedQuery("Escenariodeporte.findEscByDepId");
        query.setParameter("depId", depId);
        List<Escenario> resultList = query.getResultList();
        return resultList;
    }
    
    public List<Deporte> findDepByEscId(Long escId)
    {
        Query query = getEntityManager().createNamedQuery("Escenariodeporte.findDepByEscId");
        query.setParameter("escId", escId);
        List<Deporte> resultList = query.getResultList();
        return resultList;
    }
}
