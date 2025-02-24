package com.example.ERP_Project.controller;

import com.example.ERP_Project.entities.User;
import com.example.ERP_Project.entities.UserService;
import com.example.ERP_Project.payload.LoginRequest;
import com.example.ERP_Project.payload.MessageResponse;
import com.example.ERP_Project.payload.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            // Kullanıcıyı veritabanında ara
            Optional<User> userOptional = userService.findByEmail(loginRequest.getEmail());
            
            if (!userOptional.isPresent()) {
                return ResponseEntity
                    .status(404)
                    .body(new MessageResponse("Kullanıcı bulunamadı!"));
            }
            
            User user = userOptional.get();
            
            // Şifre kontrolü
            if (!userService.validatePassword(loginRequest.getPassword(), user.getPassword())) {
                return ResponseEntity
                    .status(401)
                    .body(new MessageResponse("Şifre hatalı!"));
            }
            
            // Şimdilik basit bir token oluştur
            String token = "dummy_token_" + user.getEmail();
            
            return ResponseEntity.ok(new LoginResponse(
                true,
                "Giriş başarılı",
                token,
                user.getName(),
                user.getSurname(),
                user.getEmail()
            ));
            
        } catch (Exception e) {
            return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Giriş işlemi başarısız: " + e.getMessage()));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
                return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Kullanıcı adı boş olamaz"));
            }

            User savedUser = userService.saveUser(user);
            return ResponseEntity.ok(savedUser);
        } catch (Exception e) {
            return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Kayıt başarısız: " + e.getMessage()));
        }
    }
} 