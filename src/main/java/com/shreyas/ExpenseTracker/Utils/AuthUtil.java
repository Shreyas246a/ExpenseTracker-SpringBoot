package com.shreyas.ExpenseTracker.Utils;

import com.shreyas.ExpenseTracker.entity.User;
import com.shreyas.ExpenseTracker.repository.UserRepository;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthUtil {

    private final UserRepository repository;

    public AuthUtil(UserRepository repository) {
        this.repository = repository;
    }

    public User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if(email != null) {
            return repository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));
        }
        return null;
    }
}
