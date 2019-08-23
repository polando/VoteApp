/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee19.logic.impl;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import jee19.entities.ItemEntity;
import jee19.entities.PersonEntity;
import jee19.entities.PollEntity;
import jee19.entities.PollStateEntity;
import jee19.entities.PollTypeEntity;
import jee19.logic.PollLogic;
import jee19.logic.dao.ItemAccess;
import jee19.logic.dao.PersonAccess;
import jee19.logic.dao.PollAccess;
import jee19.logic.dao.PollStateAccess;
import jee19.logic.dao.PollTypeAccess;
import jee19.logic.dto.Item;
import jee19.logic.dto.Person;
import jee19.logic.dto.Poll;
import jee19.logic.dto.PollState;
import jee19.logic.dto.PollType;

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
    private PollStateAccess pollStateAccess;
    
    @EJB
    private PollAccess pollAccess;
   
        
    @EJB
    private ItemAccess itemAccess;
    

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
    
    @Override
    public List<PollState> getAllPollStates() {
            List<PollStateEntity> l = pollStateAccess.getPollStateList();
            List<PollState> result = new ArrayList<>(l.size());
        for (PollStateEntity pe : l) {
            PollState p = new PollState(pe.getUuid(), pe.getJpaVersion(), pe.getName());
            p.setState(pe.getPollState());
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
    public Poll createPoll(String title, String description,PollType polltype,PollState pollstate, Instant endDateInstant, Instant createDateInstant,Instant startDateInstant,List<Person> participants) {
        PollEntity pollEntity = pollAccess.createEntity(title);
        PollTypeEntity pollTypeEntity = pollTypeAccess.getByUuid(polltype.getUuid());
       // PollStateEntity  pollStateEntity= pollStateAccess.getByUuid(pollstate.getUuid());
        Set<PersonEntity> participantEntity = new HashSet<>();
        for(Person p: participants){
            participantEntity.add(personAccess.getByUuid(p.getUuid()));
        }
        pollEntity.setPollTypeEntity(pollTypeEntity);
        pollEntity.setTitle(title);
        pollEntity.setDescription(description);
        pollEntity.setParticipants(participantEntity);
    // pollEntity.setCreateDate(startDateInstant);
    return new Poll(pollEntity.getUuid(), pollEntity.getJpaVersion(), pollEntity.getName());
    }
    
    
    
    
    
    
}
