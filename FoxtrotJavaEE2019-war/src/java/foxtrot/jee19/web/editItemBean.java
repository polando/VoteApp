/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foxtrot.jee19.web;

import foxtrot.jee19.logic.dto.Item;
import java.io.Serializable;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author ussocom
 */
@ViewScoped
@Named
public class editItemBean implements Serializable  {
    
    private static final long serialVersionUID = -2236508731827880306L;
    
    private Item item;

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
    
    
    
}
