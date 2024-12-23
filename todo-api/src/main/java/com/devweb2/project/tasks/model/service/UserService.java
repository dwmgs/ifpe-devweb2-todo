package com.devweb2.project.tasks.model.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.devweb2.project.tasks.exceptions.ApiRequestException;
import com.devweb2.project.tasks.model.entity.User;
import com.devweb2.project.tasks.model.repository.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    public User findById(Long id){
       return userRepository.findById(id).orElseThrow(
            () -> new ApiRequestException("User not found or does not exists! Please check the id", HttpStatus.NOT_FOUND) {});
    }

    public User save(User user){
        return userRepository.save(user);
    }

    public void delete(Long id){
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new ApiRequestException("User not found or does not exists!", HttpStatus.NOT_FOUND);
        }
        
        userRepository.deleteById(id);
    }

    public User update(User user){
        User userUpdate = findById(user.getId());
        userUpdate.setName(user.getName());
        userUpdate.setRole(user.getRole());
        userUpdate.setEmail(user.getEmail());
        return userRepository.save(userUpdate);
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }
}
