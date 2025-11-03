package com.shreyas.ExpenseTracker.service.impl;

import com.shreyas.ExpenseTracker.entity.User;
import com.shreyas.ExpenseTracker.repository.UserRepository;
import com.shreyas.ExpenseTracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class UserImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public User registerUser(User user) {
        return userRepository.save(user);
    }
    @Override
    public User loginUser(String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow(()->new RuntimeException("User not found"));
        if(user.getPassword().equals(password)){
            return user;
        } else {
            throw new RuntimeException("Invalid Password");
        }
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(()->new RuntimeException("User not found"));
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
        return ;
    }
}
