/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tucanchaya.facade;

import com.tucanchaya.entities.Categoria;
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
public class CategoriaFacade extends AbstractFacade<Categoria> {
    
    @PersistenceContext(unitName = "TucanchayaPU")
    private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public CategoriaFacade(){
        super(Categoria.class);
    }
    
    public List<Categoria> findByCatId( Long catId ){
        Query query = getEntityManager().createNamedQuery("Categoria.findByCatId");
        query.setParameter("catId", catId);
        List<Categoria> resuList = query.getResultList();
        return resuList;
    }
    
    public List<Categoria> findByCatNombre( String catNombre ){
        Query query = getEntityManager().createNamedQuery("Categoria.findByCatId");
        query.setParameter("catNombre", catNombre);
        List<Categoria> resuList = query.getResultList();
        return resuList;
    }
    
    public boolean existCategory(String name)
    {
        Query query = getEntityManager().createNamedQuery("Categoria.findByCatNombre");
        query.setParameter("catNombre",  name);
        List<Categoria> resultList = query.getResultList();
        return resultList!=null && resultList.size()>0;
    }
    
     public List<Categoria> findByColumn(String search)
    {
        Query query = getEntityManager().createNamedQuery("Categoria.findByColumn");
        query.setParameter("catNombre", "%" + search + "%");
        List<Categoria> resultList = query.getResultList();
        return resultList;
    }
     
    public boolean isCategoryUsed(Long id)
    {
        Query query = getEntityManager().createNamedQuery("Producto.findByCatId");
        query.setParameter("catId",  id);
        List<Categoria> resultList = query.getResultList();
        return resultList!=null && resultList.size()>0;
    }
     
}
