/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tucanchaya.facade;

import com.tucanchaya.entities.Color;
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
public class ColorFacade extends AbstractFacade<Color> {

    @PersistenceContext(unitName = "TucanchayaPU")
    private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public ColorFacade() {
        super(Color.class);
    }
    
    public List<Color> findByColorName(String colorNombre)
    {
        Query query = getEntityManager().createNamedQuery("Color.findByColorName");
        query.setParameter("colorNombre", "%" + colorNombre + "%");
        List<Color> resultList = query.getResultList();
        return resultList;
    }
    
    public boolean existColorName(String colorNombre)
    {
        Query query = getEntityManager().createNamedQuery("Color.findByColorNombre");
        query.setParameter("colorNombre",  colorNombre);
        List<Color> resultList = query.getResultList();
        return resultList!=null && resultList.size()>0;
    }
    
    public boolean existHexCode(String hexCode)
    {
        Query query = getEntityManager().createNamedQuery("Color.findByColorHexadecimal");
        query.setParameter("colorHexadecimal",  hexCode);
        List<Color> resultList = query.getResultList();
        return resultList!=null && resultList.size()>0;
    }
    
    public boolean isColorUsed(Integer id)
    {
        Query query = getEntityManager().createNamedQuery("Centrodeportivo.findByColor");
        query.setParameter("colorId",  id);
        List<Color> resultList = query.getResultList();
        return resultList!=null && resultList.size()>0;
    }
    
}
