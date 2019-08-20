/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee19.entities;


import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author ussocom
 */
@Entity
@Table(name="POLL")
public class PollEntity extends NamedEntity{

    private static final long serialVersionUID = -2818216934131204323L;
    
    
    private String title;  
    
    private String description;
    
    private Timestamp startDate;
    
    private Timestamp endDate;
    
    private Timestamp createDate;
    
    
    @ManyToOne
    private PollTypeEntity pollTypeEntity;
   
        
    
    @ManyToOne
    private PollStateEntity pollStateEntity;
    
    
    @ManyToMany
    private Set<PersonEntity> participants;
    
    
    @ManyToMany
    private Set<PersonEntity> organizers;
    
   
    @OneToMany(mappedBy = "poll")
    private Set<ResultEntity> resultEntities;
    
    
   public PollEntity() {
        this(false);
    }

    public PollEntity(boolean isNew) {
        super(isNew);
        if (isNew) {
            resultEntities = new HashSet<>();
        }
    }

    public Set<PersonEntity> getParticipants() {
        return participants;
    }

    public void setParticipants(Set<PersonEntity> participants) {
        this.participants = participants;
    }

    public Set<PersonEntity> getOrganizers() {
        return organizers;
    }

    public void setOrganizers(Set<PersonEntity> organizers) {
        this.organizers = organizers;
    }


    public Set<ResultEntity> getResultEntities() {
        return resultEntities;
    }

    public void setResultEntities(Set<ResultEntity> resultEntities) {
        this.resultEntities = resultEntities;
    }

    
    public PollTypeEntity getPollTypeEntity() {
        return pollTypeEntity;
    }

    public void setPollTypeEntity(PollTypeEntity pollTypeEntity) {
        this.pollTypeEntity = pollTypeEntity;
    }

    
    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public PollStateEntity getPollStateEntity() {
        return pollStateEntity;
    }

    public void setPollStateEntity(PollStateEntity pollStateEntity) {
        this.pollStateEntity = pollStateEntity;
    }

    
 
    
 
    
    
    

    
}
