package com.example.demo.dto;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRegisterDto {

    private String name;
    private String email;
    private String password;
    private Set<String> roles;
}
