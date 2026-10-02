package com.task_management_system.config;

import com.task_management_system.service.CustomUserDetailsService;

import com.task_management_system.service.JwtFilter;
import com.task_management_system.service.JwtService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class Config {
    private CustomUserDetailsService customUserDetailsService;
    private JwtService jwtService;

    public Config(CustomUserDetailsService customUserDetailsService, JwtService jwtService) {
        this.customUserDetailsService = customUserDetailsService;
        this.jwtService = jwtService;
    }
    JwtFilter jwtFilter(){
        return new JwtFilter(jwtService,customUserDetailsService);
    }



    @Bean
    SecurityFilterChain securityFilterChain (HttpSecurity http){
         http.csrf(csrf->csrf.disable()).
                 authorizeHttpRequests(
                 req->req.requestMatchers("/auth/**").permitAll()
                         .requestMatchers("/admin/**").hasRole("ADMIN")
                         .requestMatchers("/tasks/**").hasAnyRole("ADMIN","USER")
                         .anyRequest().authenticated()
         ).addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
         return http.build();

    }


    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config)
    {
      return   config.getAuthenticationManager();
    }
}
