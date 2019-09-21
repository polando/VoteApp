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
import jee19.logic.ItemType;
import jee19.logic.OptionType;
import jee19.logic.PollLogic;

/**
 *
 * @author ussocom
 */
@ViewScoped
@Named
public class optionBean implements Serializable {

    private static final long serialVersionUID = 3193800306634223242L;

    @EJB
    private PollLogic polllogic;

    private String shortName;

    private String discription;

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

    public void createOption() {
        polllogic.createOption(shortName, discription, OptionType.NonPermanent);
    }

}
