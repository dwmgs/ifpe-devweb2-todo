package com.devweb2.project.tasks.model.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.devweb2.project.tasks.exceptions.ApiRequestException;
import com.devweb2.project.tasks.model.entity.Card;
import com.devweb2.project.tasks.model.entity.Comment;
import com.devweb2.project.tasks.model.entity.User;
import com.devweb2.project.tasks.model.repository.CardRepository;
import com.devweb2.project.tasks.model.repository.CommentRepository;
import com.devweb2.project.tasks.model.repository.UserRepository;

@Service
public class CommentService {
    
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private UserRepository userRepository;

    public Comment findById(Long id){
        return commentRepository.findById(id).orElseThrow(
            () -> new ApiRequestException("Comment not found or does not exists! Please check the id", HttpStatus.NOT_FOUND) {});
    }

    public Comment addCommentToCard(Long cardId, Long userId, String content){
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

        Comment comment = new Comment()
                                .setCard(card)
                                .setUser(user)
                                .setContent(content)
                                .setDate(LocalDateTime.now());

        return commentRepository.save(comment);

    }

    public Comment updateComment(Long id, String newContent) {
        Optional<Comment> commentOptional = commentRepository.findById(id);
        if (commentOptional.isEmpty()) {
            throw new ApiRequestException("Comment not found or does not exists!", HttpStatus.NOT_FOUND);
        }
    
        Comment comment = commentOptional.get();
        comment.setContent(newContent);
    
        return commentRepository.save(comment);
    }


    /*
     * 
     * corrigir falha que desvincula usuario do cartão
     */
    public void delete(Long id) {
        Optional<Comment> commentOptional = commentRepository.findById(id);
        if (commentOptional.isEmpty()) {
            throw new ApiRequestException("Comment not found or does not exists!", HttpStatus.NOT_FOUND);
        }

        validateExclusion(commentOptional.get(), id);
    
        commentRepository.deleteById(id);
    }

    private void validateExclusion(Comment comment, Long cardId){
        if (!comment.getCard().getId().equals(cardId)) {
            throw new IllegalArgumentException("Comment not found on card");
        }
    }
}
