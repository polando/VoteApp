/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee19.logic.dao;

import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import jee19.entities.PollStateEntity;

/**
 *
 * @author ussocom
 */

@Stateless
@LocalBean
public class PollStateAccess extends AbstractAccess<PollStateEntity>{

    @Override
    protected Class<PollStateEntity> getEntityClass() {
         return PollStateEntity.class;
    }

    @Override
    protected PollStateEntity newEntity() {
        return new PollStateEntity(true);
    }

    @Override
    public long getEntityCount() {
        return em.createNamedQuery("getPollStateCount", Long.class).getSingleResult();
    }
    
    public List<PollStateEntity> getPollStateList() {
        return em.createNamedQuery("getPollStateList", PollStateEntity.class
        ).getResultList();
    }
    
}
