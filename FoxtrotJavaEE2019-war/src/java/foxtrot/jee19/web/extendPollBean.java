/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foxtrot.jee19.web;

import java.io.Serializable;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import foxtrot.jee19.logic.PollLogic;
import foxtrot.jee19.logic.dto.Poll;
import foxtrot.jee19.web.utilities.TimeUtility;
import foxtrot.jee19.web.utilities.errorMessageUtility;
import java.time.Instant;
import java.util.Map;
import javax.faces.context.ExternalContext;

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
    private TimeUtility timeUtility;
    
    @Inject
    private errorMessageUtility  errorMessageUtility;

    private Poll poll;
    
    private Date endDate;
    
    private String timeOffest;


    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    

    public Poll getPoll() {
        return poll;
    }

    public void setPoll(Poll poll) {
        this.poll = poll;
    }
    
    
    @PostConstruct
    public void init() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        timeOffest = (String) sessionMap.get("timeOffest");
        poll = readSelectedPollFromFlash();
        endDate = timeUtility.InstantToDate(poll.getEndDate(),timeOffest);
    }

    
    private Poll readSelectedPollFromFlash() {
        Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
        return (Poll) flash.get("selectedPoll");
    }
    
    public String extendPoll(){
        polllogic.extendPoll(poll.getUuid(), timeUtility.DateToInstant(endDate,timeOffest));
        return "pollExtendedSuccessfully";
    }

}
