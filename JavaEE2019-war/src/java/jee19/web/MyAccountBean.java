package jee19.web;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import jee19.logic.TeamBusinessLogic;
import jee19.logic.dto.Person;
import jee19.web.utilities.MessageUtilities;

/**
 * @author Dr. Volker Riediger <riediger@uni-koblenz.de>
 */
@ViewScoped
@Named
public class MyAccountBean implements Serializable {

    private static final long serialVersionUID = -7619861512087870920L;

    @EJB
    private TeamBusinessLogic teamBusinessLogic;

    @Inject
    private LoginBean loginBean;

    private Person person;

    @PostConstruct
    public void init() {
        person = loginBean.getUser();
    }

    public Person getPerson() {
        return person;
    }

    public void storePersonDetails(ActionEvent e) {
        teamBusinessLogic.storePersonDetails(person);
        MessageUtilities.addMessage(e.getSource(), "saveSuccessMessage");
    }
}
