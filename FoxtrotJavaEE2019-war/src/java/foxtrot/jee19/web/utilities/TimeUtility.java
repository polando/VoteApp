/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foxtrot.jee19.web.utilities;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author ussocom
 */
@ViewScoped
@Named
public class TimeUtility implements Serializable {

    private static final long serialVersionUID = 2446678450753630256L;

    public Instant DateToInstant(Date date, String zone) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        LocalDate localDate = LocalDate.of(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
        LocalTime localTime = LocalTime.of(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),calendar.get(Calendar.SECOND));
        ZonedDateTime zonedDateTime = ZonedDateTime.of(localDate, localTime, ZoneId.of(zone));
        System.out.println("zoned time from 1 : "+zonedDateTime.toString());
        return zonedDateTime.toInstant();
    }
    
    public Date InstantToDate(Instant instant, String zone) {
        ZoneId zoneId = ZoneId.of(zone);
        ZonedDateTime zdt = ZonedDateTime.ofInstant( instant , zoneId );
        System.out.println("zoned time from 2 : "+zdt.toString());
        Date date = new Calendar.Builder()
            .setDate(zdt.getYear(),zdt.getMonthValue()-1, zdt.getDayOfMonth())
            .setTimeOfDay(zdt.getHour(),zdt.getMinute(), zdt.getSecond())
            .build().getTime();
        return date;
    }
}
