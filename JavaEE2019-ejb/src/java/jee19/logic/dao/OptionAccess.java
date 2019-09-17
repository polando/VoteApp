/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee19.logic.dao;

import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import jee19.entities.OptEntity;

/**
 *
 * @author ussocom
 */
@Stateless
@LocalBean
public class OptionAccess extends AbstractAccess<OptEntity> {

    @Override
    protected Class<OptEntity> getEntityClass() {
        return OptEntity.class;  
    }

    @Override
    protected OptEntity newEntity() {
        return new OptEntity(true); 
    }

    @Override
    public long getEntityCount() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public List<OptEntity> getPermanentOptions() {
        return em.createNamedQuery("getPermanentOptions", OptEntity.class
        ).getResultList();
    }
    
    public List<OptEntity> getNonPermanentOptions() {
        return em.createNamedQuery("getNonPermanentOptions", OptEntity.class
        ).getResultList();
    }
    
}
