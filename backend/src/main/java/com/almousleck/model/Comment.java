package com.almousleck.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "comments")
public class Comment extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;

    // relationship classes
    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    @JsonIgnore
    private Post post;
    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    public Comment() {
    }

    public Comment(Post post, User author, String content) {
        this.post = post;
        this.author = author;
        this.content = content;
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

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}
