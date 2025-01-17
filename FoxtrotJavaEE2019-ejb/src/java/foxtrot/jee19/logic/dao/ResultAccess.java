/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foxtrot.jee19.logic.dao;

import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import foxtrot.jee19.entities.ResultEntity;

/**
 *
 * @author ussocom
 */
@Stateless
@LocalBean
public class ResultAccess extends AbstractAccess<ResultEntity>{

    @Override
    protected Class<ResultEntity> getEntityClass() {
        return ResultEntity.class;
    }

    @Override
    protected ResultEntity newEntity() {
        return new ResultEntity(true);
    }

    @Override
    public long getEntityCount() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public ResultEntity getEntityByPollAndItemIDAndOptID(String pollId,String itemId,String optionId){
         return em.createNamedQuery("getResultByPollAndItemAndOpt", ResultEntity.class)
                 .setParameter("pollId", pollId)
                 .setParameter("itemId", itemId)
                 .setParameter("optionId", optionId)
                 .getSingleResult();
    }
    
    public List<ResultEntity> getEntityByPollID(String pollId){
         return em.createNamedQuery("getResultByPollID", ResultEntity.class)
                 .setParameter("pollId", pollId)
                 .getResultList();
    }
    

    

    
    
}
