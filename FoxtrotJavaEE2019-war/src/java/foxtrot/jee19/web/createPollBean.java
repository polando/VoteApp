/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foxtrot.jee19.web;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import foxtrot.jee19.logic.PollLogic;
import foxtrot.jee19.logic.ItemType;
import foxtrot.jee19.logic.dto.DefinedPersonList;
import foxtrot.jee19.logic.dto.Item;
import foxtrot.jee19.logic.dto.Person;
import foxtrot.jee19.logic.dto.PollState;
import foxtrot.jee19.web.utilities.TimeUtility;
import java.util.Map;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

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
    
    private boolean participationTracking;
    
    private DefinedPersonList definedPersonList;
    
    private Item selectedItem;
    
    private String timeOffest;
        
    @Inject
    private LoginBean loginBean;
    
    @Inject
    private TimeUtility timeUtility;
    
    
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

    public boolean isParticipationTracking() {
        return participationTracking;
    }

    public void setParticipationTracking(boolean participationTracking) {
        this.participationTracking = participationTracking;
    }

    public DefinedPersonList getDefinedPersonList() {
        return definedPersonList;
    }

    public void setDefinedPersonList(DefinedPersonList definedPersonList) {
        this.definedPersonList = definedPersonList;
    }

    

    @PostConstruct
    public void init() {
        participants = new ArrayList<>();
        items = new ArrayList<>();
        organizers = new ArrayList<>();
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        timeOffest = (String) sessionMap.get("timeOffest");
    }

    public String createPoll() {
        setNowAsCurrentDate();
        participants = definedPersonList.getPersons();
        organizers.add(loginBean.getUser());
        polllogic.createPoll(title, description, timeUtility.DateToInstant(endDate, timeOffest), createDateInstant, timeUtility.DateToInstant(startDate, timeOffest), participants, organizers, items, participationTracking);
        return "pollCreatedSuccessfully";
    }

    public void addItem(Item e) {
        items.add(e);
    }

    void setNowAsCurrentDate() {
        setCreateDate(new Date());
    }

    public Item getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(Item selectedItem) {
        this.selectedItem = selectedItem;
    }
    
    public void modifyItem(Item item){
        items.removeIf(s -> s.getUuid().equals(item.getUuid()));
        addItem(item);
    }
    
    public void removeItem(Item item){
        items.removeIf(e->e.getUuid().equals(item.getUuid()));
        polllogic.removeItem(item);   
    }

    public String getTimeOffest() {
        return timeOffest;
    }

    public void setTimeOffest(String timeOffest) {
        System.out.println("timeOffest  b  "+timeOffest);
        this.timeOffest = timeOffest;
    }

    

    
}
