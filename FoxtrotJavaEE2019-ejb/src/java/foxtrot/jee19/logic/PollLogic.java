/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foxtrot.jee19.logic;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.ejb.Remote;
import foxtrot.jee19.entities.PollEntity;
import foxtrot.jee19.logic.dto.DefinedPersonList;
import foxtrot.jee19.logic.dto.Item;
import foxtrot.jee19.logic.dto.Option;
import foxtrot.jee19.logic.dto.Person;
import foxtrot.jee19.logic.dto.Poll;
import foxtrot.jee19.logic.dto.Token;
import foxtrot.jee19.logic.dto.VoteResult;

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
      
    public List<VoteResult> getPollResultByPollid(String pollUUID);

    public List<String> getAllPollTitles();

    public Poll editPoll(Poll poll);

    public Poll getPollByPollUUID(String pollUUID);

    public void startPoll(String pollUUID);

    public boolean checkAllVotesSubmitted(String pollUUID);

    public void setPollStateByPollUUID(String pollUUID);

    public Option createOption(String shortName, String disc, OptionType optionType);

    public Set<Poll> getStartedOrVotingPollsIDListByOrganizer(String organizerUUID);


    public List<Poll> getAllPolls();

    public void removePoll(String pollUUID);

    public Set<Poll> getPollsIDListByOrganizerAndState(String organizerUUID, PollState pollState);

    public void setPollPublished(String pollUUID, boolean published);

    public Set<Poll> getPublishedPolls();

    public List<Token> getAllParticipantsAndStates(String pollUUID);


    public List<Poll> getAllPollsbyTrackingAndOrganizer(String organizerUUID, boolean tracking);

    public List<DefinedPersonList> getAllPredifinedListByPerson(String uuid);

    public void createDefinedPersonList(String title, String ownerId, List<Person> personsInList);

    public void editPersonList(DefinedPersonList definedPersonList);

    public List<String> getAllPersonListTitlesByCreatorId(String creatorId);

    public void removeItem(Item item);

    public void extendPoll(String pollUUID, Instant endDate);

    public Poll createPoll(String title, String description, Instant endDate, Instant createDateInstant, Instant startDate, List<Person> participants, List<Person> organizers, List<Item> items, boolean participationTracking);
    
    

    
    
}
