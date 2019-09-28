/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foxtrot.jee19.logic.dao;

import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import foxtrot.jee19.entities.ItemEntity;

/**
 *
 * @author ussocom
 */
@Stateless
@LocalBean
public class ItemAccess extends AbstractAccess<ItemEntity>{

    @Override
    protected Class<ItemEntity> getEntityClass() {
        return ItemEntity.class;
    }

    @Override
    protected ItemEntity newEntity() {
         return new ItemEntity(true);
    }

    @Override
    public long getEntityCount() {
        return em.createNamedQuery("getPollItemCount", Long.class).getSingleResult();
    }
    
    public List<ItemEntity> getPollItemsList() {
        return em.createNamedQuery("getPollItemList", ItemEntity.class
        ).getResultList();
    }
    
    public List<ItemEntity> getNonPermanentPollItems() {
        return em.createNamedQuery("getNonPermanentPollItems", ItemEntity.class
        ).getResultList();
    }
    

    
    
    
    
}
