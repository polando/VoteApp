/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foxtrot.jee19.utilities;

import java.util.HashMap;
import javax.ejb.EJB;
import javax.ejb.Singleton;

/**
 *
 * @author user
 */
@Singleton
public class Notification {

    @EJB
    private MailBean mailBean;

    public boolean sendNotificationToParticioants(HashMap emailInfo)
    {
        HashMap preparedMail = prepareEmailContent(emailInfo);
        mailBean.send(preparedMail);
        return true;
    }

    private HashMap prepareEmailContent(HashMap emailInfo)
    {
        String contentHtml = "Invitation to participate in "+emailInfo.get("title")+" vote\n" +
                "    You can use this token to access vote => " + emailInfo.get("token")
                +" start date: " + emailInfo.get("start_date")+"\n"
                +" end date:" + emailInfo.get("end_date");
        emailInfo.put("content", contentHtml);
        return emailInfo;
    }
}
