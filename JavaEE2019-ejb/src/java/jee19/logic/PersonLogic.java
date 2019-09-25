package jee19.logic;

import java.util.List;
import javax.ejb.Remote;
import jee19.logic.dto.Person;

/**
 * @author Dr. Volker Riediger <riediger@uni-koblenz.de>
 */
@Remote
public interface PersonLogic {

    /**
     * Returns a list of all {@link Person}s.
     *
     * @return the person list, sorted by name
     */
    public List<Person> getPersonList();

    /**
     * Retrieves a Person from the DB, identified by the (login) name.
     *
     * @param name the login name (email address) of the person
     * @return a Person object, created if not yet existent
     */
    public Person getPersonByName(String name);


    public Person createPerson(String name);


    public long getPersonCount();


    void storePersonDetails(Person person);
    
    
    
    public List<Person> testLogicMethod();

    public void createTestData();
}
