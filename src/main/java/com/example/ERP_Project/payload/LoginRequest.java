package com.example.ERP_Project.payload;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
} 