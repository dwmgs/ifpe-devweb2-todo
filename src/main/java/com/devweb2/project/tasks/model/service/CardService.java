package com.devweb2.project.tasks.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devweb2.project.tasks.model.entity.Card;
import com.devweb2.project.tasks.model.repository.CardRepository;

@Service
public class CardService {
    
    @Autowired
    private CardRepository cardRepository;

    public Card findById(Long id){
        return cardRepository.findById(id).orElse(null);
    }

    public Card save(Card card){
        return cardRepository.save(card);
    }

    public void delete(Long id){
        cardRepository.deleteById(id);
    }

    public Card update(Card card){
        Card cardUpdate = cardRepository.findById(card.getId()).orElse(null);
        cardUpdate.setDescription(card.getDescription());
        cardUpdate.setUsers(card.getUsers());
        cardUpdate.setComments(card.getComments());
        cardUpdate.setStatus(card.getStatus());
        return cardRepository.save(cardUpdate);
    }
}
