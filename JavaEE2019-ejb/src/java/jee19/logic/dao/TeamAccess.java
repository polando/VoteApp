package jee19.logic.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import jee19.entities.TeamEntity;

/**
 * @author Dr. Volker Riediger <riediger@uni-koblenz.de>
 */
@Stateless
@LocalBean
public class TeamAccess extends AbstractAccess<TeamEntity> {

    @Override
    protected Class<TeamEntity> getEntityClass() {
        return TeamEntity.class;
    }

    @Override
    protected TeamEntity newEntity() {
        return new TeamEntity(true);
    }

    @Override
    public long getEntityCount() {
        throw new UnsupportedOperationException();
    }

}
