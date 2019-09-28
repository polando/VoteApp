/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foxtrot.jee19.web;

import java.io.Serializable;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import foxtrot.jee19.logic.PollLogic;
import foxtrot.jee19.logic.PollState;
import foxtrot.jee19.logic.dto.Person;
import foxtrot.jee19.logic.dto.Poll;

/**
 *
 * @author ussocom
 */
@ViewScoped
@Named
public class pollReviewListBean implements Serializable {
    
    private static final long serialVersionUID = -2763331690427048989L;
    
        
    @EJB
    private PollLogic polllogic;
    
    private Poll selectedPoll;
    
    private Set<Poll> startedPollsByOrganaizer;
    
    @Inject
    private LoginBean loginBean;
    
    @PostConstruct
    void init(){
        startedPollsByOrganaizer = getStartedPollsByOrganizer();
    }

    private Set<Poll> getStartedPollsByOrganizer(){
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
            return "reviewVote";
        }

    public Set<Poll> getStartedPollsByOrganaizer() {
        return startedPollsByOrganaizer;
    }
    
    
    
}
