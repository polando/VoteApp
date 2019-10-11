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
import foxtrot.jee19.logic.dto.Person;
import foxtrot.jee19.logic.dto.Poll;
import foxtrot.jee19.web.utilities.TimeUtility;
import foxtrot.jee19.web.utilities.errorMessageUtility;
import java.time.Instant;
import java.util.Map;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;

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
    private LoginBean loginBean;
    
    @Inject
    private errorMessageUtility  errorMessageUtility;
    
    @Inject
    private TimeUtility timeUtility;
        
    private DefinedPersonList definedPersonList; 
    
    private Item selectedItem;
    
    private Poll poll;
    
    private Date startDate;
    
    private Date endDate;
    
    private String timeOffest;

    public Poll getPoll() {
        return poll;
    }

    public void setPoll(Poll poll) {
        this.poll = poll;
    }
    
    @PostConstruct
    public void init() {
        poll = readSelectedPollFromFlash();
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        timeOffest = (String) sessionMap.get("timeOffest");
        System.out.println("offset is"+timeOffest);
        startDate = timeUtility.InstantToDate(poll.getStartDate(), timeOffest);
        endDate = timeUtility.InstantToDate(poll.getEndDate(), timeOffest);
    }

    public String editPoll() {
        poll.getOrganizers().add(loginBean.getUser());
        poll.setStartDate(timeUtility.DateToInstant(startDate, timeOffest));
        poll.setEndDate(timeUtility.DateToInstant(endDate, timeOffest));
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
    
    public void modifyItem(Item item){
        poll.getItems().removeIf(s -> s.getUuid().equals(item.getUuid()));
        addItem(item);
    }

    public DefinedPersonList getDefinedPersonList() { 
        return definedPersonList;
    }

    public void setDefinedPersonList(DefinedPersonList definedPersonList) {
        this.definedPersonList = definedPersonList;
    }

    public Item getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(Item selectedItem) {
        this.selectedItem = selectedItem;
    }
    
    public void removeItem(Item item){
        poll.getItems().removeIf(e->e.getUuid().equals(item.getUuid()));
        polllogic.removeItem(item);   
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getTimeOffest() {
        return timeOffest;
    }

    public void setTimeOffest(String timeOffest) {
        this.timeOffest = timeOffest;
    }
    
    
    

    
    
    
}
