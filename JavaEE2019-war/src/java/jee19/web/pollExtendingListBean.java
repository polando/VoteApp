/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee19.web;

import java.io.Serializable;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import jee19.logic.PollLogic;
import jee19.logic.dto.Person;
import jee19.logic.dto.Poll;

/**
 *
 * @author ussocom
 */
@ViewScoped
@Named
public class pollExtendingListBean implements Serializable {

    private static final long serialVersionUID = 8007001210821876334L;
    
    @EJB
    private PollLogic polllogic;
    
    private Poll selectedPoll;
    
    private Set<Poll> startedOrVotingPollsByOrganaizer;
    
    @Inject
    private LoginBean loginBean;
    
    @PostConstruct
    void init(){
        startedOrVotingPollsByOrganaizer = getStartedOrVotingPollsListByOrganizer();
    }

    private Set<Poll> getStartedOrVotingPollsListByOrganizer(){
            Person loggedInUser = loginBean.getUser();
            Set<Poll> poll = polllogic.getStartedOrVotingPollsIDListByOrganizer(loggedInUser.getUuid()); 
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
            return "seePoll";
        }


    public Set<Poll> getStartedOrVotingPollsByOrganaizer() {
        return startedOrVotingPollsByOrganaizer;
    }
    
}
