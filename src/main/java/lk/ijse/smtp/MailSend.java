package lk.ijse.smtp;

import java.io.File;

public class MailSend {
    public static void sendOrderConformMail(String to, String subject, String text){
        mail gEmailSender = new mail();
        String from = "chamikadamith9@gmail.com";

        boolean b = gEmailSender.sendEmail(to, from, subject, text);
        if (b) {
            System.out.println("Email is sent successfully");
        } else {
            System.out.println("There is problem in sending email");
        }
    }

    public static void sendOrderConformMail(String to,String subject, File file){
        mail gEmailSender = new mail();
        String from = "chamikadamith9@gmail.com";

        boolean b = gEmailSender.sendEmail(to, from,subject, file);
        if (b) {
            System.out.println("Email is sent successfully");
        } else {
            System.out.println("There is problem in sending email");
        }
    }
}
