/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee19.web;

import java.io.Serializable;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import jee19.logic.PollLogic;
import jee19.logic.dto.Person;
import jee19.logic.dto.PollType;
import jee19.logic.impl.TeamBusinessLogicImpl;

/**
 *
 * @author ussocom
 */

@ViewScoped
@Named
public class pollBean implements Serializable {
    
    private static final long serialVersionUID = -4125098359814998756L;
    
    @EJB
    private PollLogic polllogic;
    
    private List<PollType> polltypes;

    private List<Person> persons;

    public List<Person> getPersons() {
     
    //Logger.getAnonymousLogger().log(Level.INFO, "from logger 11111111111111111");

        return polllogic.getAllUsers();
    }
    
    
    
    
    public List<PollType> getPolltypes() {
        return polllogic.getAllPollTypes();
    }
    
    
    
    
    
}
