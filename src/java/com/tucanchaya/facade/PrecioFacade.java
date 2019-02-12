/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tucanchaya.facade;

import com.tucanchaya.entities.Precio;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author daniel
 */
@Stateless
public class PrecioFacade extends AbstractFacade<Precio> {

    @PersistenceContext(unitName = "TucanchayaPU")
    private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    public PrecioFacade()
    {
        super(Precio.class);
    }
    
   
    
    public List<Precio> findByPrecId ( Long precId )
    {
        Query query = getEntityManager().createNamedQuery("Precio.findByPrecId");
        query.setParameter("precId", precId);
        List<Precio> resuList = query.getResultList();
        return resuList;
    }
    
    public boolean existPrice( Long precId )
    {
        Query query = getEntityManager().createNamedQuery("Precio.findByPrecId");
        query.setParameter("precId", precId);
        List<Precio> resuList = query.getResultList();
        return resuList!=null && resuList.size()>0;
    }
    
    public boolean isPriceUsed (Long id)
    {
        Query query = getEntityManager().createNamedQuery("Precio.findByPrecioProductoId");
        query.setParameter("prodId", id);
        List<Precio> resuList = query.getResultList();
        return resuList!=null && resuList.size()>0;
    }
    
    public List<Precio> findPrecioProductoId (Long prodId)
    {
        Query query = getEntityManager().createNamedQuery("Precio.findByPrecioProductoId");
        query.setParameter("prodId", prodId);
        List<Precio> resuList = query.getResultList();
        return resuList;
    }
    
    public Precio getPriceProduct (Long id)
    {
        Query query = getEntityManager().createNamedQuery("Precio.findByProdId");
        query.setParameter("prodId", id);
        List<Precio> resuList = query.getResultList();
        return resuList.get(0);
    }
}
