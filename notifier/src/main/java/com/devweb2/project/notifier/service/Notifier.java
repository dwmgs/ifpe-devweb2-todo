package com.devweb2.project.notifier.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.devweb2.project.notifier.entities.DoneAlert;
import com.devweb2.project.notifier.mail.JavaMail;

@Component
public class Notifier {

    private static final Logger logger = LoggerFactory.getLogger(Notifier.class);
    
    @KafkaListener(topics = "notifier", groupId = "default")
    public void processContract(DoneAlert m){
        try {
            JavaMail.mailSender("Status de atividade modificado ", m.getMessage(), m.getMail(), false);
        } catch (Exception e) {
            logger.error("Erro ao processar mensagem no consumidor: ", e);
        }
    }
}
