/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foxtrot.jee19.web;

import foxtrot.jee19.logic.PollLogic;
import foxtrot.jee19.logic.dto.Poll;
import foxtrot.jee19.logic.dto.Token;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author ussocom
 */
@ViewScoped
@Named
public class participationTrackingBean implements Serializable  {

    private static final long serialVersionUID = 7910555508388171034L;
        
    @EJB
    private PollLogic polllogic;
    
    private Poll poll;
    
    private List<Token> tokens;
  
    @PostConstruct
    public void init(){
        poll = readSelectedPollFromFlash();
        tokens = getAllParticipantsAndStates();
    }

    public Poll getPoll() {
        return poll;
    }

    public List<Token> getTokens() {
        return tokens;
    }

    public void setTokens(List<Token> tokens) {
        this.tokens = tokens;
    }
    
    
    
    private Poll readSelectedPollFromFlash(){
        Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
        return (Poll)flash.get("selectedPoll");
    }
  
    public List<Token> getAllParticipantsAndStates(){
        return polllogic.getAllParticipantsAndStates(poll.getUuid());
    }

    
}
