/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee19.logic.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.NamedQuery;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;
import jee19.entities.TokenEntity;

@Stateless
@LocalBean
public class TokenAccess extends AbstractAccess<TokenEntity> {

    @Override
    protected Class<TokenEntity> getEntityClass() {
        return TokenEntity.class;
    }

    @Override
    protected TokenEntity newEntity() {
        return new TokenEntity(true);
    }

    @Override
    public long getEntityCount() {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
    public TokenEntity getTokenObjectByTokenString(String token) {
        System.out.println(token);
        Query query = em.createNamedQuery("getTokenObjectByTokenString",TokenEntity.class).setParameter("token", token);
        System.out.println("paramter"+query.getParameter("token"));
        TokenEntity tokenEntity = null; 
         try{
            tokenEntity = (TokenEntity) query.getSingleResult();
           } catch (NoResultException nre) {
           // Code for handling NoResultException
           } catch (NonUniqueResultException nure) {
           // Code for handling NonUniqueResultException
            }
        return tokenEntity;
         }
    
}