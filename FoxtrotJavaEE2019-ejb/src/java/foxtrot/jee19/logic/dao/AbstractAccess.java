package foxtrot.jee19.logic.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import foxtrot.jee19.entities.NamedEntity;

/**
 * @author Dr. Volker Riediger <riediger@uni-koblenz.de>
 * @param <E>
 */
public abstract class AbstractAccess<E extends NamedEntity> {

    @PersistenceContext(unitName = "FoxtrotJavaEE2019-ejbPU")
    protected EntityManager em;

    public E getByUuid(String uuid) {
        try {
            return em.createNamedQuery("getNamedEntityByUuid", getEntityClass())
                    .setParameter("uuid", uuid)
                    .getSingleResult();
        } catch (NoResultException e) {
            // Entity with specified UUID doesn't exist
            return null;
        }
    }

    protected abstract Class<E> getEntityClass();

    protected abstract E newEntity();

    public abstract long getEntityCount();

    public E createEntity(String name) {
        E entity = newEntity();
        entity.setName(name);
        em.persist(entity);
        return entity;
    }
    
    
}
