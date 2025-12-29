package com.example.demo.service.impl;

import com.example.demo.dto.*;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtProvider;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Override
    public User register(UserRegisterDto dto) {
        Set<Role> roles = dto.getRoles()
                .stream()
                .map(r -> Role.valueOf(r.toUpperCase()))
                .collect(Collectors.toSet());

        User user = User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .roles(roles)
                .build();

        return userRepository.save(user);
    }

    @Override
    public AuthResponse login(AuthRequest request) {
        User user = getByEmail(request.getEmail());

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        Set<String> roleNames = user.getRoles()
                .stream()
                .map(Role::name)
                .collect(Collectors.toSet());

        String token = jwtProvider.generateToken(
                user.getId(),
                user.getEmail(),
                roleNames
        );

        return AuthResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .roles(roleNames)
                .token(token)
                .build();
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found with email: " + email)
                );
    }
}
