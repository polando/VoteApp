/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foxtrot.jee19.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.RequestScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import foxtrot.jee19.logic.dto.Person;
import org.primefaces.model.DualListModel;

/**
 *
 * @author ussocom
 */
@ViewScoped
@Named
public class changeParticipantbean implements Serializable {
    
    private static final long serialVersionUID = -3020605773939912966L;
    
    @Inject
    private pollBean pollbean;
    
    @Inject 
    private changePollBean changepoll;
    
    private DualListModel<Person> peronsDualList;
    
    private List<Person> target;
    
    List<Person> allParticipants;
    
    @PostConstruct
    public void init() {
         allParticipants = pollbean.getPersons();
         target = new ArrayList<>();
         peronsDualList = new DualListModel<>(allParticipants, changepoll.getPoll().getParticipants());
    }
    
    public DualListModel<Person> getPeronsDualList() {
        return peronsDualList;
    }
    
    public void setPeronsDualList(DualListModel<Person> peronsDualList) {
        this.peronsDualList = peronsDualList;
    }
    
    
}
