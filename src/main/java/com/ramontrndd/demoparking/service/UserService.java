package com.ramontrndd.demoparking.service;

import com.ramontrndd.demoparking.entity.User;
import com.ramontrndd.demoparking.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;


    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found."));
    }

    @Transactional
    public User editPassword(Long id, String currentPassword, String newPassword, String confirmPassword) {
        if(!newPassword.equals(confirmPassword)) {
            throw new RuntimeException("Passwords do not match.");
        }
        User user = findById(id);
        if(!user.getPassword().equals(currentPassword)) {
            throw new RuntimeException("Current password is incorrect.");
        }
        user.setPassword(newPassword);
        return user;
    }

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

}
