package com.almousleck.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
@Builder
public class User extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Boolean emailVerified = false;
    private String emailVerificationToken;
    private LocalDateTime emailVerificationTokenExpiryDate;
    @JsonIgnore
    private String password;
    private String passwordResetToken;
    private LocalDateTime passwordResetTokenExpiryDate;
    private String company;
    private String position;
    private String location;
    private String profilePicture;
    private Boolean profileCompleted = false;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
