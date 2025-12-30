package com.shreyas.ExpenseTracker.Utils;

import jakarta.annotation.PostConstruct;

import java.util.List;

public class DefaultCategories {
    public static List<String> CATEGORIES = List.of(
            "Food",
            "Travel",
            "Shopping",
            "Groceries",
            "Bills",
            "Entertainment",
            "Health",
            "Other"
    );
}
