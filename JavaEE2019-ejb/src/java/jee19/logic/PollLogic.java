/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee19.logic;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import javax.ejb.Remote;
import jee19.entities.PollEntity;
import jee19.logic.dto.Item;
import jee19.logic.dto.Option;
import jee19.logic.dto.Person;
import jee19.logic.dto.Poll;
import jee19.logic.dto.VoteResult;

/**
 *
 * @author ussocom
 */

@Remote
public interface PollLogic {
    
    public List<Person> getAllUsers();
    
    public List<ItemType> getAllItemTypes();
    
    public List<Item> getAllPollItems();
    
    public List<Option> getNonPermanentOptions(); 
    
  //  public List<PollState> getAllPollStates();
    
    public Item createItem(String title, ItemType itemType, List<Option> options);
    
    public Option createOption(String shortName,String disc,boolean permanentOption);

    public Poll createPoll(String title, String description,ItemType polltype, Instant endDateInstant, Instant createDateInstant,Instant startDateInstant,List<Person> participants,List<Person> organizers,List<Item> items );
    
    public boolean checkToken(String useruuid, String token);
    
    public Poll getPollByToken(String token);
    
    public void addToVotes(String token,String pollUUID,String ItemUUID,String optionUUID);
     
    public boolean tokenExistAndNotUsed(String token);
    
    public Set<Poll> getFinishedPollsIDListByOrganizer(String organizerUUID);
    
    public List<VoteResult> getPollResultByPollid(String pollUUID);

    public List<String> getAllPollTitles();

    
    
}
