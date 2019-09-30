package foxtrot.jee19.logic.dao;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.NamingException;
import javax.persistence.NoResultException;
import foxtrot.jee19.entities.PersonEntity;
import foxtrot.jee19.logic.dto.Person;
import foxtrot.org.riediger.ldap.DirectoryLookup;

/**
 * @author Dr. Volker Riediger <riediger@uni-koblenz.de>
 */
@Stateless
@LocalBean
public class PersonAccess extends AbstractAccess<PersonEntity> {

    @EJB
    private DirectoryLookup directoryLookup;

    @Override
    public PersonEntity createEntity(String name) {
        name = name.trim().toLowerCase();
        PersonEntity pe = super.createEntity(name);

        try {
            // try to find firstname and lastname in directory
            foxtrot.org.riediger.ldap.Person p = directoryLookup.lookupPerson(name);
            if (p != null) {
                pe.setFirstname(p.getFirstName());
                pe.setLastname(p.getLastName());
            }
        } catch (NamingException ex) {
            Logger.getLogger(PersonAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pe;
    }

    @Override
    protected Class<PersonEntity> getEntityClass() {
        return PersonEntity.class;
    }

    @Override
    protected PersonEntity newEntity() {
        return new PersonEntity(true);
    }

    @Override
    public long getEntityCount() {
        return em.createNamedQuery("getPersonCount", Long.class
        ).getSingleResult();
    }

    public List<PersonEntity> getPersonList() {
        return em.createNamedQuery("getPersonList", PersonEntity.class
        ).getResultList();
    }

    @RolesAllowed("AUTHENTICATED")
    public PersonEntity getPersonByName(String name) {
        name = name.trim().toLowerCase();

        try {
            // try to find a person
            return em.createNamedQuery("getPersonByName", PersonEntity.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException ex) {
            // Create a PersonEntity for the name.
            return createEntity(name);
        }
    }

    @RolesAllowed("AUTHENTICATED")
    public void storePersonDetails(Person person) {
        System.err.println("storePersonDetails " + person.getUuid());
        PersonEntity pe = getByUuid(person.getUuid());
        pe.setFirstname(person.getFirstname());
        pe.setLastname(person.getLastname());
        pe.setDateOfBirth(person.getDateOfBirth());
    }
    
    
    
}
