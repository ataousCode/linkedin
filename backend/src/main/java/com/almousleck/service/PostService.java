package com.almousleck.service;

import com.almousleck.dto.PostDto;
import com.almousleck.model.Post;
import com.almousleck.model.User;
import com.almousleck.repository.PostRepository;
import com.almousleck.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository,
                       UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public Post createPost(PostDto postDto, Long authorId) {
        // authenticated user
        User author = userRepository.findById(authorId)
                .orElseThrow(() -> new IllegalArgumentException("User with id " + authorId + " not found"));
        Post post = new Post(postDto.getContent(), author);
        post.setPicture(postDto.getPicture());
        post.setLikes(new HashSet<>());
        return postRepository.save(post);
    }

    public Post getPost(Long postId) {
        return postRepository.findById(postId).orElseThrow(()
                -> new IllegalArgumentException("Post not found"));
    }

    public List<Post> getAllPosts() {
        return postRepository.findAllByOrderByCreationDateDesc();
    }

    public List<Post> getPostsByUserId(Long userId) {
        return postRepository.findByAuthorId(userId);
    }

    public List<Post> getFeedPosts(Long authenticatedUserId) {
        return postRepository.findByAuthorIdNotOrderByCreationDateDesc(authenticatedUserId);
    }

    public Post editPost(Long postId, Long authorId, PostDto postDto) {
        Post post = getPost(postId);
        User user = userRepository.findById(authorId)
                .orElseThrow(() -> new IllegalArgumentException("User with id " + authorId + " not found"));
        if (!post.getAuthor().equals(user))
            throw new IllegalArgumentException("You are not allowed to edit this post");
        post.setContent(postDto.getContent());
        post.setPicture(postDto.getPicture());
        return postRepository.save(post);
    }

    public void deletePost(Long postId, Long authorId) {
        Post post = getPost(postId);
        User user = userRepository.findById(authorId)
                .orElseThrow(() -> new IllegalArgumentException("User with id " + authorId + " not found"));
        if (!post.getAuthor().equals(user))
            throw new IllegalArgumentException("You are not allowed to delete this post");
        postRepository.delete(post);
    }

    public Post likePost(Long postId, Long userId) {
        Post post = getPost(postId);
        User user = userRepository.findById(userId).orElseThrow(()
                -> new IllegalArgumentException("User not found"));
        if (post.getLikes().contains(user))
            post.getLikes().remove(user);
        else
            post.getLikes().add(user);
        return postRepository.save(post);
    }

}
