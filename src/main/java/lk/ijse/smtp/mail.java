package lk.ijse.smtp;
import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.activation.FileDataSource;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.io.File;
import java.util.Properties;

public class mail {


    public boolean sendEmail(String to, String from, String subject, String text) {
        boolean flag = false;

        //logic
        //smtp properties
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.starttls.enable", true);
        properties.put("mail.smtp.port", 587);
        properties.put("mail.smtp.host", "smtp.gmail.com");



        //session
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("chamikadamith9@gmail.com", "eust hzor bpbf rxkh");
            }
        });

        try {

            Message message = new MimeMessage(session);
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setFrom(new InternetAddress(from));
            message.setSubject(subject);
            message.setText(text);
            Transport.send(message);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Exception: " + e.getMessage());
        }


        return flag;
    }

    public boolean sendEmailFile(String to, String from,String subject, File file) {

        boolean flag = false;

        //logic
        //smtp properties
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.starttls.enable", true);
        properties.put("mail.smtp.port", 587);
        properties.put("mail.smtp.host", "smtp.gmail.com");



        //session
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("chamikadamith9@gmail.com", "eust hzor bpbf rxkh");
            }
        });

        try {

            Message message = new MimeMessage(session);

            DataSource source = new FileDataSource(file);
            message.setDataHandler(new DataHandler(source));
            message.setFileName("Payment Receipt.pdf");

            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setFrom(new InternetAddress(from));
            message.setSubject(subject);
            Transport.send(message);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Exception: " + e.getMessage());
        }


        return flag;
    }

}

