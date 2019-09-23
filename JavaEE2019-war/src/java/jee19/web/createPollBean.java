/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee19.web;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import jee19.logic.PollLogic;
import jee19.logic.ItemType;
import jee19.logic.dto.Item;
import jee19.logic.dto.Person;
import jee19.logic.dto.PollState;
import jee19.web.utilities.errorMessageUtility;
import org.jboss.weld.probe.Strings;
import org.primefaces.PrimeFaces;

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

    private ItemType itemtype;

    private PollState pollstate;

    private List<Item> items;

    private List<Person> participants;

    private List<Person> organizers;

    private String title;

    private String description;

    private Date startDate;

    private Date endDate;

    private Date createDate;

    private Instant createDateInstant;

    private List<String> test;

    private Item currentEditItem;
    
    @Inject
    private LoginBean loginBean;

    public List<String> getTest() {
        return test;
    }

    public void setTest(List<String> test) {
        this.test = test;
    }

    public ItemType getItemtype() {
        return itemtype;
    }

    public void setItemtype(ItemType itemtype) {
        this.itemtype = itemtype;
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
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public List<Person> getOrganizers() {
        return organizers;
    }

    public void setOrganizers(List<Person> organizers) {
        this.organizers = organizers;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public PollState getPollstate() {
        return pollstate;
    }

    public void setPollstate(PollState pollstate) {
        this.pollstate = pollstate;
    }

    public Item getCurrentEditItem() {
        return currentEditItem;
    }

    public void setCurrentEditItem(Item currentEditItem) {
        this.currentEditItem = currentEditItem;
    }


    @PostConstruct
    public void init() {
        participants = new ArrayList<>();
        items = new ArrayList<>();
        organizers = new ArrayList<>();
    }

    public String createPoll() {
        setNowAsCurrentDate();
        organizers.add(loginBean.getUser());
        polllogic.createPoll(title, description, endDate, createDateInstant, startDate, participants, organizers, items);
        return "pollCreatedSuccessfully";
    }

    public void addItem(Item e) {
        items.add(e);
        items.forEach((i) -> {
            System.out.println("i" + i);
        });
    }

    Instant DateToInstant(Date date) {
        Instant t = null;
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(date);
        formatter.setTimeZone(TimeZone.getDefault());
        try {
            Date d = formatter.parse(dateString);
            t = d.toInstant();
        } catch (ParseException e) {
            System.err.println("Error parsing date to instant " + e.getMessage());
        }

        return t;
    }

    void setNowAsCurrentDate() {
        setCreateDate(new Date());
    }

}
