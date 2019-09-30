/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foxtrot.jee19.web;

import foxtrot.jee19.logic.PollLogic;
import foxtrot.jee19.logic.dto.Person;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author ussocom
 */

@ViewScoped
@Named
public class createPersonListBean implements Serializable {

    private static final long serialVersionUID = 7032196517464377293L;
    
    @EJB
    private PollLogic polllogic;
    
    @Inject
    private LoginBean loginBean;
    
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void createListParticipants(List<Person> personsInList) {
        polllogic.createDefinedPersonList(title, loginBean.getUser().getUuid(), personsInList);
    }
    
}
