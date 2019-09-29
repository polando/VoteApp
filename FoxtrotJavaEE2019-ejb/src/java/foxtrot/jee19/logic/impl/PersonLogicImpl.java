package foxtrot.jee19.logic.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.InvocationContext;
import javax.mail.MessagingException;
import foxtrot.jee19.entities.PersonEntity;
import foxtrot.jee19.logic.OptionType;
import foxtrot.jee19.logic.PollLogic;
import foxtrot.jee19.logic.dao.ItemAccess;
import foxtrot.jee19.logic.dao.PersonAccess;
import foxtrot.jee19.logic.dto.Person;
import foxtrot.jee19.logic.PersonLogic;

/**
 * @author Dr. Volker Riediger <riediger@uni-koblenz.de>
 */
@Stateless
public class PersonLogicImpl implements PersonLogic {

    @EJB
    private PersonAccess personAccess;
    
    @EJB
    private ItemAccess itemAccess;

    @EJB
    private PollLogic polllogic;
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
    public long getPersonCount() {
        return personAccess.getEntityCount();
    }


 /*   @Override
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
    }*/

    @Override
    public void createTestData() {
        if (getPersonCount() > 0) {
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

        Random rnd = new Random();

        List<PersonEntity> persons = new ArrayList<>();

        for (String name : PERSONS) {
            persons.add(personAccess.createEntity(name));
        }
        
        
        final String[] PREPOLLITEMS = {"Yes","No"};
        
        final String[] NONPREPOLLITEMS = {"option 1","option 2"};
        
        for(String s:PREPOLLITEMS){
            polllogic.createOption(s,s,OptionType.YesNo);
        }
        
        for(String s:NONPREPOLLITEMS){
            polllogic.createOption(s,s,OptionType.NonPermanent);
        }
        
        
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
            Logger.getLogger(PersonLogicImpl.class.getName()).log(Level.SEVERE, null, ex);
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
