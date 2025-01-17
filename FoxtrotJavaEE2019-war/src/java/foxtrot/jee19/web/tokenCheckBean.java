/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foxtrot.jee19.web;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import foxtrot.jee19.logic.PollLogic;
import foxtrot.jee19.logic.PollState;
import foxtrot.jee19.logic.dto.Item;
import foxtrot.jee19.logic.dto.Person;
import foxtrot.jee19.logic.dto.Poll;

/**
 *
 * @author ussocom
 */
@ViewScoped
@Named
public class tokenCheckBean implements Serializable {
    
    private static final long serialVersionUID = 2038742079072597732L;
    
    private String token;
    
    @EJB
    private PollLogic polllogic;
    
    @Inject
    private LoginBean loginBean;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    
    private boolean checkToken(){
         Person loggedInUser = loginBean.getUser();
         return polllogic.checkToken(loginBean.getUser().getUuid(),token);
    }
    
    private boolean tokenExistAndNotUsed(){
       return polllogic.tokenExistAndNotUsed(token);
    }
    
    private Poll getPollIfAllowed(){
        Poll poll = null;
        if(checkToken()){
             poll = polllogic.getPollByToken(token);
             if(!poll.getPollState().equals(PollState.VOTING)){
                 return null;
             }
        }
        return poll;
    }
    
    public String goToVoting(){
        if(tokenExistAndNotUsed()){
        Poll poll = getPollIfAllowed();
        if(poll != null){
            Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
            flash.put("poll", poll);
            flash.put("token", token);
            return "confirmed";
            }
        else{
             return "failed";
            }
        }
        else{
            return "failed";
        }
    }
}

