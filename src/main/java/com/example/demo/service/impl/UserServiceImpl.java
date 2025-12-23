package com.example.demo.service.impl;

import com.example.demo.dto.*;
import com.example.demo.model.*;
import com.example.demo.repository.UserRepository;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.security.JwtProvider;
import com.example.demo.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repo;
    private final PasswordEncoder encoder;
    private final JwtProvider jwtProvider;

    public UserServiceImpl(UserRepository repo, PasswordEncoder encoder, JwtProvider jwtProvider) {
        this.repo = repo;
        this.encoder = encoder;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public User register(UserRegisterDto dto) {
        if (dto.getName() == null || dto.getName().isBlank())
            throw new IllegalArgumentException("name cannot be empty");
        if (dto.getPassword() == null || dto.getPassword().isBlank())
            throw new IllegalArgumentException("password cannot be empty");
        if (repo.findByEmail(dto.getEmail()).isPresent())
            throw new IllegalArgumentException("email must be unique");

        Set<Role> roles = new HashSet<>();
        if (dto.getRoles() != null && !dto.getRoles().isEmpty())
            roles = dto.getRoles().stream().map(Role::valueOf).collect(Collectors.toSet());
        else roles.add(Role.ROLE_USER);

        User user = User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .password(encoder.encode(dto.getPassword()))
                .roles(roles)
                .createdAt(LocalDateTime.now())
                .build();

        return repo.save(user);
    }

    @Override
    public AuthResponse login(AuthRequest req) {
        User user = repo.findByEmail(req.getEmail()).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!encoder.matches(req.getPassword(), user.getPassword()))
            throw new IllegalArgumentException("Invalid password");

        String token = jwtProvider.generateToken(user.getId(), user.getEmail(), user.getRoles());

        AuthResponse response = new AuthResponse();
        response.setToken(token);
        response.setUserId(user.getId());
        response.setEmail(user.getEmail());
        response.setRoles(user.getRoles().stream().map(Enum::name).collect(Collectors.toSet()));
        return response;
    }

    @Override
    public User getByEmail(String email) {
        return repo.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}
