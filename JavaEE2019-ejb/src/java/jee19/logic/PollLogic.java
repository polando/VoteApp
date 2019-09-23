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
    
    
    public boolean checkToken(String useruuid, String token);
    
    public Poll getPollByToken(String token);
    
    public void addToVotes(String token,String pollUUID,String ItemUUID,String optionUUID);
     
    public boolean tokenExistAndNotUsed(String token);
    
    public Set<Poll> getFinishedPollsIDListByOrganizer(String organizerUUID);
    
    public Set<Poll> getPreparedPollsIDListByOrganizer(String organizerUUID);

    
    public List<VoteResult> getPollResultByPollid(String pollUUID);

    public List<String> getAllPollTitles();

    public Poll createPoll(String title, String description, Date endDate, Instant createDateInstant, Date startDate, List<Person> participants, List<Person> organizers, List<Item> items);

    public Poll editPoll(Poll poll);

    public Poll getPollByPollUUID(String pollUUID);

    public void startPoll(String pollUUID);

    public boolean checkAllVotesSubmitted(String pollUUID);

    public void setPollStateByPollUUID(String pollUUID);

    public Option createOption(String shortName, String disc, OptionType optionType);

    public Set<Poll> getStartedOrVotingPollsIDListByOrganizer(String organizerUUID);

    public void extendPoll(String pollUUID, Date endDate);

    
    
}
