package com.devweb2.project.tasks.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.devweb2.project.tasks.model.entity.Card;

public interface CardRepository extends JpaRepository<Card, Long> {

    @Query("SELECT DISTINCT c FROM Card c JOIN c.users u WHERE u.id = :userId")
    List<Card> findAllByUserId(@Param("userId") Long userId);
}
