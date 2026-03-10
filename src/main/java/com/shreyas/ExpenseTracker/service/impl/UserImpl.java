package com.shreyas.ExpenseTracker.service.impl;

import com.shreyas.ExpenseTracker.DTO.ExpenseMapper;
import com.shreyas.ExpenseTracker.DTO.Request.UserRequestDTO;
import com.shreyas.ExpenseTracker.DTO.Response.ExpenseResponseDTO;
import com.shreyas.ExpenseTracker.DTO.Response.UserResponseDTO;
import com.shreyas.ExpenseTracker.DTO.UserMapper;
import com.shreyas.ExpenseTracker.Exceptions.ResourceNotFoundException;
import com.shreyas.ExpenseTracker.Exceptions.TokenException;
import com.shreyas.ExpenseTracker.Utils.JwtUtil;
import com.shreyas.ExpenseTracker.entity.PasswordResetToken;
import com.shreyas.ExpenseTracker.entity.User;
import com.shreyas.ExpenseTracker.repository.CategoryRepository;
import com.shreyas.ExpenseTracker.repository.PasswordResetTokenRepo;
import com.shreyas.ExpenseTracker.repository.UserRepository;
import com.shreyas.ExpenseTracker.service.MailService;
import com.shreyas.ExpenseTracker.service.UserService;
import com.sun.jdi.request.DuplicateRequestException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class UserImpl implements UserService {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService emailService;
    private final PasswordResetTokenRepo passwordResetTokenRepo;

    public UserImpl(JwtUtil jwtUtil, UserRepository userRepository, PasswordEncoder passwordEncoder, MailService emailService, CategoryRepository categoryRepository, PasswordResetTokenRepo passwordResetTokenRepo) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.passwordResetTokenRepo = passwordResetTokenRepo;
    }
    @Override
    public UserResponseDTO registerUser(UserRequestDTO user) {
        if(userRepository.findByEmail(user.getEmail()).isPresent()){
            throw new RuntimeException("User with email "+user.getEmail()+" already exists");
        }

        User newUser = UserMapper.userRequestDTOToUser(user);
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser = userRepository.save(newUser);

        User finalNewUser = newUser;
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
            throw new ResourceNotFoundException("Invalid Password");
        }
    }

    @Override
    public void forgotPassword(String email){
        System.out.println(email);
        User user = userRepository.findByEmail(email).orElseThrow(()->new ResourceNotFoundException("User with this email not found"));
        PasswordResetToken p = passwordResetTokenRepo.findByEmail(email).orElse(null);
        if(p!=null){
            if(p.getExpiry().isBefore(LocalDateTime.now())){
                passwordResetTokenRepo.delete(p);
            } else {
                throw new TokenException("A reset link has already been sent to this email. Please check your inbox.");
            }
        }
        String token = UUID.randomUUID().toString();
        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setToken(token);
        passwordResetToken.setExpiry(LocalDateTime.now().plusMinutes(15));
        passwordResetToken.setEmail(email);
        passwordResetTokenRepo.save(passwordResetToken);
        String resetLink = "http://localhost:3000/reset-password?token=" + token;
        emailService.sendEmail(email,
                "Password Reset Request",
                "Click the link to reset your password:\n\n" + resetLink +
                        "\n\nThis link expires in 15 minutes.");

        System.out.println("Password reset link sent to " + email);
    }
    @Override
    public void resetPassword(String token,String password){
        PasswordResetToken p = passwordResetTokenRepo.findByToken(token).orElseThrow(()->new ResourceNotFoundException("Invalid token"));
        if(p.getExpiry().isBefore(LocalDateTime.now())){
            passwordResetTokenRepo.delete(p);
            throw new TokenException("Token expired");
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
            List<ExpenseResponseDTO> expenseResponseDTOS =u.getExpenses().stream().map(ExpenseMapper::toExpenseResponseDTO).toList();
            response.setExpenses(expenseResponseDTOS);
            return response;
        }).toList();
    }

    @Override
    public UserResponseDTO getUserById(Long id) {
        User u = userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User not found"));
        UserResponseDTO response = UserMapper.userResponseDTO(u);
        List<ExpenseResponseDTO> expenseResponseDTOS =u.getExpenses().stream().map(ExpenseMapper::toExpenseResponseDTO).toList();
        response.setExpenses(expenseResponseDTOS);
        return response;
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
        return ;
    }
}
