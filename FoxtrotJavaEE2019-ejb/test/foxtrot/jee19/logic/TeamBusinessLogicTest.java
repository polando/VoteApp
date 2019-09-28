package foxtrot.jee19.logic;

import foxtrot.jee19.logic.Term;
import foxtrot.jee19.logic.PersonLogic;
import java.util.List;
import java.util.Properties;
import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import javax.naming.NamingException;
import foxtrot.jee19.logic.dto.Course;
import foxtrot.jee19.logic.dto.CourseDetails;
import foxtrot.jee19.logic.dto.Person;
import foxtrot.jee19.logic.dto.Team;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Dr. Volker Riediger <riediger@uni-koblenz.de>
 */
public class TeamBusinessLogicTest {

    private static EJBContainer container;
    private static Context ctx;
    private PersonLogic teamBusinessLogic;

    @BeforeClass
    public static void setupClass() {
        Properties props = new Properties();
        props.setProperty(EJBContainer.MODULES, "JavaEE2019-ejb");
        container = EJBContainer.createEJBContainer(props);
        ctx = container.getContext();
    }

    @Before
    public void createEJB() throws NamingException {
        teamBusinessLogic = (PersonLogic) ctx.lookup(
                "java:global/JavaEE2019/JavaEE2019-ejb/TeamBusinessLogicImpl");
        Assert.assertNotNull(teamBusinessLogic);
    }

    @AfterClass
    public static void tearDownClass() {
        container.close();
    }

    /**
     * Test of getPersonList method, of class TeamBusinessLogic.
     */
    @Test
    public void testGetPersonList() {
    }

    /**
     * Test of getPersonByName method, of class TeamBusinessLogic.
     */
    @Test
    public void testGetPersonByName() {
    }

    /**
     * Test of getCourseList method, of class TeamBusinessLogic.
     */
    @Test
    public void testGetCourseList() {
    }

    /**
     * Test of createPerson method, of class TeamBusinessLogic.
     */
    @Test
    public void testCreatePerson() {
    }

    /**
     * Test of createCourse method, of class TeamBusinessLogic.
     */
    @Test
    public void testCreateCourse() {
    }

    /**
     * Test of getCourseDetails method, of class TeamBusinessLogic.
     */
    @Test
    public void testGetCourseDetails() {
    }

    /**
     * Test of createTeam method, of class TeamBusinessLogic.
     */
    @Test
    public void testCreateTeam() {
    }

    /**
     * Test of getPersonCount method, of class TeamBusinessLogic.
     */
    @Test
    public void testGetPersonCount() {
        Assert.assertEquals(42, teamBusinessLogic.getPersonCount());
    }

    /**
     * Test of getCourseCount method, of class TeamBusinessLogic.
     */
    @Test
    public void testGetCourseCount() {
    }

    /**
     * Test of createTestData method, of class TeamBusinessLogic.
     */
    @Test
    public void testCreateTestData() {
    }

    /**
     * Test of storePersonDetails method, of class TeamBusinessLogic.
     */
    @Test
    public void testStorePersonDetails() {
    }

    public class TeamBusinessLogicImpl implements PersonLogic {

        public List<Person> getPersonList() {
            return null;
        }

        public Person getPersonByName(String name) {
            return null;
        }

        public List<Course> getCourseList() {
            return null;
        }

        public Person createPerson(String name) {
            return null;
        }

        public Course createCourse(Term term, int year, String name, String teacherUuid) {
            return null;
        }

        public CourseDetails getCourseDetails(String uuid) {
            return null;
        }

        public Team createTeam(String name, Course course) {
            return null;
        }

        public long getPersonCount() {
            return 0L;
        }

        public long getCourseCount() {
            return 0L;
        }

        public void createTestData() {
        }

        public void storePersonDetails(Person person) {
        }

        @Override
        public List<Person> testLogicMethod() {
            return null;
        }
    }

}
