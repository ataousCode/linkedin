package com.almousleck.controller;

import com.almousleck.dto.PostDto;
import com.almousleck.model.Post;
import com.almousleck.model.User;
import com.almousleck.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<List<Post>> getPosts(@RequestAttribute("authenticatedUser") User user) {
        List<Post> posts = postService.getFeedPosts(user.getId());
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    @PostMapping("/create")
    public ResponseEntity<Post> createPost(@RequestBody PostDto postDto,
                                           @RequestAttribute("authenticatedUser")User user) {
        Post post = postService.createPost(postDto, user.getId());
        return ResponseEntity.ok(post);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<Post> getPost(@PathVariable Long postId) {
        Post post = postService.getPost(postId);
        return ResponseEntity.ok(post);
    }

    @PutMapping("/update/{postId}")
    public ResponseEntity<Post> editPost(@PathVariable Long postId,
                                         @RequestBody PostDto postDto,
                                         @RequestAttribute("authenticatedUser") User user) {
        Post post = postService.editPost(postId, user.getId(), postDto);
        return ResponseEntity.ok(post);
    }

    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId,
                                           @RequestAttribute("authenticatedUser") User user) {
        postService.deletePost(postId, user.getId());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{postId}/likes")
    public ResponseEntity<Post> likePost(@PathVariable Long postId,
                                         @RequestAttribute("authenticatedUser") User user) {
        Post post = postService.likePost(postId, user.getId());
        return ResponseEntity.ok(post);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Post>> getPostsByUserId(@PathVariable Long userId) {
        List<Post> posts = postService.getPostsByUserId(userId);
        return ResponseEntity.ok(posts);
    }
}
