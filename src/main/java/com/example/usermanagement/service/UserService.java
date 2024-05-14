package com.example.usermanagement.service;

import com.example.usermanagement.model.User;
import com.example.usermanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User registerUser(User user) throws Exception {
        // Check if user already exists
        Optional<User> existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser.isPresent()) {
            throw new Exception("User already exists");
        }

        // Encode password
        String encodedPassword = Base64.getEncoder().encodeToString(user.getPassword().getBytes());
        user.setPassword(encodedPassword);

        // Save user
        return userRepository.save(user);
    }

    public User getUserInfo(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    }
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
