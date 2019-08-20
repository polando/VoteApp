/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee19.web;

import java.io.Serializable;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import jee19.logic.TeamBusinessLogic;
import jee19.logic.dto.Person;

/**
 *
 * @author ussocom
 */

@ViewScoped
@Named
public class testBean implements Serializable {

    private static final long serialVersionUID = -5420592171136665037L;
    
        
    
        @EJB
        private TeamBusinessLogic teamBusinessLogic;
        
        private List<Person> persons; 

        public List<Person> getPersons() {
            
           persons = teamBusinessLogic.testLogicMethod();

            return persons;
        }
      
        
        
}
