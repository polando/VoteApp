/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foxtrot.jee19.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import foxtrot.jee19.entities.OptEntity;
import foxtrot.jee19.logic.PollLogic;
import foxtrot.jee19.logic.dto.Item;
import foxtrot.jee19.logic.dto.Option;
import foxtrot.jee19.logic.dto.Poll;
import foxtrot.jee19.logic.dto.VoteResult;
import org.primefaces.model.chart.PieChartModel;

/**
 *
 * @author ussocom
 */
@ViewScoped
@Named
public class pollResult implements Serializable {

    private static final long serialVersionUID = -614662490787062254L;
    
    @EJB
    private PollLogic polllogic;
    
    private Poll poll;
    
    private List<PieChartModel> pieModels;
    
    private Map<String, Map<String, Integer>> map;
      
    private List<VoteResult> VoteResults;

    public List<VoteResult> getVoteResults() {
        return VoteResults;
    }

    public void setVoteResults(List<VoteResult> VoteResults) {
        this.VoteResults = VoteResults;
    }
    
    @PostConstruct
    public void init(){
        poll = readSelectedPollFromFlash();
        VoteResults = getPollResultByPollid(poll.getUuid());
        map = new HashMap<>();
        pieModels = new ArrayList<>();
        createPieModel();
    }

    public Poll getPoll() {
        return poll;
    }
    

    public List<PieChartModel> getPieModels() {
        return pieModels;
    }
    
    
    private Poll readSelectedPollFromFlash(){
        Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
        return (Poll)flash.get("selectedPoll");
    }
    
    private List<VoteResult> getPollResultByPollid(String pollID){
         return polllogic.getPollResultByPollid(pollID);
    }    
    
    private void createPieModel() {     
        for(VoteResult result:VoteResults){
           map.putIfAbsent(result.getItem().getTitle(),new HashMap());   
        }
        for(VoteResult result:VoteResults){
           map.get(result.getItem().getTitle()).putIfAbsent(result.getOption().getShortName(), result.getNumberOfVotes());   
        }
        
        for(Map.Entry<String, Map<String, Integer>> entry : map.entrySet()){
            PieChartModel pieChartModel = new PieChartModel();
            for(Map.Entry<String, Integer> e: entry.getValue().entrySet()){
                pieChartModel.set(e.getKey(), e.getValue());
            }
            pieChartModel.setTitle(entry.getKey());
            pieChartModel.setLegendPosition("w");
            pieChartModel.setShadow(false);
            pieModels.add(pieChartModel);
        }
    }
    
    public void changePublishedState(){
        polllogic.setPollPublished(poll.getUuid(), poll.isResultPublished());
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info!", "Changes was saved"));
    }
    
    
}
