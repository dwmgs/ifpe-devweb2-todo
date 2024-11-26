package com.devweb2.project.tasks.model.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "COMMENT")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;

    private String content;
    private LocalDateTime date;

    public Long getId() {
        return id;
    }
    public Comment setId(Long id) {
        this.id = id;
        return this;
    }
    public User getUser() {
        return user;
    }
    public Comment setUser(User user) {
        this.user = user;
        return this;
    }
    public Card getCard() {
        return card;
    }
    public Comment setCard(Card card) {
        this.card = card;
        return this;
    }
    public String getContent() {
        return content;
    }
    public Comment setContent(String content) {
        this.content = content;
        return this;
    }
    public LocalDateTime getDate() {
        return date;
    }
    public Comment setDate(LocalDateTime date) {
        this.date = date;
        return this;
    }
}
