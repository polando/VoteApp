/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee19.logic.dto;

import jee19.utilities.DateConvertor;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.inject.Inject;
import jee19.logic.ItemType;



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
    
    private ItemType pollType;
    
    private List<Person> participants;
   
    private Instant createDate;
    
    private PollState pollStateEntity;
    
    private List<Item> items;
    
    private Set<Token> tokens;
 
    private List<Person> organizers;
    
    private Date startDate;
    
    private Date endDate;


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

    public ItemType getPollType() {
        return pollType;
    }

    public void setPollType(ItemType pollType) {
        this.pollType = pollType;
    }

    public List<Person> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Person> participants) {
        this.participants = participants;
    }

    public PollState getPollStateEntity() {
        return pollStateEntity;
    }

    public void setPollStateEntity(PollState pollStateEntity) {
        this.pollStateEntity = pollStateEntity;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    

    public Set<Token> getTokens() {
        return tokens;
    }

    public void setTokens(Set<Token> tokens) {
        this.tokens = tokens;
    }

    public List<Person> getOrganizers() {
        return organizers;
    }

    public void setOrganizers(List<Person> organizers) {
        this.organizers = organizers;
    }

    public Instant getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Instant createDate) {
        this.createDate = createDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    
}
