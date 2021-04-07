package com.revature.auth.utils;


import com.revature.auth.dtos.UserDTO;
import com.revature.auth.entities.User;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Set;

public class EmailUtil {

    private static String email = "AssignForceNotify@gmail.com";
    private static String password = System.getenv("AF_EMAIL_SECRET");
    private static String server = "smtp.gmail.com";

    public static boolean notifyUser(UserDTO userDTO, String url){

        Properties properties = new Properties();
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.host", server);
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getDefaultInstance(properties);
        session.setDebug(true);

        try{
            Transport trans = session.getTransport("smtp");
            trans.connect(server, 587, email, password);


            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(email));
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(userDTO.getEmail()));
            message.setSubject("Registration Review");
            message.setText("Hello, this is an email stating your application has been reviewed.\nHere is a confirmation link: " + url
            + "\nEmail: " + userDTO.getEmail() + "\nTemporary password: " + userDTO.getPassword());

            trans.sendMessage(message, message.getAllRecipients());
            return true;

        }catch (MessagingException e) {
            return false;

        }
    }

    public static boolean notifyAdmins(Set<UserDTO> admins){
        System.out.println(email);
        System.out.println(password);

        Properties properties = new Properties();
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.host", server);
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getDefaultInstance(properties);
        session.setDebug(true);

        try{
            Transport trans = session.getTransport("smtp");
            trans.connect(server, 587, email, password);

            for (UserDTO userDTO : admins) {
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress(email));
                message.addRecipient(Message.RecipientType.TO,new InternetAddress(userDTO.getEmail()));
                message.setSubject("New User Registered");
                message.setText("Hello, this is an email stating a new user has attempted to register and whose account needs to be reviewed.");
                trans.sendMessage(message, message.getAllRecipients());
            }

            return true;

        }catch (MessagingException e) {
            return false;

        }
    }

}
