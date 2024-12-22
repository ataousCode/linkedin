package com.almousleck.controller;

import com.almousleck.dto.CommentDto;
import com.almousleck.model.Comment;
import com.almousleck.model.User;
import com.almousleck.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/{postId}/add-comment")
    public ResponseEntity<Comment> addComment(@PathVariable Long postId,
                                              @RequestBody CommentDto commentDto,
                                              @RequestAttribute("authenticatedUser") User user) {
        Comment comment = commentService.addComment(postId, user.getId(), commentDto.getContent());
        return ResponseEntity.ok(comment);
    }

    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId,
                                              @RequestAttribute("authenticatedUser") User user) {
        commentService.deleteComment(commentId, user.getId());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update/{commentId}")
    public ResponseEntity<Comment> editComment(@PathVariable Long commentId,
                                               @RequestBody CommentDto commentDto,
                                               @RequestAttribute("authenticatedUser") User user) {
        Comment comment = commentService.editComment(commentId, user.getId(), commentDto.getContent());
        return ResponseEntity.ok(comment);
    }
}
