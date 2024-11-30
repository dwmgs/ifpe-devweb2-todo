package com.devweb2.project.tasks.model.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devweb2.project.tasks.model.entity.Comment;
import com.devweb2.project.tasks.model.service.CommentService;



@RestController
@RequestMapping(value = "/api/comment/")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/findById")
    public ResponseEntity<Comment> findById(@RequestParam Long id) {
        return ResponseEntity.ok(commentService.findById(id));
    }
    
    @PostMapping("/addComment")
    public ResponseEntity<Comment> addComment(@RequestParam Long cardId, @RequestParam Long userId,@RequestBody String content) {
        Comment comment = commentService.addCommentToCard(cardId, userId, content);
        return ResponseEntity.ok(comment);
    }

    @PutMapping("/update")
    public ResponseEntity<Comment> putMethodName(@RequestParam Long id, @RequestBody String content){
        return ResponseEntity.ok(commentService.updateComment(id, content));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam Long id){
        commentService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
