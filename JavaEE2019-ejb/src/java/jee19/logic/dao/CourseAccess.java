package jee19.logic.dao;

import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import jee19.entities.CourseEntity;

/**
 * @author Dr. Volker Riediger <riediger@uni-koblenz.de>
 */
@Stateless
@LocalBean
public class CourseAccess extends AbstractAccess<CourseEntity> {

    @Override
    protected Class<CourseEntity> getEntityClass() {
        return CourseEntity.class;
    }

    @Override
    protected CourseEntity newEntity() {
        return new CourseEntity(true);
    }

    @Override
    public long getEntityCount() {
        return em.createNamedQuery("getCourseCount", Long.class)
                .getSingleResult();
    }

    public List<CourseEntity> getCourseList() {
        return em.createNamedQuery("getCourseList", CourseEntity.class)
                .getResultList();
    }
}
