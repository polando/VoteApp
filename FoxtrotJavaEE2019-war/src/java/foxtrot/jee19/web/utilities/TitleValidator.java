/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foxtrot.jee19.web.utilities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import foxtrot.jee19.logic.PollLogic;

/**
 *
 * @author ussocom
 */
@Named
@ViewScoped
public class TitleValidator implements Validator, Serializable {

    private static final long serialVersionUID = 5793355967891360652L;

    @EJB
    private PollLogic polllogic;

    @Override
    public void validate(FacesContext fc, UIComponent uic, Object o) throws ValidatorException {
        List<String> titleList = polllogic.getAllPollTitles();
        if (titleList != null) {
            String pollID = (String) uic.getAttributes().get("pollID");
            System.out.println("pollID"+pollID);
            if (pollID != null) {
                String thisPollTitle = polllogic.getPollByPollUUID(pollID).getTitle();
                System.out.println("thisPollTitle"+thisPollTitle);
                titleList.removeIf(n -> n.equalsIgnoreCase(thisPollTitle));
            }
            if (!titleList.isEmpty()) {
                boolean exist = titleList.stream().anyMatch(o.toString()::equalsIgnoreCase);
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
