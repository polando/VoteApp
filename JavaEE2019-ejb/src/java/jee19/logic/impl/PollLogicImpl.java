/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee19.logic.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import jee19.entities.ItemEntity;
import jee19.entities.PersonEntity;
import jee19.entities.PollTypeEntity;
import jee19.logic.PollLogic;
import jee19.logic.dao.ItemAccess;
import jee19.logic.dao.PersonAccess;
import jee19.logic.dao.PollTypeAccess;
import jee19.logic.dto.Item;
import jee19.logic.dto.Person;
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
    
    
    
    
    
    
}
