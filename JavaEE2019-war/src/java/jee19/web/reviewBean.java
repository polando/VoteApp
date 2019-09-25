package jee19.web;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import jee19.logic.OptionType;
import jee19.logic.PollLogic;
import jee19.logic.dto.Item;
import jee19.logic.dto.Poll;

/**
 *
 * @author ussocom
 */
@ViewScoped
@Named
public class reviewBean implements Serializable{
    
    private static final long serialVersionUID = 4299467127994987518L;

    private List<Item> voteItems;

    private List<Item> abstains;


    @EJB
    private PollLogic polllogic;

    private Poll poll;

    @PostConstruct
    public void init() {
        poll = readPollFromFlash();
        voteItems = poll.getItems();
    }

    public List<Item> getVoteItems() {
        return voteItems;
    }

    public void setVoteItems(List<Item> voteItems) {
        this.voteItems = voteItems;
    }


    private Poll readPollFromFlash() {
        Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
        return (Poll) flash.get("selectedPoll");
    }


    public List<Item> getAbstains() {
        return abstains;
    }

    public void setAbstains(List<Item> abstains) {
        this.abstains = abstains;
    }

    public boolean isOptionTypeAbstain(OptionType optionType) {
        return (optionType == OptionType.Abstain);
    }
    

}