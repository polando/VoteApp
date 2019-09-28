package foxtrot.jee19.utilities;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.TimeZone;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Stateless;
import javax.inject.Named;

/**
 *
 * @author ussocom
 */
@Singleton
@Startup
@LocalBean
public class DateConvertor{

    public Instant DateToInstant(Date date) {
        Instant t = null;
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(date);
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            Date d = formatter.parse(dateString);
            t = d.toInstant();
        } catch (ParseException e) {
            System.err.println("Error parsing date to instant " + e.getMessage());
        }

        return t;
    }
    
    
    public Date InstantToDate(Instant instant) {
        Date date = Date.from(instant);
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(date);
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            date = formatter.parse(dateString);
        } catch (ParseException e) {
            System.err.println("Error parsing date to instant " + e.getMessage());
        }

        return date;
    }
    
        
    
}
