package com.devweb2.project.tasks.model.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;

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
            () -> new HttpStatusCodeException(HttpStatus.NOT_FOUND) {});
    }

    public Comment addCommentToCard(Long cardId, Long userId, String content){
        Optional<Card> cardOptional = cardRepository.findById(cardId);
        if (cardOptional.isEmpty()) {
            throw new IllegalArgumentException("Card not found");
        }

        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("User not found");
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
            throw new IllegalArgumentException("Comment not found");
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
            throw new IllegalArgumentException("Comment not found");
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
