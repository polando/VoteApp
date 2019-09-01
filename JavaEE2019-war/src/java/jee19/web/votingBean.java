/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee19.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import jee19.logic.PollLogic;
import jee19.logic.dto.Item;
import jee19.logic.dto.Poll;

/**
 *
 * @author ussocom
 */
@ViewScoped
@Named
public class votingBean implements Serializable {
    
    private static final long serialVersionUID = -7434000390609622052L;
    
    private Set<Item> voteItems;
    
    private List<Item> chosenItems;
    
    private String token;
    
    
    
    @EJB
    private PollLogic polllogic;
   
    private Poll poll;
    
    @PostConstruct
    public void init(){
        poll = readPollFromFlash();
        voteItems = poll.getItemEntities();
        token = readTokenFromFlash();
        
    }

    public List<Item> getChosenItems() {
        return chosenItems;
    }

    public void setChosenItems(List<Item> chosenItems) {
        this.chosenItems = chosenItems;
    }

    public Set<Item> getVoteItems() {
        return voteItems;
    }

    public void setVoteItems(Set<Item> voteItems) {
        this.voteItems = voteItems;
    }

  
    
    public void submitVote(){
        for(Item i:chosenItems)
           polllogic.addToVotes(token,poll.getUuid(), i.getUuid());
    }
    
    private Poll readPollFromFlash(){
        Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
        return (Poll)flash.get("poll");
    }
    
    private String readTokenFromFlash(){
        Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
        return (String)flash.get("token");
    }
    
    
    
    
}
