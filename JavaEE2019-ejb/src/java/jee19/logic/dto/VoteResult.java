/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee19.logic.dto;

import jee19.entities.ItemEntity;
import jee19.entities.OptEntity;
import jee19.entities.PollEntity;

/**
 *
 * @author ussocom
 */
public class VoteResult extends Named {
    
    private static final long serialVersionUID = 5800845511533395105L;

    public VoteResult(String uuid, long jpaVersion, String name) {
        super(uuid, jpaVersion, name);
    }
    
    private PollEntity poll;
    
    private ItemEntity item;
    
    private OptEntity option;
    
    private int numberOfVotes;

    public PollEntity getPoll() {
        return poll;
    }

    public void setPoll(PollEntity poll) {
        this.poll = poll;
    }

    public ItemEntity getItem() {
        return item;
    }

    public void setItem(ItemEntity item) {
        this.item = item;
    }

    public int getNumberOfVotes() {
        return numberOfVotes;
    }

    public void setNumberOfVotes(int numberOfVotes) {
        this.numberOfVotes = numberOfVotes;
    }

    public OptEntity getOption() {
        return option;
    }

    public void setOption(OptEntity option) {
        this.option = option;
    }
    
    
    
    
    
    
}
