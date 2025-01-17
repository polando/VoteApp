/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foxtrot.jee19.logic.dao;

import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.NamedQuery;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;
import foxtrot.jee19.entities.TokenEntity;
import foxtrot.jee19.logic.dto.Token;

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
        Query query = em.createNamedQuery("getTokenObjectByTokenString", TokenEntity.class).setParameter("token", token);
        System.out.println("paramter" + query.getParameter("token"));
        TokenEntity tokenEntity = null;
        try {
            tokenEntity = (TokenEntity) query.getSingleResult();
        } catch (NoResultException nre) {
            // Code for handling NoResultException
        } catch (NonUniqueResultException nure) {
            // Code for handling NonUniqueResultException
        }
        return tokenEntity;
    }

    public Long numberOfUsersDidntSubmit(String pollId) {
        return em.createNamedQuery("numberOfUsersDidntSubmit", Long.class)
                .setParameter("pollId", pollId)
                .getSingleResult();
    }

    public List<String> getAllTokenPhrases() {
        return em.createNamedQuery("getAllTokenPhrases", String.class)
                .getResultList();
    }

    public List<TokenEntity> getAllTokenRowsForPoll(String pollId) {
        return em.createNamedQuery("getAllTokenRowsForPoll", TokenEntity.class)
                .setParameter("pollId", pollId)
                .getResultList();
    }
}
