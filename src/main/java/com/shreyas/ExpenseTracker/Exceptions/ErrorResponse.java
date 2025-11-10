package com.shreyas.ExpenseTracker.Exceptions;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
public class ErrorResponse {
    private String message;
    private int statusCode;
    private LocalDateTime timestamp;
    public ErrorResponse(){

    }
    public ErrorResponse(String message, int statusCode, LocalDateTime timestamp) {
        this.message = message;
        this.statusCode = statusCode;
        this.timestamp = timestamp;
    }

}
