/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee19.logic.dto;

import java.time.Instant;
import java.util.List;
import java.util.Set;


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

 

    


    
    
    
}
