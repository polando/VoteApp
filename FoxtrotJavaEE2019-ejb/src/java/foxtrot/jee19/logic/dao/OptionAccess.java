/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foxtrot.jee19.logic.dao;

import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import foxtrot.jee19.entities.OptEntity;
import foxtrot.jee19.logic.OptionType;

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
    
    
    public List<OptEntity> getOptionByType(OptionType optionType) {
        return em.createNamedQuery("getPermanentOptionByState", OptEntity.class)
                .setParameter("optionType", optionType)
                .getResultList();
    }
    
}
