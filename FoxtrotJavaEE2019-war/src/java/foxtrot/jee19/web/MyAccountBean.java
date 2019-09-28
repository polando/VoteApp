package foxtrot.jee19.web;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import foxtrot.jee19.logic.dto.Person;
import foxtrot.jee19.web.utilities.MessageUtilities;
import foxtrot.jee19.logic.PersonLogic;

/**
 * @author Dr. Volker Riediger <riediger@uni-koblenz.de>
 */
@ViewScoped
@Named
public class MyAccountBean implements Serializable {

    private static final long serialVersionUID = -7619861512087870920L;

    @EJB
    private PersonLogic teamBusinessLogic;

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
