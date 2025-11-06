package com.shreyas.ExpenseTracker.DTO;

import com.shreyas.ExpenseTracker.DTO.Request.UserRequestDTO;
import com.shreyas.ExpenseTracker.DTO.Response.UserResponseDTO;
import com.shreyas.ExpenseTracker.entity.User;

public class UserMapper {
    public static UserResponseDTO userResponseDTO(User user){
        UserResponseDTO response = new UserResponseDTO();
        response.setEmail(user.getEmail());
        response.setId(user.getId());
        response.setName(user.getName());
        return response;
    }

    public static User userRequestDTOToUser(UserRequestDTO user){
        User newUser = new User();
        newUser.setName(user.getName());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());
        return newUser;
    }
}
