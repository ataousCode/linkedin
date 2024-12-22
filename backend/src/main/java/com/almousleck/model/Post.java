package com.almousleck.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "posts")
public class Post extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    private String content;
    private String picture;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @ManyToMany
    @JoinTable(
            name = "posts_likes",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> likes;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    public Post(String content, User author) {
        this.content = content;
        this.author = author;
    }

    public Post() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Set<User> getLikes() {
        return likes;
    }

    public void setLikes(Set<User> likes) {
        this.likes = likes;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

}
