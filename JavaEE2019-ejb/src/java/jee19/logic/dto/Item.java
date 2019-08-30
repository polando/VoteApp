/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee19.logic.dto;

/**
 *
 * @author ussocom
 */
public class Item extends Named {
    
    private static final long serialVersionUID = 303023540142535077L;

    public Item(String uuid, long jpaVersion, String name) {
        super(uuid, jpaVersion, name);
    }
    
     private String Item;


    public String getItem() {
        return Item;
    }

    public void setItem(String Item) {
        this.Item = Item;
    }
     
     
    
    
}
