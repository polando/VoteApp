/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee19.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import jee19.logic.dto.Person;
import org.primefaces.model.DualListModel;

/**
 *
 * @author ussocom
 */
@ViewScoped
@Named
public class pickParticipantBean implements Serializable {

    private static final long serialVersionUID = -7098961553722344473L;
    
    @Inject
    private pollBean pollbean;

    
    private DualListModel<Person> peronsDualList;
    
    
    private List<Person> target;
    
        @PostConstruct
        public void init() {
        List<Person> allParticipants = pollbean.getPersons();
        if(target == null){
        target = new ArrayList<>();
        }
        
        peronsDualList = new DualListModel<>(allParticipants, target);
         

        }

    public DualListModel<Person> getPeronsDualList() {
        return peronsDualList;
    }

    public void setPeronsDualList(DualListModel<Person> peronsDualList) {
        this.peronsDualList = peronsDualList;
    }
     
        
}
