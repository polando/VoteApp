package jee19.web;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import jee19.logic.TeamBusinessLogic;
import jee19.logic.Term;
import jee19.logic.dto.Person;

/**
 * @author Dr. Volker Riediger <riediger@uni-koblenz.de>
 */
@ViewScoped
@Named
public class CreateCourseBean implements Serializable {

    private static final long serialVersionUID = 3558874969400562689L;

    @EJB
    private TeamBusinessLogic teamBusinessLogic;

    @Inject
    private LoginBean loginBean;

    private Term term;
    private int year;
    private String name;

    @PostConstruct
    public void init() {
        System.err.println("#### CreateCourseBean ####");
    }

    public Term getTerm() {
        return term;
    }

    public void setTerm(Term term) {
        this.term = term;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void createCourse() {
        Person loggedInUser = loginBean.getUser();

        System.err.println("CREATE COURSE " + term + ", " + year + ", " + name + ", " + loggedInUser.getUuid());
        teamBusinessLogic.createCourse(term, year, name, loggedInUser.getUuid());
    }
}
