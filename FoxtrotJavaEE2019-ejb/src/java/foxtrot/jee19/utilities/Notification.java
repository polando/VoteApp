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

    public boolean sendNotificationToParticioants(String title,String start_date,String end_date, String token,String email,String number_of_participants)
    {
        HashMap emailInfo = new HashMap();
        emailInfo.put("token", token);
        emailInfo.put("email", email);
        emailInfo.put("title", title);
        emailInfo.put("start_date", start_date);
        emailInfo.put("end_date", end_date);
        emailInfo.put("number_of_participants",  number_of_participants);
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
