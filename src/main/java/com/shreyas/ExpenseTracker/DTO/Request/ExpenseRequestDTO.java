package com.shreyas.ExpenseTracker.DTO.Request;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class ExpenseRequestDTO {
    private String title;
    private String description;
    private Double amount;
    private String category;
    private LocalDate date;
}
