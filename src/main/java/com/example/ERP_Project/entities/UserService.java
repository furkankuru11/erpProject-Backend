package com.example.ERP_Project.entities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user) {
        // Kullanıcı adı kontrolü
        if (userRepository.existsByUserName(user.getUserName())) {
            throw new RuntimeException("Kullanıcı adı zaten kullanımda!");
        }
        
        try {
            // Şifreyi hashle
            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
            return userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("Kullanıcı kaydedilirken bir hata oluştu: " + e.getMessage());
        }
    }
}
