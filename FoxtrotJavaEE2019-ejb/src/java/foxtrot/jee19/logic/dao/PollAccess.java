/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foxtrot.jee19.logic.dao;

import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Entity;
import foxtrot.jee19.entities.PollEntity;
import foxtrot.jee19.logic.PollState;

/**
 *
 * @author ussocom
 */
@Stateless
@LocalBean
public class PollAccess extends AbstractAccess<PollEntity> {

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

    public void updatePoll(PollEntity entity) {
        em.merge(entity);
    }

    public List<PollEntity> getFinishedPollsIDListByOrganizer(String organizerUUID) {
        return em.createNamedQuery("getFinishedPollsIDListByOrganizer", PollEntity.class)
                .setParameter("organizerUUID", organizerUUID)
                //.setParameter("state", PollState.FINISHED)
                .getResultList();
    }
    
    public List<PollEntity> getPollsIDListByOrganizerAndState(String organizerUUID,PollState pollState) {
        return em.createNamedQuery("getPollsIDListByOrganizerAndState", PollEntity.class)
                .setParameter("organizerUUID", organizerUUID)
                .setParameter("state", pollState)
                .getResultList();
    }
    


    public List<String> getAllPollTitles() {
        return em.createNamedQuery("getAllPollTitles", String.class).getResultList();
    }

    public PollEntity getPollByPollID(String pollUUID) {
        return em.createNamedQuery("getPollbyPollUUID", PollEntity.class)
                .setParameter("pollUUID", pollUUID)
                .getSingleResult();
    }

    public List<PollEntity> getStartedOrVotingPollsIDListByOrganizer(String organizerUUID) {
        return em.createNamedQuery("getStartedOrVotingPollsIDListByOrganizer", PollEntity.class)
                .setParameter("organizerUUID", organizerUUID)
                .setParameter("stateOne", PollState.STARTED)
                .setParameter("stateTwo", PollState.VOTING)
                .getResultList();
    }
    
    public List<PollEntity> getAllPolls(){
         return em.createNamedQuery("getAllPolls", PollEntity.class)
                .getResultList();       
    }
    
    public void removePollByID(String pollUUID){
          em.remove(getPollByPollID(pollUUID));
    }
    
    public List<PollEntity> getAllPublishedPolls() {
        return em.createNamedQuery("getAllPublishedPolls", PollEntity.class)
                .getResultList();
    }

}
