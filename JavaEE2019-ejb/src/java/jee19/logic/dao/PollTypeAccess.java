/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee19.logic.dao;

import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import jee19.entities.PollTypeEntity;


/**
 *
 * @author ussocom
 */

@Stateless
@LocalBean
public class PollTypeAccess extends AbstractAccess<PollTypeEntity> {

    @Override
    protected Class<PollTypeEntity> getEntityClass() {
        return PollTypeEntity.class;
    }

    @Override
    protected PollTypeEntity newEntity() {
        return new PollTypeEntity(true);
    }

    @Override
    public long getEntityCount() {
       return em.createNamedQuery("getPollTypeCount", Long.class).getSingleResult();
    }
    
    public List<PollTypeEntity> getPollTypeList() {
        return em.createNamedQuery("getPollTypeList", PollTypeEntity.class
        ).getResultList();
    }

    
}
