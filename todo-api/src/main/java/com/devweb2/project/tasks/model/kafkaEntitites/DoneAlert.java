package com.devweb2.project.tasks.model.kafkaEntitites;

public class DoneAlert {
    
    private String message;
    private String mail;

    public String getMessage() {
        return message;
    }
    public DoneAlert setMessage(String message) {
        this.message = message;
        return this;
    }
    public String getMail() {
        return mail;
    }
    public DoneAlert setMail(String mail) {
        this.mail = mail;
        return this;
    }
}
