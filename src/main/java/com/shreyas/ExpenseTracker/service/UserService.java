package com.shreyas.ExpenseTracker.service;

import com.shreyas.ExpenseTracker.entity.User;
import org.springframework.stereotype.Service;
import java.util.List;

public interface UserService {
    public User registerUser(User user);
    public User loginUser(String email, String password);
    public List<User> getAllUsers();
    public User getUserById(Long id);

    public void deleteUserById(Long id);
}
