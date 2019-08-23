/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee19.logic.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import jee19.entities.PollEntity;

/**
 *
 * @author ussocom
 */
@Stateless
@LocalBean
public class PollAccess extends AbstractAccess<PollEntity>{

    @Override
    protected Class<PollEntity> getEntityClass() {
        return PollEntity.class;
    }

    @Override
    protected PollEntity newEntity() {
        return new PollEntity(true);
    }

    @Override
    public long getEntityCount() {
        return em.createNamedQuery("getPollCount", Long.class).getSingleResult();
    }
    
}
