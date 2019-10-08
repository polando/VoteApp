/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foxtrot.jee19.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ValueChangeListener;
import javax.inject.Named;

/**
 *
 * @author ussocom
 */
@ManagedBean
@SessionScoped
public class changeLanguageBean implements Serializable  {

    private static final Map<String, Object> locales;
    private static final long serialVersionUID = -5127899606637781663L;
    private String localeCode;
    private Locale locale; 

    public String getLocaleCode() {
        return localeCode;
    }

    public void setLocaleCode(String localeCode) {
        this.localeCode = localeCode;
    }

    static {
        locales = new LinkedHashMap<>();
        locales.put("English", Locale.ENGLISH);
        locales.put("German", Locale.GERMAN);
    }

    public Set<String> getAllLocales() {
        return locales.keySet();
    }

    public Locale getLocale() {
        return locale;
    }
    
    
    
    public void processValueChange(ValueChangeEvent e){
        String newLocaleValue = e.getNewValue().toString();
        for (Map.Entry<String, Object> entry : locales.entrySet()) {
            if (entry.getKey().equals(newLocaleValue)) {
                locale = (Locale) entry.getValue();
                //FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
            }
        }
    }
}
