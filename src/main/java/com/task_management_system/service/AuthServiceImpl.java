package com.task_management_system.service;

import com.task_management_system.dto.RegistrationDto;
import com.task_management_system.entity.EmailVerificationToken;
import com.task_management_system.entity.Role;
import com.task_management_system.entity.User;
import com.task_management_system.repository.EmailVerificationTokenRepository;
import com.task_management_system.repository.UserRepository;
import org.springframework.beans.BeanUtils;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private EmailVerificationTokenRepository emailVerificationTokenRepository;
    private EmailService emailService;


    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, EmailVerificationTokenRepository emailVerificationTokenRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailVerificationTokenRepository = emailVerificationTokenRepository;
        this.emailService = emailService;
    }

    @Override
    public String register(RegistrationDto registrationDto) {
        boolean userExist = userRepository.existsByUsername(registrationDto.getUsername());
        if (userExist)
            return "User already Exist";

        User user = new User();
        BeanUtils.copyProperties(registrationDto, user);
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        user.setEmailVarified(false);
        userRepository.save(user);


        String token = UUID.randomUUID().toString();
        EmailVerificationToken verificationToken = new EmailVerificationToken();
        verificationToken.setUser(user);
        verificationToken.setToken(token);
        verificationToken.setExpiryDate(LocalDateTime.now().plusMinutes(15));
        emailVerificationTokenRepository.save(verificationToken);
        emailService.sendVerificationEmail(user.getEmail(), token);

        return "Registration successful. Please check your email to verify your account.";

    }

    public void saveUser(User user) {
        userRepository.save(user);
    }
}
