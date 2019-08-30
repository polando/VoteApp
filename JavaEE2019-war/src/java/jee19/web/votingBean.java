/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee19.web;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import jee19.logic.PollLogic;
import jee19.logic.dto.Item;

/**
 *
 * @author ussocom
 */
@ViewScoped
@Named
public class votingBean implements Serializable {
    
    private static final long serialVersionUID = -7434000390609622052L;
    
    private List<Item> voteItems;
    
    private Item chosenItem;
    
    @EJB
    private PollLogic polllogic;
    
    
   /* public List<Item> getListOfItemsOfaPoll(){
        
    }*/
    
}
