/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foxtrot.jee19.logic.impl;

import com.sun.xml.rpc.processor.modeler.j2ee.xml.string;
import foxtrot.jee19.entities.DefinedPersonListEntity;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.ConsoleHandler;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import foxtrot.jee19.entities.ItemEntity;
import foxtrot.jee19.entities.PersonEntity;
import foxtrot.jee19.entities.PollEntity;
import foxtrot.jee19.entities.ResultEntity;
import foxtrot.jee19.entities.TokenEntity;
import java.time.Instant;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.Date;
import java.util.TimeZone;
import javax.annotation.PostConstruct;
import foxtrot.jee19.entities.OptEntity;
import java.util.HashMap;
import foxtrot.jee19.entities.NamedEntity;
import foxtrot.jee19.utilities.MailBean;
import foxtrot.jee19.utilities.Notification;


//import jee19.entities.TokenEntity;
import foxtrot.jee19.logic.PollLogic;
import foxtrot.jee19.logic.PollState;
import foxtrot.jee19.logic.ItemType;
import foxtrot.jee19.logic.OptionType;
import foxtrot.jee19.logic.dao.ItemAccess;
import foxtrot.jee19.logic.dao.OptionAccess;
import foxtrot.jee19.logic.dao.PersonAccess;
import foxtrot.jee19.logic.dao.PersonListAccess;
import foxtrot.jee19.logic.dao.PollAccess;
import foxtrot.jee19.logic.dao.ResultAccess;
import foxtrot.jee19.logic.dao.TokenAccess;
import foxtrot.jee19.logic.dto.DefinedPersonList;
//import jee19.logic.dao.TokenAccess;
import foxtrot.jee19.logic.dto.Item;
import foxtrot.jee19.logic.dto.Option;
import foxtrot.jee19.logic.dto.Person;
import foxtrot.jee19.logic.dto.Poll;
import foxtrot.jee19.logic.dto.Token;
import foxtrot.jee19.logic.dto.VoteResult;
import foxtrot.jee19.utilities.BackgroundJobManager;
import java.util.Map;

/**
 *
 * @author ussocom
 */
@Stateless
public class PollLogicImpl implements PollLogic {

    @EJB
    private PersonAccess personAccess;

    @EJB
    private PollAccess pollAccess;

    @EJB
    private ResultAccess resultAccess;

    @EJB
    private ItemAccess itemAccess;

    @EJB
    private TokenAccess tokenAccess;

    @EJB
    private BackgroundJobManager backgroundJobManager;

    @EJB
    private OptionAccess optionAccess;
    
    @EJB
    private Notification notification;
    
    @EJB
    private PersonListAccess personListAccess;
    
   /* @EJB
    private HashString hashString;*/

    @Override
    public List<Person> getAllUsers() {
        ConsoleHandler ch = new ConsoleHandler();
        List<PersonEntity> l = personAccess.getPersonList();
        List<Person> result = new ArrayList<>(l.size());
        for (PersonEntity pe : l) {
            Person p = new Person(pe.getUuid(), pe.getJpaVersion(), pe.getName());
            p.setFirstname(pe.getFirstname());
            p.setLastname(pe.getLastname());
            p.setDateOfBirth(pe.getDateOfBirth());
            result.add(p);
        }
        return result;
    }

    @Override
    public List<ItemType> getAllItemTypes() {
        List<ItemType> result = new ArrayList<>();
        for (ItemType p : ItemType.values()) {
            result.add(p);
        }
        return result;
    }

    @Override
    public List<Item> getAllPollItems() {
        List<ItemEntity> l = itemAccess.getPollItemsList();
        List<Item> result = new ArrayList<>(l.size());

        for (ItemEntity pe : l) {
            Item p = new Item(pe.getUuid(), pe.getJpaVersion(), pe.getName());
            result.add(p);
        }
        return result;

    }

    @Override
    public Item createItem(String title, ItemType itemType, List<Option> options) {
        ItemEntity p = itemAccess.createEntity(title);
        p.setTitle(title);
        p.setItemType(itemType);
        List<OptEntity> optionEntities = new ArrayList<>();
        List<Option> ops = new ArrayList<>();
        if (itemType.equals(ItemType.YesNo)) {
            optionEntities.addAll(optionAccess.getOptionByType(OptionType.YesNo));
        } else {
            options.forEach((option) -> {
                optionEntities.add(optionAccess.getByUuid(option.getUuid()));
            });
        }
        p.setOptionEntities(optionEntities);
        Item item = new Item(p.getUuid(), p.getJpaVersion(), p.getName());
        item.setTitle(p.getTitle());
        item.setItemType(p.getItemType());
        p.getOptionEntities().forEach((o) -> {
            Option option = new Option(o.getUuid(), o.getJpaVersion(), o.getName());
            option.setShortName(o.getShortName());
            option.setDiscription(o.getDiscription());
            option.setOptionType(o.getOptiontype());
            ops.add(option);
        });
        item.setOptions(ops);

        return item;
    }

    @Override
    public Poll createPoll(String title, String description, Date endDate, Instant createDateInstant, Date startDate, List<Person> participants, List<Person> organizers, List<Item> items,boolean participationTracking) {
        PollEntity pollEntity = pollAccess.createEntity(title);
        setPollProperties(pollEntity, title, description, endDate, createDateInstant, startDate, participants, organizers, items , false,participationTracking);
        return new Poll(pollEntity.getUuid(), pollEntity.getJpaVersion(), pollEntity.getName());
    }

    @Override
    public Poll editPoll(Poll poll) {
        PollEntity pollEntity = pollAccess.getByUuid(poll.getUuid());
        setPollProperties(pollEntity, poll.getTitle(), poll.getDescription(), poll.getEndDate(), poll.getCreateDate(), poll.getStartDate(), poll.getParticipants(), poll.getOrganizers(), poll.getItems(),false,poll.isParticipationTracking());
        return new Poll(pollEntity.getUuid(), pollEntity.getJpaVersion(), pollEntity.getName());
    }

    private void setPollProperties(PollEntity pollEntity, String title, String description, Date endDate, Instant createDateInstant, Date startDate, List<Person> participants, List<Person> organizers, List<Item> items, Boolean resultPublished,boolean participationTracking) {
        System.out.println("participationTracking"+participationTracking);
        List<PersonEntity> organizerEntity = new ArrayList<>();
        List<PersonEntity> participantEntity = new ArrayList<>();
        List<ItemEntity> itemEntities = new ArrayList<>();

        organizers.forEach((p) -> {
            organizerEntity.add(personAccess.getByUuid(p.getUuid()));
        });

        participants.forEach((p) -> {
            participantEntity.add(personAccess.getByUuid(p.getUuid()));
        });

        pollEntity.setTitle(title);
        pollEntity.setDescription(description);
        pollEntity.setOrganizers(organizerEntity);
        pollEntity.setParticipants(participantEntity);
        pollEntity.setStartDate(DateToInstant(startDate));
        pollEntity.setEndDate(DateToInstant(endDate));
        pollEntity.setCreateDate(createDateInstant);
        pollEntity.setResultPublished(resultPublished);
        pollEntity.setParticipationTracking(participationTracking);

        items.forEach((i) -> {
            ItemEntity itemEntity = itemAccess.getByUuid(i.getUuid());
            itemEntity.setTitle(i.getTitle());
            itemEntity.setItemType(i.getItemType());
            List<OptEntity> optionEntities = new ArrayList<>();
            if (i.getItemType().equals(ItemType.YesNo)) {
                optionEntities.addAll(optionAccess.getOptionByType(OptionType.YesNo));
            } else {
                i.getOptions().forEach((option) -> {
                    optionEntities.add(optionAccess.getByUuid(option.getUuid()));
                });
            }
           /* i.getOptions().forEach((option) -> {
                optionEntities.add(optionAccess.getByUuid(option.getUuid()));
            });*/
            itemEntity.setOptionEntities(optionEntities);
            itemEntities.add(itemEntity);
        });

        pollEntity.setItemEntities(itemEntities);

        pollEntity.setPollState(PollState.PREPARED);

    }

     
    private HashMap getPollInfo(PollEntity pollEntity)
    {
        HashMap<String, String> emailInfo = new HashMap<String, String>();
        emailInfo.put("title", pollEntity.getTitle());
        emailInfo.put("start_date", pollEntity.getStartDate().toString());
        emailInfo.put("end_date", pollEntity.getEndDate().toString());
        emailInfo.put("number_of_participants", Integer.toString(pollEntity.getParticipants().size()));
        return emailInfo;
    }
    @Override
    public void startPoll(String pollUUID) {

        Set<TokenEntity> tokenEntity = new HashSet<>();

        PollEntity pollEntity = pollAccess.getByUuid(pollUUID);

        HashMap<String, String> emailInfo = new HashMap<String, String>();
        emailInfo = getPollInfo(pollEntity);
               
        for (PersonEntity p: pollEntity.getParticipants()) {
            Token token = createToken(p.getName() + pollEntity.getName(), p, pollEntity);
            tokenEntity.add(tokenAccess.getByUuid(token.getUuid()));
            emailInfo.put("token", token.getToken());
            emailInfo.put("email", p.getName());
            notification.sendNotificationToParticioants(emailInfo);
        }
        
        pollEntity.getItemEntities().forEach((i) -> {
            i.getOptionEntities().add(optionAccess.getByUuid(createOption("Abstain", "abstainDisc", OptionType.Abstain).getUuid())); 
        });    

        pollEntity.setTokens(tokenEntity);
        
        pollEntity.getItemEntities().forEach((i) -> {
            i.getOptionEntities().forEach((o) -> {
                createResultEntity(pollEntity.getName() + i.getName(), pollEntity.getUuid(), i.getUuid(), o.getUuid());
            });
        });
        backgroundJobManager.seTimerForPoll(pollEntity.getUuid(), pollEntity.getStartDate(), pollEntity.getEndDate());

        pollEntity.setPollState(PollState.STARTED);
    }

    private ResultEntity createResultEntity(String name, String pollUUID, String ItemUUID, String optionUUID) {
        ResultEntity resultEntity = resultAccess.createEntity(name);
        resultEntity.setItem(itemAccess.getByUuid(ItemUUID));
        resultEntity.setPoll(pollAccess.getByUuid(pollUUID));
        resultEntity.setOption(optionAccess.getByUuid(optionUUID));
        resultEntity.setNumberOfVotes(0);
        return resultEntity;
    }

    @Override
    public void addToVotes(String token, String pollUUID, String ItemUUID, String OptionUUID) {
        ResultEntity resultEntity = resultAccess.getEntityByPollAndItemIDAndOptID(pollUUID, ItemUUID, OptionUUID);
        resultEntity.setNumberOfVotes(resultEntity.getNumberOfVotes() + 1);
        TokenEntity tokenEntity = tokenAccess.getTokenObjectByTokenString(token);
        tokenEntity.setUsed(Boolean.TRUE);
    }

    private Token createToken(String name, PersonEntity personEntity, PollEntity pollEntity) {
        TokenEntity tokenEntity = tokenAccess.createEntity(name);
        tokenEntity.setPersonEntity(personEntity);
        tokenEntity.setPollEntity(pollEntity);
        String TokenPhrase = hashAString(name);
        List<String> AllPhrases = tokenAccess.getAllTokenPhrases();
        while(AllPhrases.contains(TokenPhrase)){
            TokenPhrase = hashAString(name);
        }
        tokenEntity.setToken(TokenPhrase);
        tokenEntity.setUsed(false);
        Token token = new Token(tokenEntity.getUuid(), tokenEntity.getJpaVersion(), tokenEntity.getName());
        token.setToken(tokenEntity.getToken());
        return token;
    }

    @Override
    public Option createOption(String shortName, String disc, OptionType optionType) {
        OptEntity optEntity = optionAccess.createEntity(shortName);
        optEntity.setShortName(shortName);
        optEntity.setDiscription(disc);
        optEntity.setOptiontype(optionType);
        return new Option(optEntity.getUuid(), optEntity.getJpaVersion(), optEntity.getName());
    }

    @Override
    public boolean tokenExistAndNotUsed(String token) {
        TokenEntity tokenEntity = getTokenByTokenString(token);
        if (tokenEntity != null) {
            return !(tokenEntity.getUsed());
        } else {
            return false;
        }
    }

    private String hashAString(String input) {
        String hashedString = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            int byteLength = 20;
            SecureRandom secureRandom = new SecureRandom();
            byte[] randomString = new byte[byteLength];
            secureRandom.nextBytes(randomString);
            input = input.concat(Base64.getUrlEncoder().withoutPadding().encodeToString(randomString));
            byte[] hashInBytes = md.digest(input.getBytes(StandardCharsets.UTF_8));

            StringBuilder sb = new StringBuilder();
            for (byte b : hashInBytes) {
                sb.append(String.format("%02x", b));
            }
            hashedString = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hashedString;
    }

    @Override
    public boolean checkToken(String personUUID, String token) {
        Object result = tokenAccess.getTokenObjectByTokenString(token);
        PersonEntity personEntity = personAccess.getByUuid(personUUID);
        TokenEntity tokenEntity = null;
        if (result == null) {
            return false;
        } else {
            tokenEntity = (TokenEntity) result;
            System.out.println("token is  " + tokenEntity.getToken());
        }
        return tokenEntity.getPersonEntity().equals(personEntity);
    }

    @Override
    public Poll getPollByToken(String token) {
        TokenEntity tokenEntity = tokenAccess.getTokenObjectByTokenString(token);
        PollEntity pollEntity = pollAccess.getByUuid(tokenEntity.getPollEntity().getUuid());
        Poll poll = new Poll(pollEntity.getUuid(), pollEntity.getJpaVersion(), pollEntity.getName());
        return pollEnityToPoll(pollEntity,poll);
    }

    private Poll pollEnityToPoll(PollEntity pollEntity, Poll poll) {
        List<Item> items = new ArrayList<>();
        List<Person> participants = new ArrayList<>();
        List<Person> oraganizers = new ArrayList<>();
        poll.setDescription(pollEntity.getDescription());
        poll.setTitle(pollEntity.getTitle());
        poll.setCreateDate(pollEntity.getCreateDate());
        poll.setStartDate(InstantToDate(pollEntity.getStartDate()));
        poll.setEndDate(InstantToDate(pollEntity.getEndDate()));
        poll.setResultPublished(pollEntity.isResultPublished());
        pollEntity.getParticipants().forEach((p) -> {
            Person person = new Person(p.getUuid(), p.getJpaVersion(), p.getName());
            participants.add(person);
        });
        pollEntity.getOrganizers().forEach((p) -> {
            Person person = new Person(p.getUuid(), p.getJpaVersion(), p.getName());
            oraganizers.add(person);
        });
        pollEntity.getItemEntities().forEach((e) -> {
            Item item = new Item(e.getUuid(), e.getJpaVersion(), e.getName());
            item.setTitle(e.getTitle());
            item.setItemType(e.getItemType());
            List<Option> options = new ArrayList<>();
            e.getOptionEntities().forEach((o) -> {
                Option option = new Option(o.getUuid(), o.getJpaVersion(), o.getName());
                option.setShortName(o.getShortName());
                option.setDiscription(o.getDiscription());
                option.setOptionType(o.getOptiontype());
                options.add(option);
            });
            item.setOptions(options);
            items.add(item);
        });
        poll.setItems(items);
        poll.setParticipants(participants);
        poll.setOrganizers(oraganizers);
        return poll;
    }

    private TokenEntity getTokenByTokenString(String token) {
        return tokenAccess.getTokenObjectByTokenString(token);
    }

    @Override
    public Set<Poll> getFinishedPollsIDListByOrganizer(String organizerUUID) {
        Set<Poll> polls = new HashSet<>();
        for (PollEntity pollEntity : pollAccess.getFinishedPollsIDListByOrganizer(organizerUUID)) {
            Poll poll = new Poll(pollEntity.getUuid(), pollEntity.getJpaVersion(), pollEntity.getName());
            poll.setTitle(pollEntity.getTitle());
            polls.add(poll);
        }
        return polls;
    }
    
    

    @Override
    public Poll getPollByPollUUID(String pollUUID) {
        PollEntity pollEntity = pollAccess.getPollByPollID(pollUUID);
        Poll poll = new Poll(pollEntity.getUuid(), pollEntity.getJpaVersion(), pollEntity.getName());
        return pollEnityToPoll(pollEntity, poll);
    }

    @Override
    public void setPollStateByPollUUID(String pollUUID) {
        PollEntity pollEntity = pollAccess.getPollByPollID(pollUUID);
        pollEntity.setPollState(PollState.FINISHED);
    }

    @Override
    public Set<Poll> getPollsIDListByOrganizerAndState(String organizerUUID,PollState pollState ) {
        Set<Poll> polls = new HashSet<>();
        for (PollEntity pollEntity : pollAccess.getPollsIDListByOrganizerAndState(organizerUUID,pollState)) {
            System.out.println("poll title ccccc"+pollEntity.getTitle());
            Poll poll = new Poll(pollEntity.getUuid(), pollEntity.getJpaVersion(), pollEntity.getName());
            polls.add(pollEnityToPoll(pollEntity, poll));
        }
        return polls;
    }

    @Override
    public List<VoteResult> getPollResultByPollid(String pollUUID) {
        List<VoteResult> results = new ArrayList<>();
        for (ResultEntity resultEntity : resultAccess.getEntityByPollID(pollUUID)) {
            VoteResult voteResult = new VoteResult(resultEntity.getUuid(), resultEntity.getJpaVersion(), resultEntity.getName());
            voteResult.setPoll(resultEntity.getPoll());
            voteResult.setItem(resultEntity.getItem());
            voteResult.setOption(resultEntity.getOption());
            voteResult.setNumberOfVotes(resultEntity.getNumberOfVotes());
            results.add(voteResult);
        }

        return results;
    }

    @Override
    public List<Option> getNonPermanentOptions() {
        List<OptEntity> optEntities = optionAccess.getOptionByType(OptionType.NonPermanent);
        List<Option> result = new ArrayList<>(optEntities.size());

        optEntities.stream().map((oe) -> {
            Option option = new Option(oe.getUuid(), oe.getJpaVersion(), oe.getName());
            option.setShortName(oe.getShortName());
            option.setDiscription(oe.getDiscription());
            return option;
        }).forEachOrdered((option) -> {
            result.add(option);
        });
        return result;
    }

    @Override
    public List<String> getAllPollTitles() {
        return pollAccess.getAllPollTitles();
    }

    public Instant DateToInstant(Date date) {
        Instant t = null;
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(date);
        formatter.setTimeZone(TimeZone.getDefault());
        try {
            Date d = formatter.parse(dateString);
            t = d.toInstant();
        } catch (ParseException e) {
            System.err.println("Error parsing date to instant " + e.getMessage());
        }

        return t;
    }

    public Date InstantToDate(Instant instant) {
        Date date = Date.from(instant);
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formatter.setTimeZone(TimeZone.getDefault());
        String dateString = formatter.format(date);
        try {
            date = formatter.parse(dateString);
        } catch (ParseException e) {
            System.err.println("Error parsing date to instant " + e.getMessage());
        }

        return date;
    }

    @Override
    public boolean checkAllVotesSubmitted(String pollUUID) {
        return !(Math.toIntExact(tokenAccess.numberOfUsersDidntSubmit(pollUUID)) > 0);
    }
    
    
            
    @Override
    public Set<Poll> getStartedOrVotingPollsIDListByOrganizer(String organizerUUID) {
        Set<Poll> polls = new HashSet<>();
        for (PollEntity pollEntity : pollAccess.getStartedOrVotingPollsIDListByOrganizer(organizerUUID)) {
            Poll poll = new Poll(pollEntity.getUuid(), pollEntity.getJpaVersion(), pollEntity.getName());
            polls.add(pollEnityToPoll(pollEntity, poll));
        }
        return polls;
    }
    
    @Override
    public void extendPoll(String pollUUID,Date endDate){
        backgroundJobManager.extendPollTime(pollUUID,DateToInstant(endDate));
    }
    
    @Override
    public List<Poll> getAllPolls(){
        List<Poll> polls = new ArrayList<>();
        for (PollEntity pollEntity : pollAccess.getAllPolls()) {
            Poll poll = new Poll(pollEntity.getUuid(), pollEntity.getJpaVersion(), pollEntity.getName());
            polls.add(pollEnityToPoll(pollEntity, poll));
        }
        return polls;
    }
    
    @Override
    public void removePoll(String pollUUID){
        pollAccess.removePollByID(pollUUID);
    }
    
    @Override
    public void setPollPublished(String pollUUID,boolean published){
        pollAccess.getPollByPollID(pollUUID).setResultPublished(published);
    }
    
    
    @Override
    public Set<Poll> getPublishedPolls() {
        Set<Poll> polls = new HashSet<>();
        for (PollEntity pollEntity : pollAccess.getAllPublishedPolls()) {
            Poll poll = new Poll(pollEntity.getUuid(), pollEntity.getJpaVersion(), pollEntity.getName());
            poll.setTitle(pollEntity.getTitle());
            polls.add(poll);
        }
        return polls;
    } 
    
    @Override
    public List<Token> getAllParticipantsAndStates(String pollUUID){
        List<Token> tokens = new ArrayList<>();
        tokenAccess.getAllTokenRowsForPoll(pollUUID).forEach((tokenEntity) -> {
            tokens.add(TokenEntityToToken(tokenEntity));
        });
         return tokens;
    }
    
    Token TokenEntityToToken(TokenEntity tokenEntity){
        Token token = new Token(tokenEntity.getUuid(),tokenEntity.getJpaVersion(),tokenEntity.getName());
        token.setPersonEntity(tokenEntity.getPersonEntity());
        token.setUsed(tokenEntity.getUsed());
        return token;
    }
    
    
    @Override
    public List<Poll> getAllPollsbyTrackingAndOrganizer(String organizerUUID,boolean tracking){
        List<Poll> polls = new ArrayList<>();
        for (PollEntity pollEntity : pollAccess.getStartedOrVotingPollsIDListByOrganizer(organizerUUID)) {
            if(pollEntity.isParticipationTracking() == tracking){
                Poll poll = new Poll(pollEntity.getUuid(), pollEntity.getJpaVersion(), pollEntity.getName());
                polls.add(pollEnityToPoll(pollEntity, poll));}
        }
         return polls;
    }

    @Override
    public List<DefinedPersonList> getAllPredifinedListByPerson(String uuid) {
        List<DefinedPersonList> personList= new ArrayList<>();
            for(DefinedPersonListEntity dpe : personListAccess.getAllPredifinedListByPerson(uuid)){
                DefinedPersonList definedPersonList = new DefinedPersonList(dpe.getUuid(),dpe.getJpaVersion(),dpe.getName());
                List<Person> persons = new ArrayList<>();
                for(PersonEntity pe : dpe.getPersons()){
                    Person person = new Person(pe.getUuid(),pe.getJpaVersion(),pe.getName());
                    persons.add(person);
                }
                definedPersonList.setPersons(persons);
                definedPersonList.setTitle(dpe.getTitle());
                personList.add(definedPersonList);
            }
            return personList;       
    }
    
    @Override
    public void createDefinedPersonList(String title,String ownerId,List<Person> personsInList){
        DefinedPersonListEntity definedPersonListEntity = personListAccess.createEntity(title+ownerId);
        definedPersonListEntity.setOwnerPerson(personAccess.getByUuid(ownerId));
        personListObjectToEntity(definedPersonListEntity,title,personsInList);
    } 
    
    @Override
    public void editPersonList(DefinedPersonList definedPersonList){
        DefinedPersonListEntity definedPersonListEntity = personListAccess.getByUuid(definedPersonList.getUuid());
        definedPersonListEntity = personListObjectToEntity(definedPersonListEntity,definedPersonList.getTitle(),definedPersonList.getPersons());
        personListAccess.updatePoll(definedPersonListEntity);
    }
    
    private DefinedPersonListEntity personListObjectToEntity(DefinedPersonListEntity definedPersonListEntity,String title,List<Person> personsInList){
        definedPersonListEntity.setTitle(title);
        List<PersonEntity> persons = new ArrayList<>();
        for(Person p: personsInList){
            persons.add(personAccess.getByUuid(p.getUuid()));
        }
        definedPersonListEntity.setPersons(persons); 
        return definedPersonListEntity;
    }
    
    @Override
    public List<String> getAllPersonListTitlesByCreatorId(String creatorId){
            return personListAccess.getAllPersonListTitlesByCreatorId(creatorId);
    }
    
    
    
    
    
    
       
}
