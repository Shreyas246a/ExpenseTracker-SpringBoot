package com.shreyas.ExpenseTracker.DTO.Request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDTO {
    private String name;
    private String email;
    private  String password;
}
