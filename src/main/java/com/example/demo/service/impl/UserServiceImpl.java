package com.example.demo.service.impl;

import org.springframework.stereotype.Service;
import com.example.demo.model.User;
import com.example.demo.dto.AuthResponse;

@Service
public class UserServiceImpl {

    public AuthResponse generateAuthResponse(User user, String token) {
        AuthResponse response = new AuthResponse();
        response.setId(String.valueOf(user.getId())); // convert Long to String
        response.setEmail(user.getEmail());
        response.setRoles(user.getRoles());
        response.setToken(token);
        return response;
    }
}
