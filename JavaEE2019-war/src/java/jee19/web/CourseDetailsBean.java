package jee19.web;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import jee19.logic.TeamBusinessLogic;
import jee19.logic.dto.CourseDetails;

/**
 * @author Dr. Volker Riediger <riediger@uni-koblenz.de>
 */
@RequestScoped
@Named
public class CourseDetailsBean implements Serializable {

    private static final long serialVersionUID = 3928063306404100833L;

    @EJB
    private TeamBusinessLogic teamBusinessLogic;

    private String uuid;

    private CourseDetails course;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public CourseDetails getCourse() {
        if (course == null) {
            course = teamBusinessLogic.getCourseDetails(uuid);
        }
        return course;
    }
}
