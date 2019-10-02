/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foxtrot.jee19.web;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import foxtrot.jee19.logic.PollLogic;
import foxtrot.jee19.logic.dto.DefinedPersonList;
import foxtrot.jee19.logic.dto.Item;
import foxtrot.jee19.logic.dto.Poll;
import foxtrot.jee19.web.utilities.errorMessageUtility;

/**
 *
 * @author ussocom
 */

@ViewScoped
@Named
public class changePollBean  implements Serializable{

    private static final long serialVersionUID = -8672602607708913941L;

    @EJB
    private PollLogic polllogic;
    
        @Inject
    private errorMessageUtility  errorMessageUtility;
        
    private DefinedPersonList definedPersonList;   

    private Poll poll;

    public Poll getPoll() {
        return poll;
    }

    public void setPoll(Poll poll) {
        this.poll = poll;
    }
    
    @PostConstruct
    public void init() {
        poll = readSelectedPollFromFlash();
       // poll = polllogic.getPollByPollUUID(poll.getUuid());
    }

    public String editPoll() {
        poll.setParticipants(definedPersonList.getPersons());
        polllogic.editPoll(poll);
        return "pollEditedSuccessfully";
    }
    
    private Poll readSelectedPollFromFlash() {
        Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
        return (Poll) flash.get("selectedPoll");
    }

    public void addItem(Item item){
        poll.getItems().add(item);
    }

    public DefinedPersonList getDefinedPersonList() { 
        return definedPersonList;
    }

    public void setDefinedPersonList(DefinedPersonList definedPersonList) {
        if(definedPersonList != null){
            poll.setParticipants(definedPersonList.getPersons());
        }  
        this.definedPersonList = definedPersonList;
    }
    
    
}
