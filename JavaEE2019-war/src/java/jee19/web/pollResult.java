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
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import jee19.logic.PollLogic;
import jee19.logic.dto.Poll;
import jee19.logic.dto.VoteResult;

/**
 *
 * @author ussocom
 */
@ViewScoped
@Named
public class pollResult implements Serializable {

    private static final long serialVersionUID = -614662490787062254L;
    
    @EJB
    private PollLogic polllogic;
    
    private Poll poll;
    
    @PostConstruct
    public void init(){
        poll = readSelectedPollFromFlash();
    }
    
    
    private Poll readSelectedPollFromFlash(){
        Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
        return (Poll)flash.get("selectedPoll");
    }
    
    public List<VoteResult> pollResultByPollidFromFlash(){
        System.out.println("poll"+poll);
         return getPollResultByPollid(poll.getUuid());
    }
    
    private List<VoteResult> getPollResultByPollid(String pollID){
         return polllogic.getPollResultByPollid(pollID);
    }    
}
