package com.ishimwe.digitalmarriagesystem.service.impl;

import com.ishimwe.digitalmarriagesystem.model.User;
import com.ishimwe.digitalmarriagesystem.repository.UserRepository;
import com.ishimwe.digitalmarriagesystem.service.UserService;
import com.ishimwe.digitalmarriagesystem.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, org.springframework.security.crypto.password.PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
    }

    @Override
    public User updateUser(Long id, User userDetails) {
        User user = getUserById(id);
        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setEmail(userDetails.getEmail());
        user.setNationalId(userDetails.getNationalId());
        if (userDetails.getRole() != null) user.setRole(userDetails.getRole());
        return userRepository.save(user);
    }

    @Override
    public User changeRole(Long id, String role) {
        User user = getUserById(id);
        user.setRole(role);
        return userRepository.save(user);
    }

    @Override
    public List<User> getUsersByStatus(String status) {
        if ("Approved".equalsIgnoreCase(status)) {
            return userRepository.findAll().stream().filter(User::isVerified).collect(java.util.stream.Collectors.toList());
        } else if ("Pending".equalsIgnoreCase(status)) {
            return userRepository.findAll().stream().filter(u -> !u.isVerified()).collect(java.util.stream.Collectors.toList());
        } else if ("Rejected".equalsIgnoreCase(status)) {
            return userRepository.findAll().stream().filter(u -> "Rejected".equalsIgnoreCase(u.getStatus())).collect(java.util.stream.Collectors.toList());
        }
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}