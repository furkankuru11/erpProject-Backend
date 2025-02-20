package com.example.ERP_Project.controller;

import com.example.ERP_Project.entities.User;
import com.example.ERP_Project.entities.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3001") // React uygulamanızın çalıştığı port
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/save")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            // Gelen JSON'ı yazdır
            ObjectMapper mapper = new ObjectMapper();
            System.out.println("Gelen JSON: " + mapper.writeValueAsString(user));
            
            if (user.getUserName() == null || user.getPassword() == null) {
                return ResponseEntity.badRequest().body("Kullanıcı adı ve şifre gereklidir");
            }
            
            User savedUser = userService.saveUser(user);
            return ResponseEntity.ok(savedUser);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Hata: " + e.getMessage());
        }
    }
} 