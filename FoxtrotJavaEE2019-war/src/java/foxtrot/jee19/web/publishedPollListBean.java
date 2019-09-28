/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foxtrot.jee19.web;

import java.io.Serializable;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import foxtrot.jee19.logic.PollLogic;
import foxtrot.jee19.logic.dto.Poll;

/**
 *
 * @author ussocom
 */
@ViewScoped
@Named
public class publishedPollListBean implements Serializable {

    private static final long serialVersionUID = -1673257713009813396L;

        @EJB
        private PollLogic polllogic;

        private Poll selectedPoll;

        private Set<Poll> publishedPolls;

        @PostConstruct
        void init() {
            publishedPolls = getAllPublishedPolls();
        }

        private Set<Poll> getAllPublishedPolls() {
            Set<Poll> poll = polllogic.getPublishedPolls();
            return poll;
        }

        public Poll getSelectedPoll() {
            return selectedPoll;
        }

        public void setSelectedPoll(Poll selectedPoll) {
            this.selectedPoll = selectedPoll;
        }

        public String seePollResult() {
            Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
            flash.put("selectedPoll", selectedPoll);
            return "showResult";
        }

        public Set<Poll> getPublishedPolls() {
            return publishedPolls;
        }

}
