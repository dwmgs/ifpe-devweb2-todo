package com.devweb2.project.notifier.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.devweb2.project.notifier.mail.JavaMail;

@Component
public class Notifier {
    
    @KafkaListener(topics = "mail_topic", groupId = "default_group")
    public void processContract(String m){
        System.out.println(m);
        JavaMail.mailSender("Status de atividade modificado ", m, "linsbvl73@gmail.com", false);
    }
}
