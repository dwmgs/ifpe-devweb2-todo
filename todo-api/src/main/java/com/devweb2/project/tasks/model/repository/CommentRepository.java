package com.devweb2.project.tasks.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devweb2.project.tasks.model.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long>{} 
