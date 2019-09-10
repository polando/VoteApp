/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee19.web;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.inject.Inject;
import javax.inject.Named;
import jee19.logic.PollLogic;
import jee19.logic.dto.Person;
import jee19.logic.dto.Poll;

/**
 *
 * @author ussocom
 */
@RequestScoped
@Named
public class pollListBean implements Serializable {

    private static final long serialVersionUID = 2180746852941350597L;
    
    @EJB
    private PollLogic polllogic;
    
    private Poll selectedPoll;
    
    @Inject
    private LoginBean loginBean;
    
    public Set<Poll> getFinishedPollsByOrganizer(){
            Person loggedInUser = loginBean.getUser();
            Set<Poll> poll =   polllogic.getFinishedPollsIDListByOrganizer(loggedInUser.getUuid());  
        /*    System.out.println("size"+poll.size());
            for(Poll p:poll){
                System.out.println(p.getTitle());
            }*/            
        return poll;
    } 

    public Poll getSelectedPoll() {
        return selectedPoll;
    }

    public void setSelectedPoll(Poll selectedPoll) {
        this.selectedPoll = selectedPoll;
    }
    
    public String seeResult(){
            Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
            flash.put("selectedPoll", selectedPoll);
            return "showResult";
        }

}
