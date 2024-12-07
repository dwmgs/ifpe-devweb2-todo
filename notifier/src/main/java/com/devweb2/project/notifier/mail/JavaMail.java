package com.devweb2.project.notifier.mail;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.mail.Address;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class JavaMail {
    private static final Logger logger = LoggerFactory.getLogger(JavaMail.class.getName());

    public static void mailSender(String assunto, String mensagem, String AdressList, Boolean debug) {

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(props,
                new jakarta.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("dvwbii@gmail.com",
                                "fdogsodpizcywhzr");
                    }
                });

        session.setDebug(debug);

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("dvwbii@gmail.com", " - Mail Sender"));
            // Remetente

            Address[] toUsers = InternetAddress // Destinatário(s)
                    .parse(AdressList);

            message.setRecipients(Message.RecipientType.TO, toUsers);
            message.setSubject(assunto);// Assunto
            message.setText(mensagem);
            // Método para enviar a mensagem criada 
            Transport.send(message);

        } catch (MessagingException e) {
            logger.warn("Erro ao enviar Email - " + e.getMessage());
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            logger.warn("Erro ao enviar Email - " + e.getMessage());
            throw new RuntimeException(e);
        } catch (Exception e) {
            logger.warn("Erro ao enviar Email - " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
