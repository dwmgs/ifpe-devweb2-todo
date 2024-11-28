package com.devweb2.project.tasks.model.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;

import com.devweb2.project.tasks.model.entity.Card;
import com.devweb2.project.tasks.model.entity.CardStatus;
import com.devweb2.project.tasks.model.entity.User;
import com.devweb2.project.tasks.model.repository.CardRepository;
import com.devweb2.project.tasks.model.repository.UserRepository;

@Service
public class CardService {
    
    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private UserRepository userRepository;

    /*
     * 
     * aplicar tratamento personalizado de excessões
     */
    public Card findById(Long id){
        return cardRepository.findById(id).orElseThrow(
            () -> new HttpStatusCodeException(HttpStatus.NOT_FOUND) {});
    }

    public Card save(Card card){
        card.setCreationDate(LocalDateTime.now());
        return cardRepository.save(card);
    }

    public Card addUser(Long cardId, Long userId){
        Optional<Card> cardOptional = cardRepository.findById(cardId);
        if (cardOptional.isEmpty()) {
            throw new IllegalArgumentException("Card not found with id: " + cardId);
        }

        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("User not found with id: " + userId);
        }

        Card card = cardOptional.get();
        User user = userOptional.get();

        card.getUsers().add(user);

        return cardRepository.save(card);
    }

    public void delete(Long id){
        cardRepository.deleteById(id);
    }

    public Card update(Card card){
        Card cardUpdate = cardRepository.findById(card.getId()).orElse(null);
        cardUpdate.setDescription(card.getDescription());
        cardUpdate.setStatus(card.getStatus());

        if (cardUpdate.getStatus() == CardStatus.DONE) {
            cardUpdate.setEndDate(LocalDateTime.now());
        }

        return cardRepository.save(cardUpdate);
    }

}
