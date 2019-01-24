/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tucanchaya.facade;

import com.tucanchaya.entities.Usuariogrupo;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author aranda
 */
@Stateless
public class UsuariogrupoFacade extends AbstractFacade<Usuariogrupo> {

    @PersistenceContext(unitName = "TucanchayaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuariogrupoFacade() {
        super(Usuariogrupo.class);
    }
}
