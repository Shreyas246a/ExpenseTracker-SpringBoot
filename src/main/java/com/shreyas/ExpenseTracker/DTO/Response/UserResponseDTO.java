package com.shreyas.ExpenseTracker.DTO.Response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserResponseDTO {
    private Long id;
    private String name;
    private String email;
    private List<ExpenseResponseDTO> expenses;
}
