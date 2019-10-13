/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foxtrot.jee19.utilities;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Stateless;
import javax.enterprise.concurrent.ManagedScheduledExecutorService;
import javax.persistence.Entity;
import foxtrot.jee19.entities.PollEntity;
import foxtrot.jee19.logic.PollState;
import foxtrot.jee19.logic.dao.PollAccess;

/**
 *
 * @author ussocom
 */
@Singleton
@Startup
@LocalBean
public class BackgroundJobManager {

    @Resource
    ManagedScheduledExecutorService mses;

    @EJB
    private PollAccess pollAccess;

    @EJB
    private Notification notification;

    private final String startEventName = "startEvent";
    private final String finsihedEventName = "finishEvent";

    private Map<String, ScheduledFuture<?>> eventDictionary = new HashMap<>();

    public void seTimerForPoll(String pollUUID, Instant startDate, Instant endDate) {

        ScheduledExecutorService startPoll = Executors.newSingleThreadScheduledExecutor();
        ScheduledExecutorService endPoll = Executors.newSingleThreadScheduledExecutor();

        Duration fromNowTillStart = Duration.between(Instant.now(), startDate);
        Duration fromNowTillEnd = Duration.between(Instant.now(), endDate);

        scheduleEvent(pollUUID, startEventName, fromNowTillStart.toMinutes(), PollState.VOTING);
        scheduleEvent(pollUUID, finsihedEventName, fromNowTillEnd.toMinutes(), PollState.FINISHED);
    }

    public void setDelayForSendingEmail(HashMap emailInfo, long delay) {
        System.out.println("email 2 is :" + emailInfo.get("email"));
        mses.schedule(new sendMail(emailInfo.get("title").toString(),
                        emailInfo.get("start_date").toString(),emailInfo.get("end_date").toString(),
                        emailInfo.get("token").toString(),emailInfo.get("email").toString(),
                        emailInfo.get("number_of_participants").toString()), delay, TimeUnit.SECONDS);
        emailInfo = null;
    }
    

    private Runnable command;

    class sendMail implements Runnable {

        private String title;
        private String start_date;
        private String end_date;
        private String token;
        private String email;
        private String number_of_participants;

        sendMail(String title,String start_date,String end_date, String token,String email,String number_of_participants) {
            this.title = title;
            this.start_date = start_date;
            this.end_date = end_date;
            this.token = token;
            this.email = email;
            this.number_of_participants = number_of_participants;
        }

        @Override
        public void run() {
            try {
                notification.sendNotificationToParticioants(title,start_date,end_date,token,email,number_of_participants);
            } catch (Exception e) {
                System.out.println("eror" + e.getMessage());
            }
        }
    }

    public void extendPollTime(String pollUUID, Instant endDate) {
        cancelEvent(pollUUID, finsihedEventName);
        Duration fromNowTillEnd = Duration.between(Instant.now(), endDate);
        scheduleEvent(pollUUID, finsihedEventName, fromNowTillEnd.toMinutes(), PollState.FINISHED);
    }

    private Runnable changePollState(final String pollUUID, PollState pollState) {
        Runnable aRunnable = new Runnable() {
            @Override
            public void run() {
                try {
                    PollEntity pe = pollAccess.getByUuid(pollUUID);
                    System.out.println("before " + pe.getPollState());
                    pe.setPollState(pollState);
                    pollAccess.updatePoll(pe);
                    PollEntity pg = pollAccess.getByUuid(pollUUID);
                    System.out.println("before " + pg.getPollState());
                } catch (Exception e) {
                    System.out.println("eror" + e.getMessage());
                }
            }
        };
        return aRunnable;
    }

    /* private Runnable send(final HashMap emailInfo){
    Runnable aRunnable = new Runnable(){
        @Override
        public void run(){ 
            System.out.println("email 3 is :"+emailInfo.get("email"));
            try {
                notification.sendNotificationToParticioants(emailInfo);
            } catch (Exception e) {
                System.out.println("eror" + e.getMessage());
            }
        }
    };
    return aRunnable;
    }*/
    private void scheduleEvent(final String pollUUID, String eventType, long delay, PollState pollState) {
        ScheduledFuture<?> scheduledFuture = mses.schedule(changePollState(pollUUID, pollState), delay, TimeUnit.MINUTES);
        eventDictionary.put(pollUUID + eventType, scheduledFuture);
    }

    private void cancelEvent(final String pollUUID, String eventType) {
        ScheduledFuture<?> pollEvent = eventDictionary.get(pollUUID + eventType);
        pollEvent.cancel(true);
    }

}
