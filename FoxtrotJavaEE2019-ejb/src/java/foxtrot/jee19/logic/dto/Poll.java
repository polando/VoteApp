/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foxtrot.jee19.logic.dto;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Set;
import foxtrot.jee19.logic.ItemType;
import foxtrot.jee19.logic.PollState;



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
    
   // private PollState pollStateEntity;
    
    private PollState pollState;
    
    private List<Item> items;
    
    private Set<Token> tokens;
 
    private List<Person> organizers;
    
    private Instant startDate;
    
    private Instant endDate;
    
    private boolean resultPublished;
    
    private boolean participationTracking;

    public boolean isResultPublished() {
        return resultPublished;
    }

    public void setResultPublished(boolean resultPublished) {
        this.resultPublished = resultPublished;
    }
    
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

   /* public PollState getPollStateEntity() {
        return pollStateEntity;
    }

    public void setPollStateEntity(PollState pollStateEntity) {
        this.pollStateEntity = pollStateEntity;
    }*/

    public PollState getPollState() {
        return pollState;
    }

    public void setPollState(PollState pollState) {
        this.pollState = pollState;
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

    public boolean isParticipationTracking() {
        return participationTracking;
    }

    public void setParticipationTracking(boolean participationTracking) {
        this.participationTracking = participationTracking;
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
    
    

    
    
}
