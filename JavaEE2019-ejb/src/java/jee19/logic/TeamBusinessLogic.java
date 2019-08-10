package jee19.logic;

import java.util.List;
import javax.ejb.Remote;
import jee19.logic.dto.Course;
import jee19.logic.dto.CourseDetails;
import jee19.logic.dto.Person;
import jee19.logic.dto.Team;

/**
 * @author Dr. Volker Riediger <riediger@uni-koblenz.de>
 */
@Remote
public interface TeamBusinessLogic {

    /**
     * Returns a list of all {@link Person}s.
     *
     * @return the person list, sorted by name
     */
    public List<Person> getPersonList();

    /**
     * Retrieves a Person from the DB, identified by the (login) name.
     *
     * @param name the login name (email address) of the person
     * @return a Person object, created if not yet existent
     */
    public Person getPersonByName(String name);

    /**
     * Returns a list of all {@link Course}s.
     *
     * @return the course list, sorted by year, term, and name
     */
    public List<Course> getCourseList();

    /**
     * Creates a {@link Person} with the specified name.
     *
     * @param name the name of the new person, must be != null and not empty
     *
     * @return the created Person with a new uuid.
     */
    public Person createPerson(String name);

    /**
     * Creates a {@link Course} with the specified term, year, name, and
     * teacher.
     *
     * @param term the {@link Term} for the new course
     * @param year the year for the new course
     * @param name the name of the new course, must be != null and not empty
     * @param teacherUuid the uuid of the {@link Person} who acts as teacher for
     * the new course, must not be null
     *
     * @return the created Course with a new uuid.
     */
    public Course createCourse(Term term, int year, String name, String teacherUuid);

    /**
     * Retrieves the course details for the {@link Course} with the specified
     * uuid. Course details contain course data, teams, and team members.
     *
     * @param uuid the uuid of the course to look for
     *
     * @return the course details, or null if no course with the given uuid
     * exists
     */
    public CourseDetails getCourseDetails(String uuid);

    /**
     * Creates a {@link Team} with the specified name in an existing course.
     *
     * @param name the name of the new teame, must be != null and not empty
     * @param course the {@link Course} where the new team shall be added, must
     * not be null
     *
     * @return the created Team with a new uuid.
     */
    public Team createTeam(String name, Course course);

    /**
     * @return the total number of {@link Person}s
     */
    public long getPersonCount();

    /**
     * @return the total number of {@link Course}s
     */
    public long getCourseCount();

    /**
     * Creates some {@link Person}s, {@link Course}s, and {@link Team}s. This
     * method does nothing if one or more {@link Person}s already exist.
     */
    public void createTestData();

    void storePersonDetails(Person person);
}
