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
public class changeOrganizerBean implements Serializable {

    private static final long serialVersionUID = 6189522864552479871L;

    @Inject
    private pollBean pollbean;

    @Inject
    private changePollBean changepoll;
    
    @Inject
    private LoginBean loginBean;

    private DualListModel<Person> peronsDualList;

    private List<Person> target;
    
    List<Person> allParticipants;

    @PostConstruct
    public void init() {  
        Person loggedInUser = loginBean.getUser();
        allParticipants = pollbean.getPersons();
        allParticipants.removeIf(e -> e.getUuid().equals(loggedInUser.getUuid()));
        target = changepoll.getPoll().getOrganizers();
        target.removeIf(e -> e.getUuid().equals(loggedInUser.getUuid()));
        peronsDualList = new DualListModel<>(allParticipants,target);
    }

    public DualListModel<Person> getPeronsDualList() {
        return peronsDualList;
    }

    public void setPeronsDualList(DualListModel<Person> peronsDualList) {
        this.peronsDualList = peronsDualList;
    }

    public List<Person> getAllParticipants() {
        return allParticipants;
    }
    
    

}
