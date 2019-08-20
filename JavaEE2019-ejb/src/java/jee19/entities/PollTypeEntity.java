/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee19.entities;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author ussocom
 */
@NamedQueries({
    @NamedQuery(name = "getPollTypeCount", query = "SELECT COUNT(p) FROM PollTypeEntity p"),
    @NamedQuery(name = "getPollTypeList", query = "SELECT p FROM PollTypeEntity p ORDER BY p.name, p.uuid"),
})
@Entity
@Table(name="POLLTYPE")
public class PollTypeEntity extends NamedEntity {
    
    private static final long serialVersionUID = -5834931285362006681L;
    
    private String pollType;

    public String getPollType() {
        return pollType;
    }

    public void setPollType(String pollType) {
        this.pollType = pollType;
    }
    
    public PollTypeEntity() {
        this(false);
    }

    public PollTypeEntity(boolean isNew) {
        super(isNew);
    }
    
    
    
}
