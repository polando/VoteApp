/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foxtrot.jee19.web.utilities;

import foxtrot.jee19.logic.PollLogic;
import foxtrot.jee19.logic.dto.DefinedPersonList;
import foxtrot.jee19.web.LoginBean;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author ussocom
 */
@Named
@ViewScoped
public class PersonListValidator implements Validator, Serializable {

    private static final long serialVersionUID = 7698284412762904351L;

    @Inject
    private LoginBean loginBean;

    @EJB
    private PollLogic polllogic;

    @Override
    public void validate(FacesContext fc, UIComponent uic, Object o) throws ValidatorException {
        List<DefinedPersonList> lists = polllogic.getAllPredifinedListByPerson(loginBean.getUser().getUuid());
        if (lists != null) {
            String personListId = (String) uic.getAttributes().get("personListId");
            if (personListId != null) {
                lists.removeIf(n -> n.getUuid().equalsIgnoreCase(personListId));
            }
            if (!lists.isEmpty()) {
                boolean exist = false;
                for (DefinedPersonList plist : lists) {
                    if (plist.getTitle().equalsIgnoreCase(o.toString())) {
                        exist = true;
                    }
                }
                if (exist) {
                    FacesMessage fm = new FacesMessage("This title already exist");
                    FacesContext.getCurrentInstance().addMessage(null, fm);
                    fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(fm);
                }
            }
        }
    }

}
