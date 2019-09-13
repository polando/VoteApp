/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee19.web;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import jee19.logic.PollLogic;
import jee19.logic.PollType;
import jee19.logic.dto.Item;
import jee19.logic.dto.Person;

/**
 *
 * @author ussocom
 */

@SessionScoped
@Named
public class pollBean implements Serializable {
    
    private static final long serialVersionUID = -4125098359814998756L;
    
    @EJB
    private PollLogic polllogic;
    
    private List<PollType> polltypes;

    private List<Person> persons;
    


    public List<Person> getPersons() {
        return polllogic.getAllUsers();
    }
    

    public List<PollType> getPolltypes() {
        return polllogic.getAllPollTypes();
    }
    
    
    public List<Item> getPollitems() {
        return polllogic.getAllPollItems();
    }
    
        
    public List<Item> getNonPermanentPollItems() {
        return polllogic.getNonPermanentPollItems();
    }
    
    public List<String> getAllPollTitles(){
        return polllogic.getAllPollTitles();
    }
    
    
    
    
    
}
