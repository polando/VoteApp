/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee19.web;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import jee19.logic.ItemType;
import jee19.logic.PollLogic;
import jee19.logic.dto.Item;
import jee19.logic.dto.Option;

/**
 *
 * @author ussocom
 */
@ViewScoped
@Named
public class itemBean implements Serializable {

    private static final long serialVersionUID = 4955334646126098904L;
    
    @EJB
    private PollLogic polllogic;
    
    private List<Option> options;
    
    private String Title;
    
    private ItemType itemType;
    
    @PostConstruct
    void init(){
        itemType = ItemType.YesNo;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }
    
    public boolean isYesNo(){
        return itemType.equals(ItemType.YesNo);
    }
    
    public Item createItem(){
        return polllogic.createItem(Title, itemType, options);
    }
    
    
}
