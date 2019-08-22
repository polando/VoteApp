package jee19.logic.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.InvocationContext;
import javax.mail.MessagingException;
import jee19.entities.CourseEntity;
import jee19.entities.ItemEntity;
import jee19.entities.PersonEntity;
import jee19.entities.PollTypeEntity;
import jee19.entities.TeamEntity;
import jee19.logic.TeamBusinessLogic;
import jee19.logic.Term;
import jee19.logic.dao.CourseAccess;
import jee19.logic.dao.ItemAccess;
import jee19.logic.dao.PersonAccess;
import jee19.logic.dao.PollTypeAccess;
import jee19.logic.dao.TeamAccess;
import jee19.logic.dto.Course;
import jee19.logic.dto.CourseDetails;
import jee19.logic.dto.Item;
import jee19.logic.dto.Person;
import jee19.logic.dto.PollType;
import jee19.logic.dto.Team;

/**
 * @author Dr. Volker Riediger <riediger@uni-koblenz.de>
 */
@Stateless
public class TeamBusinessLogicImpl implements TeamBusinessLogic {

    @EJB
    private PersonAccess personAccess;

    @EJB
    private CourseAccess courseAccess;

    @EJB
    private TeamAccess teamAccess;
    
    @EJB
    private PollTypeAccess pollTypeAccess;
    
    @EJB
    private ItemAccess itemAccess;

//    @Resource(lookup = "mail/uniko-mail")
//    private Session mailSession;
    // @AroundInvoke
    private Object intercept(InvocationContext ctx) throws Exception {
//        Object[] parameters = ctx.getParameters();
//        String param = (String) parameters[0];
//        param = param.toLowerCase();
//        parameters[0] = param;
//        ctx.setParameters(parameters);
        try {
            Method m = ctx.getMethod();
            System.err.println("Call to " + ctx.getMethod().getName());
            Object result = ctx.proceed();
            System.err.println("  ...after call to " + ctx.getMethod().getName());
            return result;
        } catch (Exception e) {
            System.err.println("   ...caught exception " + e);
            throw e;
        }
    }

    @Override
    public List<Person> getPersonList() {
        List<PersonEntity> l = personAccess.getPersonList();
        List<Person> result = new ArrayList<>(l.size());
        for (PersonEntity pe : l) {
            Person p = new Person(pe.getUuid(), pe.getJpaVersion(), pe.getName());
            p.setFirstname(pe.getFirstname());
            p.setLastname(pe.getLastname());
            p.setDateOfBirth(pe.getDateOfBirth());
            result.add(p);
        }
        return result;
    }

    @Override
    public Person getPersonByName(String name
    ) {
        PersonEntity pe = personAccess.getPersonByName(name);
        Person p = new Person(pe.getUuid(), pe.getJpaVersion(), pe.getName());
        p.setFirstname(pe.getFirstname());
        p.setLastname(pe.getLastname());
        p.setDateOfBirth(pe.getDateOfBirth());
        return p;
    }

    @Override
    public Person createPerson(String name
    ) {
        PersonEntity pe = personAccess.createEntity(name);
        return new Person(pe.getUuid(), pe.getJpaVersion(), pe.getName());
    }

    @Override
    @RolesAllowed("STAFF")
    public Course createCourse(Term term, int year, String name, String teacherUuid
    ) {
        PersonEntity pe = personAccess.getByUuid(teacherUuid);
        CourseEntity ce = courseAccess.createEntity(name);
        ce.setTerm(term);
        ce.setYear(year);
        ce.setTeacher(pe);
        pe.getCourses().add(ce);
        return new Course(
                ce.getUuid(), ce.getJpaVersion(),
                ce.getName(), ce.getTerm(), ce.getYear(),
                new Person(pe.getUuid(), pe.getJpaVersion(), pe.getName())
        );
    }

    @Override
    public Team createTeam(String name, Course course) {
        CourseEntity c = courseAccess.getByUuid(course.getUuid());
        TeamEntity t = teamAccess.createEntity(name);
        t.setCourse(c);
        c.getTeams().add(t);
        return new Team(t.getUuid(), t.getJpaVersion(), t.getName());
    }

    @Override
    public long getPersonCount() {
        return personAccess.getEntityCount();
    }

    @Override
    public long getCourseCount() {
        return courseAccess.getEntityCount();
    }

    @Override
    public List<Course> getCourseList() {
        List<Course> result = new ArrayList<>((int) getCourseCount());
        courseAccess.getCourseList().forEach((c) -> {
            result.add(new Course(
                    c.getUuid(), c.getJpaVersion(),
                    c.getName(), c.getTerm(), c.getYear(),
                    new Person(c.getTeacher().getUuid(),
                            c.getTeacher().getJpaVersion(),
                            c.getTeacher().getName()))
            );
        });
        return result;
    }

    @Override
    @RolesAllowed("AUTHENTICATED")
    public CourseDetails getCourseDetails(String uuid) {
        CourseEntity c = courseAccess.getByUuid(uuid);
        if (c == null) {
            return null;
        }
        CourseDetails result = new CourseDetails(
                c.getUuid(), c.getJpaVersion(), c.getName(), c.getTerm(), c.getYear(),
                new Person(c.getTeacher().getUuid(), c.getTeacher().getJpaVersion(), c.getTeacher().getName()));
        c.getTeams().forEach((te) -> {
            Team t = new Team(te.getUuid(), te.getJpaVersion(), te.getName());
            te.getMembers().forEach((pe) -> {
                t.getMembers().add(new Person(pe.getUuid(), pe.getJpaVersion(), pe.getName()));
            });
            Collections.sort(t.getMembers());
            result.getTeams().add(t);
        });
        Collections.sort(result.getTeams());
        return result;
    }

    @Override
    @RolesAllowed("ADMIN")
    public void createTestData() {
        if (getCourseCount() > 0) {
            // no need to create test data since some data exists
            return;
        }
        final String[] PERSONS = {"julianmosen@uni-koblenz.de",
            "ksauerborn@uni-koblenz.de",
            "stocker@uni-koblenz.de",
            "matheyes@uni-koblenz.de",
            "nschroeder@uni-koblenz.de",
            "mendozade@uni-koblenz.de",
            "cbauch@uni-koblenz.de",
            "kraft@uni-koblenz.de",
            "snaveed@uni-koblenz.de",
            "fsteffens@uni-koblenz.de",
            "thielen93@uni-koblenz.de",
            "ktrauden@uni-koblenz.de",
            "wottschal@uni-koblenz.de",
            "adufek@uni-koblenz.de",
            "nkarbach@uni-koblenz.de",
            "deniedi@uni-koblenz.de",
            "tburkha@uni-koblenz.de",
            "mkroll@uni-koblenz.de",
            "fheller@uni-koblenz.de",
            "kgoronzy@uni-koblenz.de",
            "roesken@uni-koblenz.de",
            "nfriesen@uni-koblenz.de",
            "larissabenner@uni-koblenz.de",
            "terbar@uni-koblenz.de",
            "aeul@uni-koblenz.de",
            "mlangner@uni-koblenz.de",
            "hneuhaus@uni-koblenz.de",
            "daniel78@uni-koblenz.de",
            "chemie@uni-koblenz.de",
            "smuerset@uni-koblenz.de",
            "noreply@group.uni-koblenz.de",
            "theinz@uni-koblenz.de",
            "wfau22@uni-koblenz.de",
            "cwirth@uni-koblenz.de",
            "ecesis@uni-koblenz.de",
            "celinavreden@uni-koblenz.de",
            "avogel@uni-koblenz.de",
            "sbroeder@uni-koblenz.de",
            "rfrings@uni-koblenz.de",
            "cseel@uni-koblenz.de"
        };

        final String[] COURSES = {"Math", "Physics", "Chemistry", "Biology",
            "Computer Science", "English", "History", "Ethics", "Sports",
            "Cooking", "Accounting"};

        final String[] TEAMS = {"alpha", "bravo", "charlie", "delta", "echo",
            "foxtrot", "golf", "hotel", "india", "juliet", "kilo", "lima",
            "mike", "november", "oscar", "papa", "quebec", "romeo", "sierra",
            "tango", "uniform", "victor", "whiskey", "xray", "yankee", "zulu"};
        
        final String[] POLLTYPES = {"yes/no","1 of n" , "m of n"};
        
        final String[] POLLITEMS = {"item1","item2","item3"};

        Random rnd = new Random();

        List<PersonEntity> persons = new ArrayList<>();

        for (String name : PERSONS) {
            persons.add(personAccess.createEntity(name));
        }
        
        for (String name : POLLTYPES){
             createPollType(name);
        }
        
        for (String name : POLLITEMS){
             createPollItem(name);
        }
        

        for (String name : COURSES) {
            CourseEntity c = courseAccess.createEntity(name);
            c.setTerm(
                    rnd.nextBoolean() ? Term.WINTER : Term.SUMMER);
            c.setYear(2000 + rnd.nextInt(10));
            PersonEntity teacher = persons.get(rnd.nextInt(persons.size()));
            c.setTeacher(teacher);
            teacher.getCourses().add(c);
            for (String teamName : TEAMS) {
                TeamEntity team = teamAccess.createEntity(teamName);
                c.getTeams().add(team);
                team.setCourse(c);

                int n = rnd.nextInt(5);
                while (n-- > 0) {
                    PersonEntity member = persons.get(rnd.nextInt(persons.size()));
                    team.getMembers().add(member);
                    member.getTeams().add(team);
                }
            }
        }
    }
    
    public PollType createPollType(String name) {
        PollTypeEntity p = pollTypeAccess.createEntity(name);
        p.setPollType(name);
        return new PollType(p.getUuid(), p.getJpaVersion(), p.getName());
    }
    
    public Item createPollItem(String name) {
        ItemEntity p = itemAccess.createEntity(name);
        p.setItem(name);
        return new Item(p.getUuid(), p.getJpaVersion(), p.getName());
    }

    @RolesAllowed("AUTHENTICATED")
    @Override
    public void storePersonDetails(Person person
    ) {
        personAccess.storePersonDetails(person);
        try {
            sendMail(person.getName(),
                    "Your account settings have been changed",
                    "Dear user,\n\n"
                    + "Your account settings have been changed to:\n"
                    + "- firstname: " + person.getFirstname() + "\n"
                    + "- lastname: " + person.getLastname() + "\n"
                    + "- date of birth: " + (person.getDateOfBirth() == null ? "(not specified)" : person.getDateOfBirth()) + "\n\n"
                    + "Regards: Your TEAM SYSTEM\n"
            );
        } catch (MessagingException ex) {
            Logger.getLogger(TeamBusinessLogicImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void sendMail(String recipient, String subject, String text) throws MessagingException {
//        Message msg = new MimeMessage(mailSession);
//        msg.setSubject(subject);
//        msg.setSentDate(new Date());
//        msg.setRecipients(
//                Message.RecipientType.TO,
//                InternetAddress.parse(recipient, false));
//        msg.setFrom(InternetAddress.parse("team-system@no.where")[0]);
//        msg.setText(text);
//        Transport.send(msg);
    }

    @Override
    public List<Person> testLogicMethod() {
          List<Person> result =  new ArrayList<>((int) personAccess.getEntityCount()) ;
          personAccess.getPersonList().forEach( (c) -> {
                  result.add(new Person(c.getUuid(),c.getJpaVersion(),c.getName()));
                  });
          return result;
    }
}
