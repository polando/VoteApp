/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee19.web;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import jee19.logic.PollLogic;
import jee19.logic.dto.Person;

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
    
    public void checkToken(){
         Person loggedInUser = loginBean.getUser();
         
         System.out.println(polllogic.checkToken(loginBean.getUser().getUuid(),token));
    }
    
    
}
