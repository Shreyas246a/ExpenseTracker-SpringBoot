package com.shreyas.ExpenseTracker.service;

import com.shreyas.ExpenseTracker.DTO.Request.UserRequestDTO;
import com.shreyas.ExpenseTracker.DTO.Response.UserResponseDTO;
import com.shreyas.ExpenseTracker.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    public UserResponseDTO registerUser(UserRequestDTO user);
    public Map<String,Object> loginUser(String email, String password);
    public List<UserResponseDTO> getAllUsers();
    public UserResponseDTO getUserById(Long id);
    public void resetPassword(String token,String password);
    public void forgotPassword(String email);

    public void deleteUserById(Long id);
}
