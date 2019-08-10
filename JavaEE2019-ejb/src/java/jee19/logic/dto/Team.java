package jee19.logic.dto;

import java.util.ArrayList;
import java.util.List;

public class Team extends Named {

    private static final long serialVersionUID = 8365590700816594806L;

    private final List<Person> members;

    private Course course;

    public Team(String uuid, long jpaVersion, String name) {
        super(uuid, jpaVersion, name);
        members = new ArrayList<>();
    }

    public Course getCourse() {
        return course;
    }

    public List<Person> getMembers() {
        return members;
    }
}
