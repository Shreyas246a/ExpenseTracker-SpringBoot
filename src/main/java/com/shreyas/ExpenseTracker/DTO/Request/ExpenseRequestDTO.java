package com.shreyas.ExpenseTracker.DTO.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class ExpenseRequestDTO {
    @NotBlank(message = "Title cannot be blank")
    private String title;
    private String description;
    @NotNull(message = "Amount is required")
    @Size(min = 1, message = "Amount must be greater than zero")
    private Double amount;
    @NotNull(message = "Category is required")
    private String category;
    @NotNull(message = "Date is required")
    private LocalDate date;
}
