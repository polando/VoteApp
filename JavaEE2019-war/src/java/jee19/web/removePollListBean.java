/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee19.web;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import jee19.logic.PollLogic;
import jee19.logic.dto.Poll;

/**
 *
 * @author ussocom
 */
@ViewScoped
@Named
public class removePollListBean implements Serializable {

    private static final long serialVersionUID = 4537200079954971410L;
    
        @EJB
    private PollLogic polllogic;
    
    private Poll selectedPoll;
    
    private List<Poll> allPolls;
    
    
    @PostConstruct
    void init(){
        allPolls = polllogic.getAllPolls();
    }



    public Poll getSelectedPoll() {
        return selectedPoll;
    }

    public void setSelectedPoll(Poll selectedPoll) {
        this.selectedPoll = selectedPoll;
    }

    public List<Poll> getAllPolls() {
        return allPolls;
    }

    public void setAllPolls(List<Poll> allPolls) {
        this.allPolls = allPolls;
    }
    
    
    
    public String removePoll(){
        polllogic.removePoll(selectedPoll.getUuid());
        return "pollRemoveSuccess";
    }
    
}
