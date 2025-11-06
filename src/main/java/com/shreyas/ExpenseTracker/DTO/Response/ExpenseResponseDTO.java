package com.shreyas.ExpenseTracker.DTO.Response;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class ExpenseResponseDTO {
    private Long id;
    private String title;
    private String description;
    private Double amount;
    private String category;
    private LocalDate date;
    private String userName; // cleaner than embedding the whole user
}
