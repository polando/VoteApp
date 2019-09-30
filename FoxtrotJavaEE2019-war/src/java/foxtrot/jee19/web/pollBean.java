/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foxtrot.jee19.web;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import foxtrot.jee19.logic.PollLogic;
import foxtrot.jee19.logic.ItemType;
import foxtrot.jee19.logic.dto.DefinedPersonList;
import foxtrot.jee19.logic.dto.Item;
import foxtrot.jee19.logic.dto.Option;
import foxtrot.jee19.logic.dto.Person;
import javax.inject.Inject;

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
    
    @Inject
    private LoginBean loginBean;
    
    private List<ItemType> itemTypes;

    private List<Person> persons;
    
 
    public List<Option> getNonPermanentOptions() {
        return polllogic.getNonPermanentOptions();
    }

    public PollLogic getPolllogic() {
        return polllogic;
    }

    public void setPolllogic(PollLogic polllogic) {
        this.polllogic = polllogic;
    }

    public List<ItemType> getItemTypes() {
        return polllogic.getAllItemTypes();
    }


    public List<Person> getPersons() {
        return polllogic.getAllUsers();
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }
    
    public List<DefinedPersonList> getAllPredifinedListByPerson(){
        Person loggedInUser = loginBean.getUser();;
        return polllogic.getAllPredifinedListByPerson(loggedInUser.getUuid());
    }
    


    
    
}
