/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foxtrot.jee19.logic.dto;

import foxtrot.jee19.entities.PersonEntity;

/**
 *
 * @author ussocom
 */
public class Token extends Named{

    private static final long serialVersionUID = 3075959729710272865L;
    
    public Token(String uuid, long jpaVersion, String name) {
        super(uuid, jpaVersion, name);
    }
    
    private PersonEntity personEntity;
    
    private String token;

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
    
    
    
    
    
}
