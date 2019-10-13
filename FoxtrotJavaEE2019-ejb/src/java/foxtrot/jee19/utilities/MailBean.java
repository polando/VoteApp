/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foxtrot.jee19.utilities;

import java.util.HashMap;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author user
 */
@Singleton
public class MailBean {

    @Resource(name="mail/mailtrap")
    private Session session;

    public void send(HashMap emailInfo) {
        Message message = new MimeMessage(session);
        try {
            message.setSubject(emailInfo.get("title").toString());
            message.setText(
                    emailInfo.get("content").toString()
            );
            message.setRecipients(MimeMessage.RecipientType.TO, InternetAddress.parseHeader(emailInfo.get("email").toString(), true));
            Transport.send(message);
            
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
