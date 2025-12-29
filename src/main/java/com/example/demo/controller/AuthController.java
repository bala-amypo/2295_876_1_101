package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/register")
    public String register(@RequestBody Object body) {
        return "registered";
    }

    @PostMapping("/login")
    public String login(@RequestBody Object body) {
        return "token";
    }
}
