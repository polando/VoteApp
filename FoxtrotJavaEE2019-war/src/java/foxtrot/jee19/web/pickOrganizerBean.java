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
public class pickOrganizerBean implements Serializable {

    private static final long serialVersionUID = -4283759819209517087L;

    @Inject
    private pollBean pollbean;

    @Inject
    private LoginBean loginBean;

    private DualListModel<Person> model;

    private List<Person> fullList;


    @PostConstruct
    public void init() {

        Person loggedInUser = loginBean.getUser();
        fullList = pollbean.getPersons();
        //  allParticipants.removeIf(e -> e.getUuid().equals(loggedInUser.getUuid()));
        model = new DualListModel<>(new ArrayList<>(fullList), new ArrayList<>());
            
            model = new DualListModel<>(
            new ArrayList<>(fullList),
            new ArrayList<>()
        );
    }
    
    
    public List<Person> getFullList() {
        return fullList;
    }

    public DualListModel<Person> getModel() {
        return model;
    }

    public void setModel(DualListModel<Person> model) {
        this.model = model;
    }

}
