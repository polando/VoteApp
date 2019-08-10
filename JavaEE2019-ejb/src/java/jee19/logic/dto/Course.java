package jee19.logic.dto;

import jee19.logic.Term;

public class Course extends Named {

    private static final long serialVersionUID = 6928638737947955571L;

    private final Term term;

    private final int year;

    private final Person teacher;

    public Course(String uuid, long jpaVersion, String name, Term term, int year, Person teacher) {
        super(uuid, jpaVersion, name);
        this.term = term;
        this.year = year;
        this.teacher = teacher;
    }

    public Term getTerm() {
        return term;
    }

    public int getYear() {
        return year;
    }

    public Person getTeacher() {
        return teacher;
    }
}
