package com.shreyas.ExpenseTracker.controller;

import com.shreyas.ExpenseTracker.DTO.Request.UserRequestDTO;
import com.shreyas.ExpenseTracker.DTO.Response.UserResponseDTO;
import com.shreyas.ExpenseTracker.service.UserService;
import jakarta.validation.Valid;
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
    public ResponseEntity<UserResponseDTO> registerUser(@Valid  @RequestBody UserRequestDTO user) {
        System.out.println(user);
        return ResponseEntity.ok(u.registerUser(user));
    }
    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> loginUser(@Valid @RequestBody Map<String,String> loginData) {
        String email = loginData.get("email");
        String password = loginData.get("password");
        UserResponseDTO user = u.loginUser(email, password);
        return ResponseEntity.ok(user);
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        UserResponseDTO user = u.getUserById(id);
        return ResponseEntity.ok(user);
    }
}
