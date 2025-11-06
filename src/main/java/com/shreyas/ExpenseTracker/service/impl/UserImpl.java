package com.shreyas.ExpenseTracker.service.impl;

import com.shreyas.ExpenseTracker.DTO.ExpenseMapper;
import com.shreyas.ExpenseTracker.DTO.Request.UserRequestDTO;
import com.shreyas.ExpenseTracker.DTO.Response.ExpenseResponseDTO;
import com.shreyas.ExpenseTracker.DTO.Response.UserResponseDTO;
import com.shreyas.ExpenseTracker.DTO.UserMapper;
import com.shreyas.ExpenseTracker.entity.User;
import com.shreyas.ExpenseTracker.repository.UserRepository;
import com.shreyas.ExpenseTracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserResponseDTO registerUser(UserRequestDTO user) {
        User newUser = userRepository.save(UserMapper.userRequestDTOToUser(user));
        return UserMapper.userResponseDTO(newUser);
    }

    @Override
    public UserResponseDTO loginUser(String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow(()->new RuntimeException("User not found"));
        if(user.getPassword().equals(password)){
            UserResponseDTO response = new UserResponseDTO();
            response.setEmail(user.getEmail());
            response.setId(user.getId());
            response.setName(user.getName());
            return response;
        } else {
            throw new RuntimeException("Invalid Password");
        }
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(u -> {
            UserResponseDTO response = UserMapper.userResponseDTO(u);
            List<ExpenseResponseDTO> expenseResponseDTOS =u.getExpenses().stream().map(expense -> {
                return ExpenseMapper.toExpenseResponseDTO(expense);
            }).toList();
            response.setExpenses(expenseResponseDTOS);
            return response;
        }).toList();
    }

    @Override
    public UserResponseDTO getUserById(Long id) {
        User u = userRepository.findById(id).orElseThrow(()->new RuntimeException("User not found"));
        UserResponseDTO response = UserMapper.userResponseDTO(u);
        List<ExpenseResponseDTO> expenseResponseDTOS =u.getExpenses().stream().map(expense -> {
            return ExpenseMapper.toExpenseResponseDTO(expense);
        }).toList();
        response.setExpenses(expenseResponseDTOS);
        return response;
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
        return ;
    }
}
