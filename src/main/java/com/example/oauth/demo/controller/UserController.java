package com.example.oauth.demo.controller;

import com.example.oauth.demo.config.security.CurrentUser;
import com.example.oauth.demo.config.security.UserPrincipal;
import com.example.oauth.demo.domain.entity.User;
import com.example.oauth.demo.domain.repository.UserRepository;
import com.example.oauth.demo.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('FREE')")
    public User getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        return userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
    }
}