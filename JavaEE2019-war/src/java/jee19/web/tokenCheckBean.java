/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee19.web;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import jee19.logic.PollLogic;
import jee19.logic.dto.Person;
import jee19.logic.dto.Poll;

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
    
    private Poll getPollIfAllowed(){
        Poll poll = null;
        if(checkToken()){
            System.out.println("true");
             poll = polllogic.getPollByToken(token);
             System.out.println("after poll"+poll.getTitle());
        }
        return poll;
    }
    
    public String goToVoting(){
        Poll poll = getPollIfAllowed();
        if(poll != null){
            System.out.println("successsss");
            Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
            flash.put("poll", poll);
            return "confirmed";
        }
        else{
            return "failed";
        }
    }
}

