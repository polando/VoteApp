
package jee19.entities;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@NamedQueries({
    @NamedQuery(name = "getTokenObjectByTokenString", query = "SELECT p FROM TokenEntity p WHERE p.token = :token"),
    @NamedQuery(name = "numberOfUsersDidntSubmit", query = "SELECT COUNT(p) FROM TokenEntity p WHERE p.pollEntity.uuid = :pollId AND p.used = 0"),
    @NamedQuery(name = "getAllTokenPhrases", query = "SELECT p.token FROM TokenEntity p")

})
@Entity
@Table(name = "TOKEN")
public class TokenEntity extends NamedEntity {
    
    private static final long serialVersionUID = -4645472589132602347L;
    
    public TokenEntity() {
        this(false);
    }

    public TokenEntity(boolean isNew) {
        super(isNew);
    }
    
    @ManyToOne
    private PollEntity pollEntity;
    
    @ManyToOne
    private PersonEntity personEntity;
    
    private String token;
    
    private Boolean used;
    

    public PollEntity getPollEntity() {
        return pollEntity;
    }

    public void setPollEntity(PollEntity pollEntity) {
        this.pollEntity = pollEntity;
    }

    public PersonEntity getPersonEntity() {
        return personEntity;
    }

    public void setPersonEntity(PersonEntity personEntity) {
        this.personEntity = personEntity;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Boolean getUsed() {
        return used;
    }

    public void setUsed(Boolean used) {
        this.used = used;
    }
    
    
    
    
}