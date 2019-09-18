/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee19.logic.dto;

import java.util.List;
import jee19.logic.ItemType;

/**
 *
 * @author ussocom
 */
public class Item extends Named {
    
    private static final long serialVersionUID = 303023540142535077L;

    public Item(String uuid, long jpaVersion, String name) {
        super(uuid, jpaVersion, name);
    }
    

    private List<Option> options;
    
    private String Title;
    
    private ItemType itemType;
    
    private List<Option> chosenOptions;
    
    private Option chosenOption;


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
    
    public boolean isNOfM(){
        return itemType.equals(ItemType.NOfM);
    }
       
    public void resetOptions(){
        options.clear();
    }

    public List<Option> getChosenOptions() {
        return chosenOptions;
    }

    public void setChosenOptions(List<Option> chosenOptions) {
        this.chosenOptions = chosenOptions;
    }

    public Option getChosenOption() {
        return chosenOption;
    }

    public void setChosenOption(Option chosenOption) {
        this.chosenOption = chosenOption;
    }

    
    

    
    
    
    
}
