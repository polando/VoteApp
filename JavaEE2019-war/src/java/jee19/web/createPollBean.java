/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee19.web;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import jee19.logic.PollLogic;
import jee19.logic.dto.Person;
import jee19.logic.dto.PollType;
import org.primefaces.model.DualListModel;

/**
 *
 * @author ussocom
 */

@ViewScoped
@Named
public class createPollBean implements Serializable {

    private static final long serialVersionUID = -8672602607708913941L;
    
    @EJB
    private PollLogic polllogic;
    
    private PollType polltype;
    
    private List<Person> participants;
        
    private String title;  
    
    private String description;
    
    private Date startDate;
    
    private Date endDate;
    
    private Date createDate;
    
    private Instant startDateInstant;
    
    private Instant endDateInstant;
    
    private Instant createDateInstant;
    

    


    public PollType getPolltype() {
        return polltype;
    }

    public void setPolltype(PollType polltype) {
        this.polltype = polltype;
    }

    public List<Person> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Person> participants) {
        this.participants = participants;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
        startDateInstant = DateToInstant(startDate);
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        
        this.endDate = endDate;
        endDateInstant = DateToInstant(endDate);
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
        createDateInstant = createDate.toInstant();
    }

    
    
    public void createPoll(){
      setNowAsCurrentDate();
      System.out.println( startDateInstant + " ** "+ endDateInstant + " ** " + createDateInstant);
    }
    
    
    Instant DateToInstant(Date date){
        Instant t = null;
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(date);
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        try{
        Date d = formatter.parse(dateString);
        t = d.toInstant();
        }
        catch(ParseException e){
            System.err.println("Error parsing date to instant " + e.getMessage());
        }
        
         return t;
    }
    
    void setNowAsCurrentDate(){
        setCreateDate(new Date());
    }
    
    
    
    
}
