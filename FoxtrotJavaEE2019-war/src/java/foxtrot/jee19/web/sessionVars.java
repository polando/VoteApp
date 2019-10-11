/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foxtrot.jee19.web;

import java.io.Serializable;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author ussocom
 */
@ManagedBean
@SessionScoped
public class sessionVars implements Serializable  {
    
    private static final long serialVersionUID = -8372858764815895715L;

         
    public void setVals(){
        System.out.println("set vars called");
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String timeOffest = params.get("timeOffest");
        sessionMap.put("timeOffest", timeOffest);
    }
    
}
