/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foxtrot.jee19.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import foxtrot.jee19.logic.PollLogic;
import foxtrot.jee19.logic.ItemType;
import foxtrot.jee19.logic.OptionType;
import foxtrot.jee19.logic.dto.Item;
import foxtrot.jee19.logic.dto.Option;
import foxtrot.jee19.logic.dto.Poll;

/**
 *
 * @author ussocom
 */
@ViewScoped
@Named
public class votingBean implements Serializable {

    private static final long serialVersionUID = -7434000390609622052L;

    private List<Item> voteItems;

    private List<Item> abstains;

    private Item chosenItem;

    private String token;

    @EJB
    private PollLogic polllogic;

    private Poll poll;

    @PostConstruct
    public void init() {
        poll = readPollFromFlash();
        voteItems = poll.getItems();
        /*  abstains = poll.getItems();

        voteItems.forEach((i) -> {
            List<Option> options = new ArrayList<>();
            i.getOptions().forEach((o) -> {
                if (o.getOptionType() != OptionType.Abstain) {
                    options.add(o);
                }
            });
            i.setOptions(options);
        });

        abstains.forEach((i) -> {
            List<Option> options = new ArrayList<>();
            i.getOptions().forEach((o) -> {
                if (o.getOptionType() == OptionType.Abstain) {
                    options.add(o);
                }
            });
            i.setOptions(options);
        });
         */
        token = readTokenFromFlash();
        
        for(Item i: voteItems){
            for(Option o:i.getOptions()){
                System.out.println("item:"+i.getTitle()+"opt:"+o.getShortName());
            }
        }

    }

    public List<Item> getVoteItems() {
        return voteItems;
    }

    public void setVoteItems(List<Item> voteItems) {
        this.voteItems = voteItems;
    }

    public String submitVote() {
        voteItems.forEach((i) -> {
            if (i.isIsAbstainChosen()) {
                for (Option o : i.getOptions()) {
                    if (o.getOptionType() == OptionType.Abstain) {
                        i.setChosenOption(o);
                    }
                }
                polllogic.addToVotes(token, poll.getUuid(), i.getUuid(), i.getChosenOption().getUuid());
            } else if (i.isNOfM()) {
                i.getChosenOptions().forEach((o) -> {
                    polllogic.addToVotes(token, poll.getUuid(), i.getUuid(), o.getUuid());
                });
            } else {
                polllogic.addToVotes(token, poll.getUuid(), i.getUuid(), i.getChosenOption().getUuid());
            }

        });
        if (isAllVotesSubmitted(poll.getUuid())) {
            polllogic.setPollStateByPollUUID(poll.getUuid());
        }
        return "voteSumbitSuccess";
    }

    private boolean isAllVotesSubmitted(String pollUUID) {
        return polllogic.checkAllVotesSubmitted(pollUUID);
    }

    private Poll readPollFromFlash() {
        Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
        return (Poll) flash.get("poll");
    }

    private String readTokenFromFlash() {
        Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
        return (String) flash.get("token");
    }

    public Item getChosenItem() {
        return chosenItem;
    }

    public void setChosenItem(Item chosenItem) {
        this.chosenItem = chosenItem;
    }

    public List<Item> getAbstains() {
        return abstains;
    }

    public void setAbstains(List<Item> abstains) {
        this.abstains = abstains;
    }

    public void setChosenOptionToAbstain(Item item) {

    }

    public boolean isOptionTypeAbstain(OptionType optionType) {
        return (optionType == OptionType.Abstain);
    }

}
