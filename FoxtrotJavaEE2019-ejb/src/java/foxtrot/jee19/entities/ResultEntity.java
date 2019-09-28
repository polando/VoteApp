/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foxtrot.jee19.entities;

import java.util.HashSet;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author ussocom
 */
@NamedQueries({
    
    @NamedQuery(name = "getResultByPollAndItemAndOpt", query = "SELECT p FROM ResultEntity p WHERE p.poll.uuid = :pollId AND p.item.uuid = :itemId AND p.option.uuid = :optionId"),
    @NamedQuery(name = "getResultByPollID", query = "SELECT p FROM ResultEntity p WHERE p.poll.uuid = :pollId"),
        
})
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
    
    @ManyToOne
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
