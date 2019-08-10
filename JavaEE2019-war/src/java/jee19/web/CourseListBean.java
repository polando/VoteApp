package jee19.web;

import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import jee19.logic.TeamBusinessLogic;
import jee19.logic.dto.Course;

/**
 * @author Dr. Volker Riediger <riediger@uni-koblenz.de>
 */
@RequestScoped
@Named
public class CourseListBean {

    @EJB
    private TeamBusinessLogic teamBusinessLogic;

    private List<Course> courses;

    public List<Course> getCourses() {
        if (courses == null) {
            courses = teamBusinessLogic.getCourseList();
        }
        return courses;
    }
}
