/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee19.logic.dto;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import jee19.entities.PollTypeEntity;


/**
 *
 * @author ussocom
 */
public class Poll extends Named{

    private static final long serialVersionUID = 3376868050673997100L;
    
    public Poll(String uuid, long jpaVersion, String name) {
        super(uuid, jpaVersion, name);
    }
    
    private String title;  
    
    private String description;
    
    private PollType pollType;
    
    private List<Person> participants;
    
    private Instant startDate;
    
    private Instant endDate;
    
    private Instant createDate;
    
    private PollState pollStateEntity;
    
    private Set<Item> itemEntities;
    
    private Set<Token> tokens;
 
    private Set<Person> organizers;
    
    
   

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PollType getPollType() {
        return pollType;
    }

    public void setPollType(PollType pollType) {
        this.pollType = pollType;
    }

    public List<Person> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Person> participants) {
        this.participants = participants;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public Instant getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Instant createDate) {
        this.createDate = createDate;
    }

    public PollState getPollStateEntity() {
        return pollStateEntity;
    }

    public void setPollStateEntity(PollState pollStateEntity) {
        this.pollStateEntity = pollStateEntity;
    }

    public Set<Item> getItemEntities() {
        return itemEntities;
    }

    public void setItemEntities(Set<Item> itemEntities) {
        this.itemEntities = itemEntities;
    }

    public Set<Token> getTokens() {
        return tokens;
    }

    public void setTokens(Set<Token> tokens) {
        this.tokens = tokens;
    }

    public Set<Person> getOrganizers() {
        return organizers;
    }

    public void setOrganizers(Set<Person> organizers) {
        this.organizers = organizers;
    }

 

    


    
    
    
}
