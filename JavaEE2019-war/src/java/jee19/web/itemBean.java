/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee19.web;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import jee19.logic.PollLogic;

/**
 *
 * @author ussocom
 */

@ViewScoped
@Named
public class itemBean implements Serializable{
    
    private static final long serialVersionUID = 3193800306634223242L;
    
    @EJB
    private PollLogic polllogic;
    
    private String item;

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }
    
    
    public void createItem(){
        polllogic.createPollItem(item);
    }
    
}
