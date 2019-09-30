/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foxtrot.jee19.logic.dto;

import java.util.List;

/**
 *
 * @author ussocom
 */
public class DefinedPersonList extends Named  {

    private static final long serialVersionUID = -2068344687689060468L;
    
    private String Title;
    
    private List<Person> persons;

    public DefinedPersonList(String uuid, long jpaVersion, String name) {
        super(uuid, jpaVersion, name);
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }
    
    
}
