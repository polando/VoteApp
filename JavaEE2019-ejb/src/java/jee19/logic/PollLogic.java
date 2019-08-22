/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee19.logic;

import java.util.List;
import javax.ejb.Remote;
import jee19.logic.dto.Item;
import jee19.logic.dto.Person;
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
    
    public Item createPollItem(String name);
    
    
    
}
