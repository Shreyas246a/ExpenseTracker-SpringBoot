package com.shreyas.ExpenseTracker.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Setter
@Getter
@Table(name = "expenses")
public class Expense {
    @Id
    private Long id;
    @Column(nullable = false)
    private String title;
    private String description;
    private Double amount;
    private String category;
    private LocalDate date;
    @ManyToOne()
    @JoinColumn(name ="user_id")
    private User user;
}
