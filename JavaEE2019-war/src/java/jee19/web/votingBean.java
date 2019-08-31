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
    
    private List<Item> voteItems;
    
    private Item chosenItem;
    
    
    @EJB
    private PollLogic polllogic;
   
    private Poll poll;

    public Item getChosenItem() {
        return chosenItem;
    }

    public void setChosenItem(Item chosenItem) {
        this.chosenItem = chosenItem;
    }
    
    
    
    public Set<Item> getListOfItemsOfaPoll(){
        poll = readPollFromFlash();
        System.out.println(poll.getItemEntities());
        return poll.getItemEntities();
    }
    
    public void submitVote(){
        
        
    }
    
    private Poll readPollFromFlash(){
        Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
        return (Poll)flash.get("poll");
    }
    
    
    
}
