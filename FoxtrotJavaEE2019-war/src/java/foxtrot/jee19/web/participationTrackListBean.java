/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foxtrot.jee19.web;

import foxtrot.jee19.logic.PollLogic;
import foxtrot.jee19.logic.PollState;
import foxtrot.jee19.logic.dto.Person;
import foxtrot.jee19.logic.dto.Poll;
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

/**
 *
 * @author ussocom
 */
@ViewScoped
@Named
public class participationTrackListBean implements Serializable {
    
    private static final long serialVersionUID = 8728678416855682728L;
    
    @EJB
    private PollLogic polllogic;
    
    private Poll selectedPoll;
       
    private List<Poll> trackingActivePollsByOrganaizer;
    
    @Inject
    private LoginBean loginBean;
    
    @PostConstruct
    void init(){
        trackingActivePollsByOrganaizer = getTrackingActivePollsByOrganizer();
    }

    public List<Poll> getTrackingActivePollsByOrganaizer() {
        return trackingActivePollsByOrganaizer;
    }

    private List<Poll> getTrackingActivePollsByOrganizer(){
            Person loggedInUser = loginBean.getUser();
            List<Poll> poll = polllogic.getAllPollsbyTrackingAndOrganizer(loggedInUser.getUuid(), true);
        return poll;
    } 
    
    public Poll getSelectedPoll() {
        return selectedPoll;
    }

    public void setSelectedPoll(Poll selectedPoll) {
        this.selectedPoll = selectedPoll;
    }
    
    public String seePollResult(){
            Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
            flash.put("selectedPoll", selectedPoll);
            return "showResult";
        }
    
}
