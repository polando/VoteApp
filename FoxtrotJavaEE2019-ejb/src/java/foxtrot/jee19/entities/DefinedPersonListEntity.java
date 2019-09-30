/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foxtrot.jee19.entities;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author ussocom
 */

@NamedQueries({
    @NamedQuery(name = "getAllPredifinedListByPerson", query = "SELECT p FROM DefinedPersonListEntity p WHERE p.ownerPerson.uuid = :personUUID")
})
@Entity
@Table(name = "DEFINEDPERSONLIST")
public class DefinedPersonListEntity extends NamedEntity {
    
    private static final long serialVersionUID = 599256188743688859L;
    
    private String Title;
    
    @ManyToOne
    private PersonEntity ownerPerson;
    
    @ManyToMany
    private List<PersonEntity> persons;
   
    
    public DefinedPersonListEntity() {
        this(false);
    }

    public DefinedPersonListEntity(boolean isNew) {
        super(isNew);
        if (isNew) {
            persons = new ArrayList<>();
        }
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public List<PersonEntity> getPersons() {
        return persons;
    }

    public void setPersons(List<PersonEntity> persons) {
        this.persons = persons;
    }

    public PersonEntity getOwnerPerson() {
        return ownerPerson;
    }

    public void setOwnerPerson(PersonEntity ownerPerson) {
        this.ownerPerson = ownerPerson;
    }
    
    
    
    
    
}
