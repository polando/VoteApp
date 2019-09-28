/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foxtrot.jee19.logic.dto;

import foxtrot.jee19.logic.OptionType;

/**
 *
 * @author ussocom
 */
public class Option extends Named {

    private static final long serialVersionUID = 6320361376028507786L;
    
    public Option(String uuid, long jpaVersion, String name) {
        super(uuid, jpaVersion, name);
    }
    
    private String shortName;
    
    private String discription;
    
    private OptionType optionType;

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public OptionType getOptionType() {
        return optionType;
    }

    public void setOptionType(OptionType optionType) {
        this.optionType = optionType;
    }

    
    
    
    
    
}
