package com.shreyas.ExpenseTracker.repository;

import com.shreyas.ExpenseTracker.entity.PasswordResetToken;
import com.shreyas.ExpenseTracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordResetTokenRepo extends JpaRepository<PasswordResetToken,Long> {
    Optional<PasswordResetToken> findByToken(String token);
    Optional<PasswordResetToken> findByEmail(String email);
}
