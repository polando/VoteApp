package jee19.logic.dto;

import java.util.ArrayList;
import java.util.List;
import jee19.logic.Term;

/**
 * @author Dr. Volker Riediger <riediger@uni-koblenz.de>
 */
public class CourseDetails extends Course {

    private static final long serialVersionUID = -6465480949995375469L;

    private final List<Team> teams;

    public CourseDetails(String uuid, long jpaVersion, String name, Term term, int year, Person teacher) {
        super(uuid, jpaVersion, name, term, year, teacher);
        teams = new ArrayList<>();
    }

    public List<Team> getTeams() {
        return teams;
    }
}
