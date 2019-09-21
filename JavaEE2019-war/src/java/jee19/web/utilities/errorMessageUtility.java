/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee19.web.utilities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.PrimeFaces;

/**
 *
 * @author ussocom
 */
@ViewScoped
@Named
public class errorMessageUtility implements Serializable {

    private static final long serialVersionUID = -7002842622399180553L;

    public void errorCall(ArrayList<String> problems) {
        StringBuilder sb = new StringBuilder();
        FacesMessage message = new FacesMessage();
        message.setSeverity(FacesMessage.SEVERITY_ERROR);
        message.setSummary("Errors");
        sb.append("You had following errors:");
        sb.append("\n");
        for (String problem : problems) {
            sb.append(problem);
            sb.append("\n");
        }
        message.setDetail(sb.toString());
        FacesContext ctx = FacesContext.getCurrentInstance();
        ctx.addMessage(null, message);
        ctx.renderResponse();
        PrimeFaces current = PrimeFaces.current();
        current.dialog().showMessageDynamic(message);
    }
    
    public boolean isNullOrEmpty(String input){
       return (input == null || input.isEmpty());
    }
    
    public boolean isNullOrEmpty(List input){
        return (input == null || input.isEmpty());
    }
    
    public boolean isNullOrEmpty(Date input){
        return (input == null);
    }
}
