/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee19.utilities;

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
import jee19.entities.PollEntity;
import jee19.logic.PollState;
import jee19.logic.dao.PollAccess;


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

private final String startEventName = "startEvent";
private final String finsihedEventName = "finishEvent";

private Map<String, ScheduledFuture<?>> eventDictionary = new HashMap<>();
    
    
    public void seTimerForPoll(String pollUUID,Instant startDate,Instant endDate){

        ScheduledExecutorService startPoll = Executors.newSingleThreadScheduledExecutor();
        ScheduledExecutorService endPoll = Executors.newSingleThreadScheduledExecutor();

        Duration fromNowTillStart = Duration.between(Instant.now(),startDate);
        Duration fromNowTillEnd = Duration.between(Instant.now(),endDate);

        scheduleEvent(pollUUID,startEventName,fromNowTillStart.toMinutes(),PollState.VOTING);
        scheduleEvent(pollUUID,finsihedEventName,fromNowTillEnd.toMinutes(),PollState.FINISHED);
        
    }
    
    public void extendPollTime(String pollUUID,Instant endDate){
        cancelEvent(pollUUID,finsihedEventName);
        Duration fromNowTillEnd = Duration.between(Instant.now(),endDate);
        scheduleEvent(pollUUID,finsihedEventName,fromNowTillEnd.toMinutes(),PollState.FINISHED);
    }



    private Runnable changePollState(final String pollUUID,PollState pollState){
    Runnable aRunnable = new Runnable(){
        @Override
        public void run(){ 
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
    
    
    private void scheduleEvent(final String pollUUID,String eventType,long delay,PollState pollState){
        ScheduledFuture<?> scheduledFuture =  mses.schedule(changePollState(pollUUID,pollState), delay , TimeUnit.MINUTES);
        eventDictionary.put(pollUUID+eventType, scheduledFuture);
    }
    
    private void cancelEvent(final String pollUUID,String eventType){
         ScheduledFuture<?> pollEvent = eventDictionary.get(pollUUID+eventType);
         pollEvent.cancel(true);
    }
    
}
