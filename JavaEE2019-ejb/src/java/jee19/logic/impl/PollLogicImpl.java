/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee19.logic.impl;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
import javax.annotation.PostConstruct;
import jee19.entities.OptEntity;

//import jee19.entities.TokenEntity;
import jee19.logic.PollLogic;
import jee19.logic.PollState;
import jee19.logic.ItemType;
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
            optionEntities.addAll(optionAccess.getPermanentOptions());
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
            option.setPermanentOption(o.isPermanentOption());
            ops.add(option);
        });
        item.setOptions(ops);

        return item;
    }

    @Override
    public Poll createPoll(String title, String description, ItemType itemType, Instant endDateInstant, Instant createDateInstant, Instant startDateInstant, List<Person> participants, List<Person> organizers, List<Item> items) {
        PollEntity pollEntity = pollAccess.createEntity(title);
        Set<TokenEntity> tokenEntity = new HashSet<>();
        Set<PersonEntity> organizerEntity = new HashSet<>();
        List<ItemEntity> itemEntities = new ArrayList<>();

        organizers.forEach((p) -> {
            organizerEntity.add(personAccess.getByUuid(p.getUuid()));
        });

        pollEntity.setTitle(title);
        pollEntity.setDescription(description);
        pollEntity.setOrganizers(organizerEntity);
        pollEntity.setStartDate(startDateInstant);
        pollEntity.setEndDate(endDateInstant);
        pollEntity.setCreateDate(createDateInstant);

        participants.forEach((p) -> {
            tokenEntity.add(tokenAccess.getByUuid(createToken(p.getName() + pollEntity.getName(), p, pollEntity).getUuid()));
        });

        pollEntity.setTokens(tokenEntity);

        items.forEach((i) -> {
            System.out.println("I exist"+i);
            ItemEntity itemEntity = itemAccess.getByUuid(i.getUuid());
            System.out.println("I exist 2"+i);
            itemEntity.setTitle(i.getTitle());
            System.out.println("I exist 3"+i);
            itemEntity.setItemType(i.getItemType());
            System.out.println("I exist 4"+i);
            List<OptEntity> optionEntities = new ArrayList<>();
            if (i.getItemType().equals(ItemType.YesNo)) {
                optionEntities.addAll(optionAccess.getPermanentOptions());
            } else {
                i.getOptions().forEach((option) -> {
                    optionEntities.add(optionAccess.getByUuid(option.getUuid()));
                });
            }
            System.out.println("I exist 5"+i);
            itemEntity.setOptionEntities(optionEntities);
            System.out.println("I exist 6"+i);
            itemEntities.add(itemEntity);
        });

        pollEntity.setItemEntities(itemEntities);

        items.forEach((i) -> {
            i.getOptions().forEach((o) -> {
                createResultEntity(pollEntity.getName() + i.getName(), pollEntity.getUuid(), i.getUuid(), o.getUuid());
            });
        });

        pollEntity.setPollState(PollState.STARTED);

        backgroundJobManager.seTimerForPoll(pollEntity.getUuid(), startDateInstant, endDateInstant);

        return new Poll(pollEntity.getUuid(), pollEntity.getJpaVersion(), pollEntity.getName());
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
    public void addToVotes(String token, String pollUUID, String ItemUUID,String OptionUUID) {
        ResultEntity resultEntity = resultAccess.getEntityByPollAndItemIDAndOptID(pollUUID, ItemUUID, OptionUUID);
        resultEntity.setNumberOfVotes(resultEntity.getNumberOfVotes() + 1);
        TokenEntity tokenEntity = tokenAccess.getTokenObjectByTokenString(token);
        tokenEntity.setUsed(Boolean.TRUE);
    }

    private Token createToken(String name, Person person, PollEntity pollEntity) {
        TokenEntity tokenEntity = tokenAccess.createEntity(name);
        PersonEntity personEntity = personAccess.getByUuid(person.getUuid());
        tokenEntity.setPersonEntity(personEntity);
        tokenEntity.setPollEntity(pollEntity);
        tokenEntity.setToken(hashAString(name));
        tokenEntity.setUsed(false);
        return new Token(tokenEntity.getUuid(), tokenEntity.getJpaVersion(), tokenEntity.getName());
    }

    @Override
    public Option createOption(String shortName, String disc, boolean permanentOption) {
        OptEntity optEntity = optionAccess.createEntity(shortName);
        optEntity.setShortName(shortName);
        optEntity.setDiscription(disc);
        optEntity.setPermanentOption(permanentOption);
        return new Option(optEntity.getUuid(), optEntity.getJpaVersion(), optEntity.getName());
    }

    @Override
    public boolean tokenExistAndNotUsed(String token) {
        TokenEntity tokenEntity = getTokenByTokenString(token);
        if(tokenEntity != null)
            return !(tokenEntity.getUsed());
        else
            return false;
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
        Set<Item> items = new HashSet<>();
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
                option.setPermanentOption(o.isPermanentOption());
                options.add(option);
            });
            item.setOptions(options);
            items.add(item);
        });

        poll.setItemEntities(items);
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
        List<OptEntity> optEntities = optionAccess.getNonPermanentOptions();
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

    private List<OptEntity> getPermanentPollItems() {
        return optionAccess.getPermanentOptions();
    }

    @Override
    public List<String> getAllPollTitles() {
        return pollAccess.getAllPollTitles();
    }

    public void CreatetestData() {

    }

}
