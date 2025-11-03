package com.shreyas.ExpenseTracker.controller;

import com.shreyas.ExpenseTracker.entity.User;
import com.shreyas.ExpenseTracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class userController {
    @Autowired
   private UserService u;
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        System.out.println(user);
        return ResponseEntity.ok(u.registerUser(user));
    }
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody Map<String,String> loginData) {
        String email = loginData.get("email");
        String password = loginData.get("password");
        User user = u.loginUser(email, password);
        return ResponseEntity.ok("Login Successful for user: " + user.getName());
    }
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = u.getUserById(id);
        return ResponseEntity.ok(user);
    }
}
