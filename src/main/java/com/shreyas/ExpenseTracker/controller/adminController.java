package com.shreyas.ExpenseTracker.controller;

import com.shreyas.ExpenseTracker.DTO.Response.UserResponseDTO;
import com.shreyas.ExpenseTracker.entity.User;
import com.shreyas.ExpenseTracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admins")
public class adminController {
    @Autowired
    UserService userService;
    @GetMapping("/All_users")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        UserResponseDTO user = userService.getUserById(id);
        if(user!=null){
        userService.deleteUserById(id);
        return ResponseEntity.ok("User with id " + id + " deleted successfully.");
        } else{
            return ResponseEntity.status(404).body("User with id " + id + " not found.");
        }
    }
}
