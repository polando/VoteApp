/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee19.logic;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.ejb.Remote;
import jee19.logic.dto.Item;
import jee19.logic.dto.Person;
import jee19.logic.dto.Poll;
import jee19.logic.dto.PollState;
import jee19.logic.dto.PollType;

/**
 *
 * @author ussocom
 */

@Remote
public interface PollLogic {
    
    public List<Person> getAllUsers();
    
    public List<PollType> getAllPollTypes();
    
    public List<Item> getAllPollItems();
    
    public List<PollState> getAllPollStates();
    
    public Item createPollItem(String name);
    
    public Poll createPoll(String title, String description,PollType polltype,PollState pollstate, Instant endDateInstant, Instant createDateInstant,Instant startDateInstant,List<Person> participants,List<Person> organizers );
    
    public boolean checkToken(String useruuid, String token);

    
}
