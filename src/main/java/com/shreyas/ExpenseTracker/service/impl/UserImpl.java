package com.shreyas.ExpenseTracker.service.impl;

import com.shreyas.ExpenseTracker.DTO.ExpenseMapper;
import com.shreyas.ExpenseTracker.DTO.Request.UserRequestDTO;
import com.shreyas.ExpenseTracker.DTO.Response.ExpenseResponseDTO;
import com.shreyas.ExpenseTracker.DTO.Response.UserResponseDTO;
import com.shreyas.ExpenseTracker.DTO.UserMapper;
import com.shreyas.ExpenseTracker.Exceptions.ResourceNotFoundException;
import com.shreyas.ExpenseTracker.Utils.DefaultCategories;
import com.shreyas.ExpenseTracker.Utils.JwtUtil;
import com.shreyas.ExpenseTracker.entity.Category;
import com.shreyas.ExpenseTracker.entity.PasswordResetToken;
import com.shreyas.ExpenseTracker.entity.User;
import com.shreyas.ExpenseTracker.repository.PasswordResetTokenRepo;
import com.shreyas.ExpenseTracker.repository.UserRepository;
import com.shreyas.ExpenseTracker.service.MailService;
import com.shreyas.ExpenseTracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class UserImpl implements UserService {
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MailService emailService;
    @Autowired
    private PasswordResetTokenRepo passwordResetTokenRepo;
    @Override
    public UserResponseDTO registerUser(UserRequestDTO user) {
        if(userRepository.findByEmail(user.getEmail()).isPresent()){
            throw new RuntimeException("User with email "+user.getEmail()+" already exists");
        }

        User newUser = UserMapper.userRequestDTOToUser(user);
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser = userRepository.save(newUser);

        User finalNewUser = newUser;

        DefaultCategories.CATEGORIES.forEach((cat)->{
            Category category = Category.builder().
                     name(cat)
                    .user(finalNewUser)
                    .isDefault(true)
                    .build();
        });
        return UserMapper.userResponseDTO(newUser);
    }

    @Override
    public Map<String,Object> loginUser(String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow(()->new ResourceNotFoundException("User not found"));
        if(passwordEncoder.matches(password, user.getPassword())){
            UserResponseDTO response = new UserResponseDTO();
            response.setEmail(user.getEmail());
            response.setId(user.getId());
            response.setName(user.getName());
            Map<String,Object> data = new java.util.HashMap<>(Map.of("UserData", response));
            String token = jwtUtil.generateToken(user);
            data.put("token",token);
            return data;
        } else {
            throw new RuntimeException("Invalid Password");
        }
    }


    public void forgotPassword(String email){
        User user = userRepository.findByEmail(email).orElseThrow(()->new ResourceNotFoundException("User not found"));

        String token = UUID.randomUUID().toString();
        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setToken(token);
        passwordResetToken.setExpiry(LocalDateTime.now().plusMinutes(15));
        passwordResetToken.setEmail(email);
        passwordResetTokenRepo.save(passwordResetToken);
        String resetLink = "http://localhost:3000/reset-password?token=" + token; // frontend URL

        emailService.sendEmail(email,
                "Password Reset Request",
                "Click the link to reset your password:\n\n" + resetLink +
                        "\n\nThis link expires in 15 minutes.");

        System.out.println("Password reset link sent to " + email);
    }
    public void resetPassword(String token,String password){
        PasswordResetToken p = passwordResetTokenRepo.findByToken(token).orElseThrow(()->new ResourceNotFoundException("Invalid token"));
        if(p.getExpiry().isBefore(LocalDateTime.now())){
            throw new RuntimeException("Token expired");
        } else {
            String email = p.getEmail();
            System.out.println(p);
            User user = userRepository.findByEmail(email).orElseThrow(()->new ResourceNotFoundException("User not found"));
            user.setPassword(passwordEncoder.encode(password));
            userRepository.save(user);
            passwordResetTokenRepo.delete(p);
        }
    }

























    @Override
    public List<UserResponseDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(u -> {
            UserResponseDTO response = UserMapper.userResponseDTO(u);
            List<ExpenseResponseDTO> expenseResponseDTOS =u.getExpenses().stream().map(expense -> {
                return ExpenseMapper.toExpenseResponseDTO(expense);
            }).toList();
            response.setExpenses(expenseResponseDTOS);
            return response;
        }).toList();
    }

    @Override
    public UserResponseDTO getUserById(Long id) {
        User u = userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User not found"));
        UserResponseDTO response = UserMapper.userResponseDTO(u);
        List<ExpenseResponseDTO> expenseResponseDTOS =u.getExpenses().stream().map(expense -> {
            return ExpenseMapper.toExpenseResponseDTO(expense);
        }).toList();
        response.setExpenses(expenseResponseDTOS);
        return response;
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
        return ;
    }
}
