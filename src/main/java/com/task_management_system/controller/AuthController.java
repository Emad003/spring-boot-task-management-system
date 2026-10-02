package com.task_management_system.controller;

import com.task_management_system.dto.RegistrationDto;
import com.task_management_system.dto.loginDto;
import com.task_management_system.entity.EmailVerificationToken;
import com.task_management_system.entity.User;
import com.task_management_system.repository.EmailVerificationTokenRepository;
import com.task_management_system.response.LoginResponse;
import com.task_management_system.service.AuthServiceImpl;
import com.task_management_system.service.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private AuthServiceImpl authService;
    private AuthenticationManager authenticationManager;
    private JwtService jwtService;
    private EmailVerificationTokenRepository emailVerificationTokenRepository;

    public AuthController(AuthServiceImpl authService, AuthenticationManager authenticationManager, JwtService jwtService, EmailVerificationTokenRepository emailVerificationTokenRepository) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.emailVerificationTokenRepository = emailVerificationTokenRepository;
    }

    @PostMapping("/signup")
    ResponseEntity<String> signUp(@RequestBody RegistrationDto registrationDto) {
        String Response = authService.register(registrationDto);
        return ResponseEntity.ok(Response);
    }

    @GetMapping("/verify")
    public ResponseEntity<String> verifyEmail(@RequestParam String token){
        Optional<EmailVerificationToken> optionalToken=  emailVerificationTokenRepository.findByToken(token);
        if(optionalToken.isEmpty())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid verification Token");

        EmailVerificationToken verificationToken = emailVerificationTokenRepository.findByToken(token).get();

        if(verificationToken.getExpiryDate().isBefore(LocalDateTime.now())){
            emailVerificationTokenRepository.delete(verificationToken);
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Verification token has expired");
        }
         User user=verificationToken.getUser();
        user.setEmailVarified(true);
        authService.saveUser(user);
        emailVerificationTokenRepository.delete(verificationToken);
        return ResponseEntity.ok("Usere verified successfully");



    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponse<String>> login(@RequestBody loginDto loginDto) {
        LoginResponse<String> loginResponse = new LoginResponse<>();
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
        try {
            Authentication authenticate = authenticationManager.authenticate(token);
            String role = authenticate.getAuthorities()
                    .iterator()
                    .next()
                    .getAuthority();
            loginResponse.setMessage("Authentication Success");
            loginResponse.setStatusCode(200);
            loginResponse.setData(jwtService.generateToken(loginDto.getUsername(), role));
            return new ResponseEntity<>(loginResponse,HttpStatus.valueOf(loginResponse.getStatusCode()));

        } catch (BadCredentialsException e) {
            loginResponse.setMessage("Authentication Failed");
            loginResponse.setStatusCode(401);
            loginResponse.setData(null);
            return new ResponseEntity<>(loginResponse,HttpStatus.valueOf(loginResponse.getStatusCode()));


        }

    }


}
