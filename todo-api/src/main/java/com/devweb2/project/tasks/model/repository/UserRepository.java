package com.devweb2.project.tasks.model.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.devweb2.project.tasks.model.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailIgnoreCase(String email);

    @Query("SELECT u FROM User u " +
            "WHERE (u.id = :id)")
    List<User> findAllByUserId(Long id);
}
