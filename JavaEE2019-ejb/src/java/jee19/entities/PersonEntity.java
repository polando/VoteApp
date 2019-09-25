package jee19.entities;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@NamedQueries({
    @NamedQuery(name = "getPersonCount", query = "SELECT COUNT(p) FROM PersonEntity p"),
    @NamedQuery(name = "getPersonList", query = "SELECT p FROM PersonEntity p ORDER BY p.name, p.uuid"),
    @NamedQuery(name = "getPersonByName", query = "SELECT p FROM PersonEntity p WHERE p.name = :name")
})
@Entity
@Table(name = "PERSON")
public class PersonEntity extends NamedEntity {

    private static final long serialVersionUID = 8164978510161170908L;

    
    @OneToMany(mappedBy = "personEntity")
    private Set<TokenEntity> pollsAsParticipant;
        
    @ManyToMany(mappedBy = "organizers")
    private Set<PollEntity> pollsAsOrganizer;



    private String firstname;
    private String lastname;
    private LocalDate dateOfBirth;

    public PersonEntity() {
        this(false);
    }

    public PersonEntity(boolean isNew) {
        super(isNew);
        if (isNew) {
            pollsAsOrganizer = new HashSet<>();
            pollsAsParticipant = new HashSet<>();
        }
    }

    public Set<TokenEntity> getPollsAsParticipant() {
        return pollsAsParticipant;
    }

    public void setPollsAsParticipant(Set<TokenEntity> pollsAsParticipant) {
        this.pollsAsParticipant = pollsAsParticipant;
    }


    public Set<PollEntity> getPollsAsOrganizer() {
        return pollsAsOrganizer;
    }

    public void setPollsAsOrganizer(Set<PollEntity> pollsAsOrganizer) {
        this.pollsAsOrganizer = pollsAsOrganizer;
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
