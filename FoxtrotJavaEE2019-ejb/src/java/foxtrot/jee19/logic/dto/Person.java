package foxtrot.jee19.logic.dto;

import java.time.LocalDate;
import java.util.Set;

public class Person extends Named {

    private static final long serialVersionUID = 2813983198416172587L;

    private Set<Team> teams;

    private Set<Course> courses;

    private String firstname;
    private String lastname;
    private LocalDate dateOfBirth;

    public Person(String uuid, long jpaVersion, String name) {
        super(uuid, jpaVersion, name);
    }

    public Set<Team> getTeams() {
        return teams;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    
    
}
