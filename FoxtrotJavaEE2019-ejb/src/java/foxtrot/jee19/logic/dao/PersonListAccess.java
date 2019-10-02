/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foxtrot.jee19.logic.dao;

import foxtrot.jee19.entities.DefinedPersonListEntity;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author ussocom
 */
@Stateless
@LocalBean
public class PersonListAccess extends AbstractAccess<DefinedPersonListEntity> {

    @Override
    protected Class<DefinedPersonListEntity> getEntityClass() {
        return DefinedPersonListEntity.class;
    }

    @Override
    protected DefinedPersonListEntity newEntity() {
        return new DefinedPersonListEntity(true);
    }

    @Override
    public long getEntityCount() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public List<DefinedPersonListEntity> getAllPredifinedListByPerson(String personUUID){
        return em.createNamedQuery("getAllPredifinedListByPerson", DefinedPersonListEntity.class)
                .setParameter("personUUID", personUUID)
                .getResultList();
    }
    
    public void updatePoll(DefinedPersonListEntity entity) {
        em.merge(entity);
    }
    
    public List<String> getAllPersonListTitlesByCreatorId(String creatorUUID){
        return em.createNamedQuery("getAllPersonListTitlesByCreatorId", String.class)
                .setParameter("creatorUUID", creatorUUID)
                .getResultList();        
    }
}
