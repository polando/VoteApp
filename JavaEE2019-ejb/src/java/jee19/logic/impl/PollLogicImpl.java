/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee19.logic.impl;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
import jee19.entities.ItemEntity;
import jee19.entities.PersonEntity;
import jee19.entities.PollEntity;
import jee19.entities.ResultEntity;
import jee19.entities.TokenEntity;
import java.time.Instant;
import java.util.Date;
import java.util.TimeZone;
import javax.annotation.PostConstruct;
import jee19.entities.OptEntity;

//import jee19.entities.TokenEntity;
import jee19.logic.PollLogic;
import jee19.logic.PollState;
import jee19.logic.ItemType;
import jee19.logic.OptionType;
import jee19.logic.dao.ItemAccess;
import jee19.logic.dao.OptionAccess;
import jee19.logic.dao.PersonAccess;
import jee19.logic.dao.PollAccess;
import jee19.logic.dao.ResultAccess;
import jee19.logic.dao.TokenAccess;
//import jee19.logic.dao.TokenAccess;
import jee19.logic.dto.Item;
import jee19.logic.dto.Option;
import jee19.logic.dto.Person;
import jee19.logic.dto.Poll;
import jee19.logic.dto.Token;
import jee19.logic.dto.VoteResult;
import jee19.utilities.BackgroundJobManager;

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
    public Poll createPoll(String title, String description, Date endDate, Instant createDateInstant, Date startDate, List<Person> participants, List<Person> organizers, List<Item> items) {
        PollEntity pollEntity = pollAccess.createEntity(title);
        setPollProperties(pollEntity, title, description, endDate, createDateInstant, startDate, participants, organizers, items);
        return new Poll(pollEntity.getUuid(), pollEntity.getJpaVersion(), pollEntity.getName());
    }

    @Override
    public Poll editPoll(Poll poll) {
        PollEntity pollEntity = pollAccess.getByUuid(poll.getUuid());
        setPollProperties(pollEntity, poll.getTitle(), poll.getDescription(), poll.getEndDate(), poll.getCreateDate(), poll.getStartDate(), poll.getParticipants(), poll.getOrganizers(), poll.getItems());
        return new Poll(pollEntity.getUuid(), pollEntity.getJpaVersion(), pollEntity.getName());
    }

    private void setPollProperties(PollEntity pollEntity, String title, String description, Date endDate, Instant createDateInstant, Date startDate, List<Person> participants, List<Person> organizers, List<Item> items) {
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

        items.forEach((i) -> {
            ItemEntity itemEntity = itemAccess.getByUuid(i.getUuid());
            itemEntity.setTitle(i.getTitle());
            itemEntity.setItemType(i.getItemType());
            List<OptEntity> optionEntities = new ArrayList<>();
            /*  if (i.getItemType().equals(ItemType.YesNo)) {
                optionEntities.addAll(optionAccess.getOptionByType(OptionType.YesNo));
            } else {
                i.getOptions().forEach((option) -> {
                    optionEntities.add(optionAccess.getByUuid(option.getUuid()));
                });
            }*/
            i.getOptions().forEach((option) -> {
                optionEntities.add(optionAccess.getByUuid(option.getUuid()));
            });
            itemEntity.setOptionEntities(optionEntities);
            itemEntities.add(itemEntity);
        });

        pollEntity.setItemEntities(itemEntities);

        pollEntity.setPollState(PollState.PREPARED);

    }

    @Override
    public void startPoll(String pollUUID) {

        Set<TokenEntity> tokenEntity = new HashSet<>();

        PollEntity pollEntity = pollAccess.getByUuid(pollUUID);

        pollEntity.getParticipants().forEach((p) -> {
            tokenEntity.add(tokenAccess.getByUuid(createToken(p.getName() + pollEntity.getName(), p, pollEntity).getUuid()));
        });
        
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
        tokenEntity.setToken(hashAString(name));
        tokenEntity.setUsed(false);
        return new Token(tokenEntity.getUuid(), tokenEntity.getJpaVersion(), tokenEntity.getName());
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
        System.out.println(pollEntity.getTitle());
        List<Item> items = new ArrayList<>();
        List<Option> options = new ArrayList<>();
        Poll poll = new Poll(pollEntity.getUuid(), pollEntity.getJpaVersion(), pollEntity.getName());
        poll.setDescription(pollEntity.getDescription());
        poll.setTitle(pollEntity.getTitle());
        pollEntity.getItemEntities().forEach((e) -> {
            Item item = new Item(e.getUuid(), e.getJpaVersion(), e.getName());
            item.setTitle(e.getTitle());
            item.setItemType(e.getItemType());
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
        return poll;
    }

    private Poll pollEnityToPoll(PollEntity pollEntity, Poll poll) {
        List<Item> items = new ArrayList<>();
        List<Option> options = new ArrayList<>();
        List<Person> participants = new ArrayList<>();
        List<Person> oraganizers = new ArrayList<>();
        poll.setDescription(pollEntity.getDescription());
        poll.setTitle(pollEntity.getTitle());
        poll.setCreateDate(pollEntity.getCreateDate());
        poll.setStartDate(InstantToDate(pollEntity.getStartDate()));
        poll.setEndDate(InstantToDate(pollEntity.getEndDate()));
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
    public Set<Poll> getPreparedPollsIDListByOrganizer(String organizerUUID) {
        Set<Poll> polls = new HashSet<>();
        for (PollEntity pollEntity : pollAccess.getPreparedPollsIDListByOrganizer(organizerUUID)) {
            Poll poll = new Poll(pollEntity.getUuid(), pollEntity.getJpaVersion(), pollEntity.getName());
            //poll.setTitle(pollEntity.getTitle());
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
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
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
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
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

}
