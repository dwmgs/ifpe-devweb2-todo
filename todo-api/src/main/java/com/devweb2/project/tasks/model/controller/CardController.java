package com.devweb2.project.tasks.model.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devweb2.project.tasks.model.entity.Card;
import com.devweb2.project.tasks.model.service.CardService;


@RestController
@RequestMapping(value = "/api/card/")
public class CardController {
    
    @Autowired
    private CardService cardService;
    
    @GetMapping(path = "/findById", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Card> findById(@RequestParam Long id){
        return ResponseEntity.ok(cardService.findById(id));
    }

    @PostMapping(path = "/save", produces = MediaType.APPLICATION_JSON_VALUE , consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Card> save(@RequestBody Card card){
        return ResponseEntity.ok(cardService.save(card));
    }
   
    @DeleteMapping(path = "/delete")
    public ResponseEntity<?> delete(@RequestParam Long id){
        cardService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(path = "/update", produces = MediaType.APPLICATION_JSON_VALUE , consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Card> update(@RequestBody Card card){
        return ResponseEntity.ok(cardService.update(card));
    }

    
    @PutMapping(path = "/addUser", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Card> addUser(@RequestParam Long cardId, @RequestParam Long userId){
        return ResponseEntity.ok(cardService.addUser(cardId, userId));
    }

    @PutMapping(path = "/removeUser", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Card> removeUser(@RequestParam Long cardId, @RequestParam Long userId){
        return ResponseEntity.ok(cardService.removeUser(cardId, userId));
    }

}
