/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tucanchaya.facade;


import com.tucanchaya.entities.Producto;
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
public class ProductoFacade extends AbstractFacade<Producto>{

    @PersistenceContext(unitName = "TucanchayaPU")
    private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public ProductoFacade()
    {
        super(Producto.class);
    }
    
    public List<Producto> findByProdId ( Long prodId )
    {
        Query query = getEntityManager().createNamedQuery("Producto.findByProdId");
        query.setParameter("prodId", prodId);
        List<Producto> resuList = query.getResultList();
        return resuList;
    }
    
    public List<Producto> findByProdNombre ( String prodNombre )
    {
        Query query = getEntityManager().createNamedQuery("Producto.findByProdNombre");
        query.setParameter("prodNombre", prodNombre);
        List<Producto> resuList = query.getResultList();
        return resuList;
    }
    
    public boolean existProduct(String name)
    {
        Query query = getEntityManager().createNamedQuery("Producto.findByProdNombre");
        query.setParameter("prodNombre",  name);
        List<Producto> resultList = query.getResultList();
        return resultList!=null && resultList.size()>0;
    }
    
     public List<Producto> findProductByColumn(Long cenId,String search)
    {
        Query query = getEntityManager().createNamedQuery("Producto.findByColumn");
        query.setParameter("cenId", cenId);
        query.setParameter("prodNombre", "%" + search + "%");
        List<Producto> resultList = query.getResultList();
        return resultList;
    }
     
    public List<Producto> findProductByCenIdOrderbyName(Long cenId)
    {
        Query query = getEntityManager().createNamedQuery("Producto.findProductByCenIdOrderbyName");
        query.setParameter("cenId", cenId);
        List<Producto> resultList = query.getResultList();
        return resultList;
    }
     
    public boolean isProductUsed(Long id)
    {
        Query query = getEntityManager().createNamedQuery("Precio.findByProdId");
        query.setParameter("prodId",  id);
        List<Producto> resultList = query.getResultList();
        return resultList!=null && resultList.size()>0;
    }
    
}
