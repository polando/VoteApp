/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foxtrot.jee19.web;

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
        preparedPollsByOrganaizer = pollsByOrganizerFromDb();
    }

    public Set<Poll> pollsByOrganizerFromDb(){
            Person loggedInUser = loginBean.getUser();
            Set<Poll> poll = polllogic.getPollsIDListByOrganizerAndState(loggedInUser.getUuid(),PollState.PREPARED); 
        return poll;
    } 

    public Poll getSelectedPoll() {
        return selectedPoll;
    }

    public void setSelectedPoll(Poll selectedPoll) {
        if(selectedPoll != null)
            System.out.println("poll title in selected poll is : "+selectedPoll.getTitle());
        this.selectedPoll = selectedPoll;
    }
    
    public String seePoll(){
            Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();   
            System.out.println("poll title in seepoll is : "+selectedPoll.getTitle());
            flash.put("selectedPoll", selectedPoll);
            return "showPoll";
        }
    
    public String startPoll(){
            selectedPoll.getOrganizers().add(loginBean.getUser());
            polllogic.startPoll(selectedPoll.getUuid());
            return "pollStarted";
        }

    public Set<Poll> getPreparedPollsByOrganaizer() {
        return preparedPollsByOrganaizer;
    }

    
}
