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
import jee19.entities.PollTypeEntity;
import jee19.entities.ResultEntity;
import jee19.entities.TokenEntity;
import java.time.Instant;
import java.util.HashMap;
import jee19.entities.NamedEntity;

//import jee19.entities.TokenEntity;
import jee19.logic.PollLogic;
import jee19.logic.PollState;
import jee19.logic.dao.ItemAccess;
import jee19.logic.dao.PersonAccess;
import jee19.logic.dao.PollAccess;
import jee19.logic.dao.PollTypeAccess;
import jee19.logic.dao.ResultAccess;
import jee19.logic.dao.TokenAccess;
//import jee19.logic.dao.TokenAccess;
import jee19.logic.dto.Item;
import jee19.logic.dto.Person;
import jee19.logic.dto.Poll;
import jee19.logic.dto.PollType;
import jee19.logic.dto.Token;
import jee19.utilities.BackgroundJobManager;
import jee19.utilities.MailBean;
import jee19.utilities.Notification;

/**
 *
 * @author ussocom
 */

@Stateless
public class PollLogicImpl implements PollLogic{
    
    @EJB
    private PersonAccess personAccess;
    
    @EJB
    private PollTypeAccess pollTypeAccess;
    
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
    private Notification notification;

    

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
    public List<PollType> getAllPollTypes() {
        List<PollTypeEntity> l = pollTypeAccess.getPollTypeList();
                List<PollType> result = new ArrayList<>(l.size());
        for (PollTypeEntity pe : l) {
            PollType p = new PollType(pe.getUuid(), pe.getJpaVersion(), pe.getName());
            p.setPtype(pe.getPollType());
            result.add(p);
        }
        return result;
    }
    
 /*   @Override
    public List<PollState> getAllPollStates() {
            List<PollStateEntity> l = pollStateAccess.getPollStateList();
            List<PollState> result = new ArrayList<>(l.size());
        for (PollStateEntity pe : l) {
            PollState p = new PollState(pe.getUuid(), pe.getJpaVersion(), pe.getName());
            p.setState(pe.getPollState());
            result.add(p);
        }
        return result;
    }*/
    

    @Override
    public List<Item> getAllPollItems() {
        List<ItemEntity> l = itemAccess.getPollItemsList();
        List<Item> result = new ArrayList<>(l.size());
        
        for (ItemEntity pe : l) {
            Item p = new Item(pe.getUuid(), pe.getJpaVersion(), pe.getName());
            p.setItem(pe.getItem());
            result.add(p);
        }
        return result;
        
    }
    
    @Override
    public Item createPollItem(String name) {
        ItemEntity p = itemAccess.createEntity(name);
        p.setItem(name);
        return new Item(p.getUuid(), p.getJpaVersion(), p.getName());
    }

    @Override
    public Poll createPoll(
            String title,
            String description,
            PollType polltype,
            Instant endDateInstant,
            Instant createDateInstant,
            Instant startDateInstant,
            List<Person> participants,
            List<Person> organizers,
            List<Item> items
    ) {
        PollEntity pollEntity = pollAccess.createEntity(title);
        PollTypeEntity pollTypeEntity = pollTypeAccess.getByUuid(polltype.getUuid());
        Set<TokenEntity> tokenEntity = new HashSet<>();
        Set<PersonEntity> organizerEntity = new HashSet<>();
        Set<ItemEntity> itemEntity = new HashSet<>();

         for(Person p: organizers){
            organizerEntity.add(personAccess.getByUuid(p.getUuid()));
        }
        pollEntity.setPollTypeEntity(pollTypeEntity);
        pollEntity.setTitle(title);
        pollEntity.setDescription(description);
        pollEntity.setOrganizers(organizerEntity);
        pollEntity.setStartDate(startDateInstant);
        pollEntity.setEndDate(endDateInstant);
        pollEntity.setCreateDate(createDateInstant);

        HashMap<String, String> emailInfo = new HashMap<String, String>();
        emailInfo = getPollInfo(pollEntity, participants);

        for(Person p: participants){
            Token token = createToken(p.getName()+pollEntity.getName(),p,pollEntity);
            tokenEntity.add(tokenAccess.getByUuid(token.getUuid()));
            emailInfo.put("token", token.getToken());
            emailInfo.put("email", p.getName());
            notification.sendNotificationToParticioants(emailInfo);
        }
        pollEntity.setTokens(tokenEntity);
        
        items.forEach((i) -> {
            itemEntity.add(itemAccess.getByUuid(i.getUuid()));
        });

        items.forEach((i) -> {
            createResultEntity(pollEntity.getName()+i.getName(),pollEntity.getUuid(),i.getUuid());
        });
        
        pollEntity.setItemEntities(itemEntity);
        
        pollEntity.setPollState(PollState.STARTED);
        
        backgroundJobManager.seTimerForPoll(pollEntity.getUuid(), startDateInstant, endDateInstant);

        
    return new Poll(pollEntity.getUuid(), pollEntity.getJpaVersion(), pollEntity.getName());
    }
    
    private HashMap getPollInfo(PollEntity pollEntity, List<Person> participants)
    {
        HashMap<String, String> emailInfo = new HashMap<String, String>();
        emailInfo.put("title", pollEntity.getTitle());
        emailInfo.put("start_date", pollEntity.getStartDate().toString());
        emailInfo.put("end_date", pollEntity.getEndDate().toString());
        emailInfo.put("number_of_participants", Integer.toString(participants.size()));
        return emailInfo;
    }

    private ResultEntity createResultEntity(String name,String pollUUID,String ItemUUID){
        ResultEntity resultEntity = resultAccess.createEntity(name);
        resultEntity.setItem(itemAccess.getByUuid(ItemUUID));
        resultEntity.setPoll(pollAccess.getByUuid(pollUUID));
        resultEntity.setNumberOfVotes(0);
        return resultEntity;  
    }
    
    
    @Override
    public void addToVotes(String token,String pollUUID,String ItemUUID){
        ResultEntity resultEntity = resultAccess.getEntityByPollAndItemID(pollUUID, ItemUUID);
        resultEntity.setNumberOfVotes(resultEntity.getNumberOfVotes()+1);
        TokenEntity tokenEntity = tokenAccess.getTokenObjectByTokenString(token);
        tokenEntity.setUsed(Boolean.TRUE);
    }
    
    private Token createToken(String name,Person person,PollEntity pollEntity){
        TokenEntity tokenEntity = tokenAccess.createEntity(name);
        PersonEntity personEntity = personAccess.getByUuid(person.getUuid());
        tokenEntity.setPersonEntity(personEntity); 
        tokenEntity.setPollEntity(pollEntity);
        tokenEntity.setToken(hashAString(name));
        tokenEntity.setUsed(false);

        Token token   = new Token(tokenEntity.getUuid(), tokenEntity.getJpaVersion(), tokenEntity.getName());
        token.setToken(tokenEntity.getToken());
        return token;

    }
    
    @Override
    public boolean isTokenUsed(String token){
       TokenEntity tokenEntity = getTokenByTokenString(token);
       return   tokenEntity.getUsed();
    }
    
    private String hashAString(String input){
        String hashedString = null;
        try{
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] hashInBytes = md.digest(input.getBytes(StandardCharsets.UTF_8));

        StringBuilder sb = new StringBuilder();
        for (byte b : hashInBytes) {
            sb.append(String.format("%02x", b));
        }
        hashedString = sb.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return hashedString;
    }
    
    @Override
    public boolean checkToken(String personUUID,String token){
          Object result = tokenAccess.getTokenObjectByTokenString(token);
          PersonEntity personEntity = personAccess.getByUuid(personUUID);
          TokenEntity tokenEntity = null;
          if(result == null)
              return false;
          else
          {
              tokenEntity = (TokenEntity)result;
              System.out.println("token is  "+tokenEntity.getToken());
          }
        return tokenEntity.getPersonEntity().equals(personEntity);  
    }
    
    @Override
    public Poll getPollByToken(String token){
          TokenEntity tokenEntity = tokenAccess.getTokenObjectByTokenString(token);
          PollEntity pollEntity = pollAccess.getByUuid(tokenEntity.getPollEntity().getUuid());
          System.out.println(pollEntity.getTitle());
          Set<Item> items = new HashSet<>();
          Poll poll = new Poll(pollEntity.getUuid(), pollEntity.getJpaVersion(), pollEntity.getName());
                poll.setDescription(pollEntity.getDescription());
                poll.setTitle(pollEntity.getTitle());
                  pollEntity.getItemEntities().forEach((e) -> {
                      Item item = new Item(e.getUuid(),e.getJpaVersion(),e.getName());
                      item.setItem(e.getItem());
                      items.add(item);
                  });
                poll.setItemEntities(items);
          return poll;
    }
    
    private TokenEntity getTokenByTokenString(String token){
        return  tokenAccess.getTokenObjectByTokenString(token);
    }
}
