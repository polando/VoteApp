package jee19.entities;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TEAM")
public class TeamEntity extends NamedEntity {

    private static final long serialVersionUID = -180151093144602061L;

    @ManyToMany(mappedBy = "teams")
    private Set<PersonEntity> members;

    @ManyToOne
    private CourseEntity course;

    public TeamEntity() {
        this(false);
    }

    public TeamEntity(boolean isNew) {
        super(isNew);
        if (isNew) {
            members = new HashSet<>();
        }
    }

    public CourseEntity getCourse() {
        return course;
    }

    public void setCourse(CourseEntity course) {
        this.course = course;
    }

    public Set<PersonEntity> getMembers() {
        return members;
    }

}
