package com.shreyas.ExpenseTracker.controller;

import com.shreyas.ExpenseTracker.DTO.Request.UserRequestDTO;
import com.shreyas.ExpenseTracker.DTO.Response.UserResponseDTO;
import com.shreyas.ExpenseTracker.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserService u;
    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerUser(@Valid @RequestBody UserRequestDTO user) {
        System.out.println(user);
        return ResponseEntity.ok(u.registerUser(user));
    }
    @PostMapping("/login")
    public ResponseEntity<Map<String,Object>> loginUser(@Valid @RequestBody Map<String,String> loginData) {
        String email = loginData.get("email");
        String password = loginData.get("password");
        Map<String ,Object>map = u.loginUser(email, password);
        return ResponseEntity.ok(map);
    }
}
