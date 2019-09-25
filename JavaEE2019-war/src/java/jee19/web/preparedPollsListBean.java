/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee19.web;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import jee19.logic.PollLogic;
import jee19.logic.PollState;
import jee19.logic.dto.Person;
import jee19.logic.dto.Poll;

/**
 *
 * @author ussocom
 */
@ViewScoped
@Named
public class preparedPollsListBean implements Serializable {

    private static final long serialVersionUID = 2180746852941350597L;
    
    @EJB
    private PollLogic polllogic;
    
    private Poll selectedPoll;
    
    private Set<Poll> preparedPollsByOrganaizer;
    
    @Inject
    private LoginBean loginBean;
    
    @PostConstruct
    void init(){
        preparedPollsByOrganaizer = getPreparedPollsByOrganizer();
    }

    private Set<Poll> getPreparedPollsByOrganizer(){
            Person loggedInUser = loginBean.getUser();
            Set<Poll> poll = polllogic.getPollsIDListByOrganizerAndState(loggedInUser.getUuid(),PollState.PREPARED); 
        return poll;
    } 

    public Poll getSelectedPoll() {
        return selectedPoll;
    }

    public void setSelectedPoll(Poll selectedPoll) {
        this.selectedPoll = selectedPoll;
    }
    
    public String seePoll(){
            Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
            flash.put("selectedPoll", selectedPoll);
            return "showPoll";
        }
    
    public String startPoll(){
            polllogic.startPoll(selectedPoll.getUuid());
            return "pollStarted";
        }

    public Set<Poll> getPreparedPollsByOrganaizer() {
        return preparedPollsByOrganaizer;
    }

    
}
