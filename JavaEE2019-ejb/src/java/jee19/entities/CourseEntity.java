package jee19.entities;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import jee19.logic.Term;

@NamedQueries({
    @NamedQuery(name = "getCourseCount", query = "SELECT COUNT(c) FROM CourseEntity c"),
    @NamedQuery(name = "getCourseList", query = "SELECT c FROM CourseEntity c ORDER BY c.year, c.term, c.name")
})
@Entity
@Table(name = "COURSE")
public class CourseEntity extends NamedEntity {

    private static final long serialVersionUID = -1936004176997530919L;

    @Enumerated(EnumType.STRING)
    private Term term;

    @Column(name = "COURSEYEAR")
    private int year;

    @OneToMany(mappedBy = "course")
    private Set<TeamEntity> teams;

    @ManyToOne
    private PersonEntity teacher;

    public CourseEntity() {
    }

    public CourseEntity(boolean isNew) {
        super(isNew);
        if (isNew) {
            teams = new HashSet<>();
        }
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

    public Set<TeamEntity> getTeams() {
        return teams;
    }

    public PersonEntity getTeacher() {
        return teacher;
    }

    public void setTeacher(PersonEntity teacher) {
        this.teacher = teacher;
    }

}
