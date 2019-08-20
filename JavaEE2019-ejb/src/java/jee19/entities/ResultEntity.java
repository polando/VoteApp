/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee19.entities;

import java.util.HashSet;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author ussocom
 */

@Entity
@Table(name="RESULT")
public class ResultEntity extends NamedEntity{
    
    private static final long serialVersionUID = 7998576183148303094L;

    public ResultEntity() {
    }

    public ResultEntity(boolean isNew) {
        super(isNew);

    }
    
    
    @ManyToOne
    private PollEntity poll;
    
    
    @ManyToOne
    private ItemEntity item;
    
    
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
    
    
}
