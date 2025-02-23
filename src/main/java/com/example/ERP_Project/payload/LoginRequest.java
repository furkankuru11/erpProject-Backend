package com.example.ERP_Project.payload;

import lombok.Data;

@Data
public class LoginRequest {
    private String userName;
    private String password;
} 