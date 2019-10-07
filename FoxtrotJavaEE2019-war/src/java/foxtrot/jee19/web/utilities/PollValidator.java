/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foxtrot.jee19.web.utilities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import foxtrot.jee19.logic.dto.Item;
import foxtrot.jee19.logic.dto.Person;
import foxtrot.jee19.logic.dto.Poll;
import org.primefaces.PrimeFaces;

/**
 *
 * @author ussocom
 */
@Named
@ViewScoped
public class PollValidator  implements  Serializable  {
    
    @Inject
    private errorMessageUtility errorMessageUtility;

    private static final long serialVersionUID = 2484461611437080754L;
    
    public void validatePoll(Poll poll){
        validatePoll(poll.getParticipants(),poll.getOrganizers(),poll.getItems(),poll.getStartDate(),poll.getEndDate());
    }
    
    public void validatePoll(List<Person> participants,List<Person> organizers,List<Item> items,Date startDate,Date endDate) throws ValidatorException {
        ArrayList<String> problems = new ArrayList<>();
        System.out.println(participants.size());
        if (participants == null || participants.size() < 3) {
            problems.add("There must be at least 3 participants");
        }
        if (errorMessageUtility.isNullOrEmpty(items)) {
            problems.add("There must be at least one item");
        }
        if (startDate.after(endDate) || startDate.equals(endDate)) {
            problems.add("End Date must be later than start date");
        }
        outerloop:
        for (Item i : items) {
            for (Item  p: items) {
                if (i.getTitle().equalsIgnoreCase(p.getTitle()) && !i.getUuid().equals(p.getUuid())) {
                    problems.add("Duplicate item title");
                    break outerloop;
                } 
            }
        }

        if (!problems.isEmpty()) {
            errorMessageUtility.errorCall(problems);
        }
    }


    public void validateItem(String title,List<Item> items) {
        ArrayList<String> problems = new ArrayList<>();
        for(Item i : items){
            if (i.getTitle().equalsIgnoreCase(title)){
                problems.add("Duplicate item title");
                break;
            }
        }

        if (!problems.isEmpty()) {
            errorMessageUtility.errorCall(problems);
        } else {
            PrimeFaces pf = PrimeFaces.current();
            pf.executeScript("PF('itemModal').hide()");
        }
    }    
    
    
}
