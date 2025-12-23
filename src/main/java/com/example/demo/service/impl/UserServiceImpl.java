package com.example.demo.service.impl;

import com.example.demo.dto.AuthResponse;
import com.example.demo.dto.AuthRequest;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtProvider jwtProvider;

    public AuthResponse login(AuthRequest req) {
        User user = userRepository.findByEmail(req.getEmail())
                                  .orElseThrow(() -> new RuntimeException("User not found"));

        // Verify password logic here (assuming plain text for simplicity)
        if (!user.getPassword().equals(req.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        AuthResponse response = new AuthResponse();
        response.setId(user.getId().toString()); // convert Long to String if needed
        response.setEmail(user.getEmail());

        // Convert Set<Role> to Set<String>
        Set<String> roles = user.getRoles()
                                .stream()
                                .map(role -> role.getName())
                                .collect(Collectors.toSet());
        response.setRoles(roles);

        // Generate JWT token
        String token = jwtProvider.generateToken(user.getEmail(), null, 3600000); // 1 hour expiration
        response.setToken(token);

        return response;
    }
}
