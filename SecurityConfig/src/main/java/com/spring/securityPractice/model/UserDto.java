package com.spring.securityPractice.model;

import lombok.*;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private long id;
    private String name;
    private String userId;
    private String email;
    private String password;
    private String role;


    public void setAccessToken(String s) {
    }
}