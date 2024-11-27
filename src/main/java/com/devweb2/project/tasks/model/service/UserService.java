package com.devweb2.project.tasks.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devweb2.project.tasks.model.entity.User;
import com.devweb2.project.tasks.model.repository.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    public User findById(Long id){
       return userRepository.findById(id).orElse(null);
    }

    public User save(User user){
        return userRepository.save(user);
    }

    public void delete(Long id){
        userRepository.deleteById(id);
    }

    public User update(User user){
        User userUpdate = userRepository.findById(user.getId()).orElse(null);
        userUpdate.setName(user.getName());
        userUpdate.setRole(user.getRole());
        return userRepository.save(userUpdate);
    }
}
