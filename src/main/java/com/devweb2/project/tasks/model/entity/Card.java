package com.devweb2.project.tasks.model.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "CARD")
public class Card {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private LocalDateTime creationDate;
    private LocalDateTime endDate;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_cards",
        joinColumns = @JoinColumn(name = "card_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> users;

    @OneToMany(mappedBy = "card", fetch = FetchType.EAGER)
    private List<Comment> comments;

    @Enumerated(EnumType.STRING)
    private CardStatus status;

    public Long getId() {
        return id;
    }
    public Card setId(Long id) {
        this.id = id;
        return this;
    }
    public String getDescription() {
        return description;
    }
    public Card setDescription(String description) {
        this.description = description;
        return this;
    }
    public LocalDateTime getCreationDate() {
        return creationDate;
    }
    public Card setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
        return this;
    }
    public LocalDateTime getEndDate() {
        return endDate;
    }
    public Card setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
        return this;
    }
    public List<User> getUsers() {
        return users;
    }
    public Card setUsers(List<User> users) {
        this.users = users;
        return this;
    }
    public List<Comment> getComments() {
        return comments;
    }
    public Card setComments(List<Comment> comments) {
        this.comments = comments;
        return this;
    }
    public CardStatus getStatus() {
        return status;
    }
    public Card setStatus(CardStatus status) {
        this.status = status;
        return this;
    }

}
