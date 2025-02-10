package com.devweb2.project.tasks.model.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.devweb2.project.tasks.exceptions.ApiRequestException;
import com.devweb2.project.tasks.model.entity.Card;
import com.devweb2.project.tasks.model.entity.CardStatus;
import com.devweb2.project.tasks.model.entity.User;
import com.devweb2.project.tasks.model.kafkaEntitites.DoneAlert;
import com.devweb2.project.tasks.model.repository.CardRepository;
import com.devweb2.project.tasks.model.repository.UserRepository;

@Service
public class CardService {
    
    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public Card findById(Long id){
        return cardRepository.findById(id).orElseThrow(
            () -> new ApiRequestException("Card not found or does not exists! Please check the id", HttpStatus.NOT_FOUND) {});
    }

    public Card save(Card card){
        card.setCreationDate(LocalDateTime.now());
        return cardRepository.save(card);
    }

    public Card addUser(Long cardId, Long userId){
        Optional<Card> cardOptional = cardRepository.findById(cardId);
        if (cardOptional.isEmpty()) {
            throw new ApiRequestException("Card not found or does not exists!", HttpStatus.NOT_FOUND);
        }

        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new ApiRequestException("User not found or does not exists!", HttpStatus.NOT_FOUND);
        }

        Card card = cardOptional.get();
        User user = userOptional.get();

        card.getUsers().add(user);

        return cardRepository.save(card);
    }

    public Card removeUser(Long cardId, Long userId){
        Optional<Card> cardOptional = cardRepository.findById(cardId);
        if (cardOptional.isEmpty()) {
            throw new ApiRequestException("Card not found or does not exists!", HttpStatus.NOT_FOUND);
        }

        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new ApiRequestException("User not found or does not exists!", HttpStatus.NOT_FOUND);
        }

        Card card = cardOptional.get();
        List<User> usersList = card.getUsers().stream()
                                                .filter(u -> u.getId() != userId)
                                                .collect(Collectors.toList());
        
        card.setUsers(usersList);
        return cardRepository.save(card);
    }

    public void delete(Long id){
        Optional<Card> cardOptional = cardRepository.findById(id);
        if (cardOptional.isEmpty()) {
            throw new ApiRequestException("Card not found or does not exists!", HttpStatus.NOT_FOUND);
        }
        cardRepository.deleteById(id);
    }

    public Card update(Card card){

        Card cardUpdate = findById(card.getId());
        cardUpdate.setDescription(card.getDescription());
        cardUpdate.setStatus(card.getStatus());

        if (cardUpdate.getStatus() == CardStatus.DONE) {
            Hibernate.initialize(cardUpdate.getUsers());

            LocalDateTime endDate = LocalDateTime.now();
            cardUpdate.setEndDate(endDate);

            try {
                for (User u : cardUpdate.getUsers()) {
                    DoneAlert doneAlert = new DoneAlert();
                    doneAlert.setMessage("Atividade " + card.getId().toString()+ " finalizada em: " + endDate.toString());
                    doneAlert.setMail(u.getEmail());
                    
                    kafkaTemplate.send("notifier", UUID.randomUUID().toString(), doneAlert);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return cardRepository.save(cardUpdate);
    }

    public List<Card> findAll(){
        return cardRepository.findAll();
    }
}
