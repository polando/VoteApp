package foxtrot.org.riediger.ldap.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import javax.ejb.EJB;
import javax.naming.NamingException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import foxtrot.org.riediger.ldap.DirectoryLookup;
import foxtrot.org.riediger.ldap.Person;

/**
 * @author Dr. Volker Riediger <riediger@uni-koblenz.de>
 */
@javax.enterprise.context.Dependent
@Path("/")
public class DirectoryLookupRestResource {

    @EJB
    private DirectoryLookup dl;

    @GET
    @Path("/person/{uid}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_XML})
    public Person getPersonByUid(@PathParam("uid") String uid) throws NamingException {
        Person p = dl.lookupPerson(uid);
        return p == null ? new Person() : p;
    }

    @GET
    @Path("/persons")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_XML})
    public List<Person> getPersonsByNameQP(@QueryParam("name") String name) throws NamingException {
        // access via query parameter
        if (name == null) {
            // undefined parameter, return empty list
            return new ArrayList<>();
        }
        return getPersonsByName(name);
    }

    @GET
    @Path("/persons/{name}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_XML})
    public List<Person> getPersonsByNamePP(@PathParam("name") String name) throws NamingException {
        // access via path parameter
        return getPersonsByName(name);
    }

    private List<Person> getPersonsByName(String name) throws NamingException {
        Person p = dl.lookupPerson(name);
        SortedSet<Person> persons = dl.lookupPersonsByName(name, 10);
        List<Person> result = new ArrayList<>();
        if (p != null) {
            persons.remove(p);
            result.add(p);
        }
        for (Person p1 : persons) {
            result.add(p1);
        }
        return result;
    }
}
