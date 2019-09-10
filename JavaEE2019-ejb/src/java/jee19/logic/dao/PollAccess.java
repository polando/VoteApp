/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee19.logic.dao;

import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Entity;
import jee19.entities.PollEntity;
import jee19.logic.PollState;

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
    
    public void updatePoll(PollEntity entity){
        em.merge(entity);
    }
    
    public List<PollEntity> getFinishedPollsIDListByOrganizer(String organizerUUID){
         return em.createNamedQuery("getFinishedPollsIDListByOrganizer", PollEntity.class)
                 .setParameter("organizerUUID", organizerUUID)
                // .setParameter("state", PollState.FINISHED)
                 .getResultList();
    }
    
}
