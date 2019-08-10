package jee19.web;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import jee19.logic.TeamBusinessLogic;

/**
 * @author Dr. Volker Riediger <riediger@uni-koblenz.de>
 */
@RequestScoped
@Named("adminBean")
public class AdminBean implements Serializable {

    private static final long serialVersionUID = -8969157250811794497L;

    @EJB
    private TeamBusinessLogic teamBusinessLogic;

    public void createTestData() {
        teamBusinessLogic.createTestData();
    }
}
