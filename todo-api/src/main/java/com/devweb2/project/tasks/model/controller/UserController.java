package com.devweb2.project.tasks.model.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devweb2.project.tasks.model.entity.User;
import com.devweb2.project.tasks.model.service.UserService;

@RestController
@RequestMapping(value = "/api/user/")
public class UserController {
    
    @Autowired
    private UserService userService;

    @GetMapping(path = "/findById", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> findById(@RequestParam Long id){
        return ResponseEntity.ok(userService.findById(id));
    }

    @PostMapping(path = "/save", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> save(@RequestBody User user){
        return ResponseEntity.ok(userService.save(user));
    }
    
    @DeleteMapping(path = "/delete")
    public ResponseEntity<?> delete(@RequestParam Long id){
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(path = "/update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> update(@RequestBody User user){
        return ResponseEntity.ok(userService.update(user));
    }

    @GetMapping(path = "/findAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> findAll(){
        return ResponseEntity.ok(userService.findAll());
    }
    
}
