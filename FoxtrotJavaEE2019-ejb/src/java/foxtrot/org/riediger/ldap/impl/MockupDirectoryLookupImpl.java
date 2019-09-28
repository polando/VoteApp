package foxtrot.org.riediger.ldap.impl;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.naming.NamingException;
import foxtrot.org.riediger.ldap.DirectoryLookup;
import foxtrot.org.riediger.ldap.Person;

/**
 * Simulates Directory Lookup by reading user names from a CSV file.
 *
 * The CSV file has a headline and entries of the following format:
 * <pre>
 * NAME;FIRSTNAME;LASTNAME
 * alice;Alice;Wonderland
 * bob;Bob;Builder
 * ...
 * </pre>
 *
 * @author Dr. Volker Riediger <riediger@uni-koblenz.de>
 */
@Singleton
public class MockupDirectoryLookupImpl implements DirectoryLookup {

    private static final String DATAFILE = "testdata/person.csv";

    private Set<Person> persons; // contains all entries
    private Map<String, Person> personIndex; // maps names to entries

    /**
     * Reads the CSV data file with person entries.
     */
    @PostConstruct
    public void init() {
        persons = new TreeSet<>();
        personIndex = new HashMap<>();
        int lineNumber = -1;
        try (LineNumberReader rdr = new LineNumberReader(
                new InputStreamReader(getClass().getResourceAsStream(DATAFILE),
                        "utf-8"))) {
            rdr.readLine(); // skip header line
            lineNumber = rdr.getLineNumber();
            for (String line = rdr.readLine(); line != null; line = rdr.readLine()) {
                lineNumber = rdr.getLineNumber();
                String[] parts = line.split(";");
                Person p = new Person();
                p.setName(parts[0]);
                p.setFirstName(parts.length >= 2 ? parts[1] : "");
                p.setLastName(parts.length >= 3 ? parts[2] : "");
                persons.add(p);
                personIndex.put(p.getName(), p);
            }
        } catch (IOException ex) {
            Logger.getLogger(MockupDirectoryLookupImpl.class.getName()).log(Level.SEVERE, "error in line " + lineNumber, ex);
        }
    }

    @Override
    @Lock(LockType.WRITE)
    public Person lookupPerson(String uid) throws NamingException {
        try {
            Thread.sleep(20);
        } catch (InterruptedException ex) {
        }
        return personIndex.get(uid);
    }

    @Override
    @Lock(LockType.WRITE)
    public SortedSet<Person> lookupPersonsByName(String nameFragment, long countLimit)
            throws NamingException {
        try {
            Thread.sleep(50);
        } catch (InterruptedException ex) {
        }
        SortedSet<Person> result = new TreeSet();
        String[] names = nameFragment.trim().toLowerCase().split("\\s+");
        int cnt = 0;
        PERSON:
        for (Person p : persons) {
            for (String n : names) {
                if (!(p.getName().toLowerCase().contains(n)
                        || p.getFirstName().toLowerCase().contains(n)
                        || p.getLastName().toLowerCase().contains(n))) {
                    continue PERSON;
                }
            }
            result.add(p);
            ++cnt;
            if (countLimit > 0 && cnt >= countLimit) {
                break;
            }
        }
        // if nameFragment does not look like an e-mail address, return the set
        if (nameFragment.indexOf('@') <= 0 || nameFragment.indexOf(' ') >= 0) {
            return result;
        }

        // if result set already contains exact match, return the set
        for (Person p : result) {
            if (p.getName().equals(nameFragment)) {
                return result;
            }
        }

        // otherwise try to find exact match for user with email == nameFragment
        Person p = lookupPerson(nameFragment);
        if (p != null) {
            result.add(p);
        }
        return result;
    }
}
