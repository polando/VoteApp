/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foxtrot.jee19.entities;


import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import foxtrot.jee19.logic.PollState;
import foxtrot.jee19.utilities.PollStateJpaConverter;

/**
 *
 * @author ussocom
 */

@NamedQueries({
    @NamedQuery(name = "getPollCount", query = "SELECT COUNT(p) FROM PollEntity p"),
    @NamedQuery(name = "getPollList", query = "SELECT p FROM PollEntity p ORDER BY p.name, p.uuid"),
    @NamedQuery(name = "getFinishedPollsIDListByOrganizer", query = "SELECT p FROM PollEntity p,ResultEntity r INNER JOIN p.organizers org WHERE org.uuid = :organizerUUID AND (SELECT SUM (s.numberOfVotes) FROM ResultEntity s WHERE s.poll.uuid = p.uuid) > 0"),
    @NamedQuery(name = "getAllPollTitles", query = "SELECT p.title FROM PollEntity p"),
    @NamedQuery(name = "getPollsIDListByOrganizerAndState", query = "SELECT p FROM PollEntity p INNER JOIN p.organizers org WHERE org.uuid = :organizerUUID AND p.pollState = :state"),
    @NamedQuery(name = "getPollbyPollUUID", query = "SELECT p FROM PollEntity p WHERE p.uuid = :pollUUID "),
    @NamedQuery(name = "getStartedOrVotingPollsIDListByOrganizer", query = "SELECT p FROM PollEntity p INNER JOIN p.organizers org WHERE org.uuid = :organizerUUID AND (p.pollState = :stateOne OR p.pollState = :stateTwo)"),
    @NamedQuery(name = "getAllPolls", query = "SELECT p FROM PollEntity p"), 
    @NamedQuery(name = "getAllPublishedPolls", query = "SELECT p FROM PollEntity p WHERE p.resultPublished = TRUE"),
    @NamedQuery(name = "getAllPollsWithStateAndTrackingByOrganizer", query = "SELECT p FROM PollEntity p INNER JOIN p.organizers org WHERE org.uuid = :organizerUUID AND p.resultPublished = :tracking AND p.pollState = :state")
    
})
@Entity
@Table(name="POLL")
public class PollEntity extends NamedEntity{

    private static final long serialVersionUID = -2818216934131204323L;
    
    
    private String title;  
    
    private String description;
    
    private Instant startDate;
    
    private Instant endDate;
    
    private Instant createDate;
    
    private boolean resultPublished;
    
    private boolean participationTracking;
    
    @Convert(converter = PollStateJpaConverter.class)
    private PollState pollState;
    

    @ManyToMany
    private List<ItemEntity> itemEntities;
    

    @OneToMany(mappedBy = "pollEntity", cascade = CascadeType.ALL)
    private Set<TokenEntity> tokens;
 
    
    @ManyToMany
    @JoinTable(name = "POLL_ORGANIZERS",
        joinColumns = @JoinColumn(name = "poll_id"), 
        inverseJoinColumns = @JoinColumn(name = "organizer_id"))
    private List<PersonEntity> organizers;
    
    
    @ManyToMany
    @JoinTable(name = "POLL_PARTICIPANTS",
        joinColumns = @JoinColumn(name = "poll_id"), 
        inverseJoinColumns = @JoinColumn(name = "participant_id"))
    private List<PersonEntity> participants;
    
   
    @OneToMany(mappedBy = "poll",cascade = CascadeType.ALL)
    private Set<ResultEntity> resultEntities;
    
    
   public PollEntity() {
        this(false);
    }

    public PollEntity(boolean isNew) {
        super(isNew);
        if (isNew) {
            resultEntities = new HashSet<>();
        }
    }

    public boolean isResultPublished() {
        return resultPublished;
    }

    public void setResultPublished(boolean resultPublished) {
        this.resultPublished = resultPublished;
    }

    
    public List<PersonEntity> getOrganizers() {
        return organizers;
    }

    public void setOrganizers(List<PersonEntity> organizers) {
        this.organizers = organizers;
    }


    public Set<ResultEntity> getResultEntities() {
        return resultEntities;
    }

    public void setResultEntities(Set<ResultEntity> resultEntities) {
        this.resultEntities = resultEntities;
    }


    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public Instant getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Instant createDate) {
        this.createDate = createDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public List<ItemEntity> getItemEntities() {
        return itemEntities;
    }

    public void setItemEntities(List<ItemEntity> itemEntities) {
        this.itemEntities = itemEntities;
    }


    public Set<TokenEntity> getTokens() {
        return tokens;
    }

    public void setTokens(Set<TokenEntity> tokens) {
        this.tokens = tokens;
    }

    public PollState getPollState() {
        return pollState;
    }

    public void setPollState(PollState pollState) {
        this.pollState = pollState;
    }

    public List<PersonEntity> getParticipants() {
        return participants;
    }

    public void setParticipants(List<PersonEntity> participants) {
        this.participants = participants;
    }

    public boolean isParticipationTracking() {
        return participationTracking;
    }

    public void setParticipationTracking(boolean participationTracking) {
        this.participationTracking = participationTracking;
    }
 
    
}
