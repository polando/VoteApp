/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee19.web;

import java.io.Serializable;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import jee19.logic.PollLogic;
import jee19.logic.dto.Poll;
import jee19.web.utilities.errorMessageUtility;

/**
 *
 * @author ussocom
 */
@ViewScoped
@Named
public class extendPollBean implements Serializable {
    
    private static final long serialVersionUID = 3530453365009265610L;
    
    @EJB
    private PollLogic polllogic;
    
    @Inject
    private errorMessageUtility  errorMessageUtility;

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
    }

    
    private Poll readSelectedPollFromFlash() {
        Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
        return (Poll) flash.get("selectedPoll");
    }
    
    public String extendPoll(){
        polllogic.extendPoll(poll.getUuid(), poll.getEndDate());
        return "pollExtendedSuccessfully";
    }

}
