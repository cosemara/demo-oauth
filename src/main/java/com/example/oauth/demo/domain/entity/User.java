package com.example.oauth.demo.domain.entity;

import com.example.oauth.demo.type.AuthProvider;
import com.example.oauth.demo.type.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

import static java.util.Objects.nonNull;

@Getter
@Setter
@Entity
@Table(name = "user")
@NoArgsConstructor
public class User extends BaseEntity {

    @Id
    @Column(name = "user_id")
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String username;

    @JsonIgnore
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private UserRole role;

    @Column(nullable = false)
    private Boolean emailVerified = false;

    @Column
    private LocalDateTime deletedDate;

    @Column
    private String providerId;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    public User(String username, String email, LocalDateTime deletedDate) {
        this.username = username;
        this.email = email;
        this.deletedDate = deletedDate;
    }

    @Builder
    public User(Long id, String email, String username, String password, UserRole role, Boolean emailVerified, LocalDateTime deletedDate, String providerId, AuthProvider provider) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
        this.emailVerified = emailVerified;
        this.deletedDate = deletedDate;
        this.providerId = providerId;
        this.provider = provider;
    }

    public boolean isUnregister() {
        return nonNull(deletedDate);
    }

    public void unregister() {
        this.deletedDate = LocalDateTime.now();
    }

    public User update(String name) {
        this.username = name;
        return this;
    }
}