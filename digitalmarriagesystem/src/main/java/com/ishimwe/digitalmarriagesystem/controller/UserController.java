package com.ishimwe.digitalmarriagesystem.controller;

import com.ishimwe.digitalmarriagesystem.model.User;
import com.ishimwe.digitalmarriagesystem.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    public User createUser(@RequestBody User user){
        return userService.saveUser(user);
    }

    @GetMapping
    public List<User> getUsers(@RequestParam(required = false) String status){
        if (status != null && !status.isEmpty()) {
            return userService.getUsersByStatus(status);
        }
        return userService.getAllUsers();
    }

    @GetMapping("/search")
    public User searchUserByEmail(@RequestParam String email) {
        return userService.getUserByEmail(email);
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id){
        return userService.getUserById(id);
    }

    @PutMapping("/{id}/role")
    public User updateRole(@PathVariable Long id, @RequestBody java.util.Map<String, String> request) {
        return userService.changeRole(id, request.get("role").toUpperCase());
    }

    @PutMapping("/{id}/profile")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
    }
}