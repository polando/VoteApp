/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foxtrot.jee19.web;

import foxtrot.jee19.logic.PollLogic;
import foxtrot.jee19.logic.dto.DefinedPersonList;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import foxtrot.jee19.logic.dto.Person;
import javax.ejb.EJB;
import org.primefaces.model.DualListModel;

/**
 *
 * @author ussocom
 */
@ViewScoped
@Named
public class changeParticipantListBean implements Serializable {
    
    private static final long serialVersionUID = -3020605773939912966L;
    
    @EJB
    private PollLogic pollLogic;
            
    @Inject
    private pollBean pollbean;
    
    private DefinedPersonList selectedDefinedPersonList;
    
    private DualListModel<Person> peronsDualList;
    
    private List<Person> target;
    
    private List<Person> source;
    
    List<Person> allParticipants;
    
    @PostConstruct
    public void init() {
         allParticipants = pollbean.getPersons();
         source = new ArrayList<>(allParticipants);
         target = new ArrayList<>();
         peronsDualList = new DualListModel<>(source,target);
    }
    
    public void setValues(DefinedPersonList definedPersonList){
        source = pollbean.getPersons();
        selectedDefinedPersonList = definedPersonList;
        peronsDualList.setTarget(selectedDefinedPersonList.getPersons());
        source.removeAll(new ArrayList<>(selectedDefinedPersonList.getPersons()));
        peronsDualList.setSource(source);
    }
    
    public DualListModel<Person> getPeronsDualList() {
        return peronsDualList;
    }
    
    public void setPeronsDualList(DualListModel<Person> peronsDualList) {
        this.peronsDualList = peronsDualList;
    }

    public DefinedPersonList getSelectedDefinedPersonList() {
        return selectedDefinedPersonList;
    }

    public void setSelectedDefinedPersonList(DefinedPersonList selectedDefinedPersonList) {
        this.selectedDefinedPersonList = selectedDefinedPersonList;
    }
    
    public DefinedPersonList editList(){
        selectedDefinedPersonList.setPersons(peronsDualList.getTarget());
        pollLogic.editPersonList(selectedDefinedPersonList);
        return selectedDefinedPersonList;
    }

    public List<Person> getAllParticipants() {
        return allParticipants;
    }
    
    
    
    
}
