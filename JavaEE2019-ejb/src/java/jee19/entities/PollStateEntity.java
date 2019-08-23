/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee19.entities;

import java.util.HashSet;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author ussocom
 */
@NamedQueries({
    @NamedQuery(name = "getPollStateCount", query = "SELECT COUNT(p) FROM PollStateEntity p"),
    @NamedQuery(name = "getPollStateList", query = "SELECT p FROM PollStateEntity p ORDER BY p.name, p.uuid"),
})
@Entity
@Table(name="POLLSTATE")
public class PollStateEntity extends NamedEntity{

    private static final long serialVersionUID = 5937946648481478941L;
    
    private String pollState;
      
    
    public PollStateEntity() {
        this(false);
    }

    public PollStateEntity(boolean isNew) {
        super(isNew);
    }

    public String getPollState() {
        return pollState;
    }

    public void setPollState(String pollState) {
        this.pollState = pollState;
    }

    
    
    
}
