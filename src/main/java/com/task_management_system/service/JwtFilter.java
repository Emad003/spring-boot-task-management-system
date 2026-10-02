package com.task_management_system.service;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtFilter extends OncePerRequestFilter {
    private JwtService jwtService;
    private CustomUserDetailsService customUserDetailsService;

    public JwtFilter(JwtService jwtService, CustomUserDetailsService customUserDetailsService) {
        this.jwtService = jwtService;

        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")){
            String rawToken=token.substring(7);
             var username=jwtService.validateTokenAndRetrieveUsername(rawToken);
             if(username!=null&& SecurityContextHolder.getContext().getAuthentication()==null){
                 UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
                 var authToken=
                         new UsernamePasswordAuthenticationToken(userDetails,
                                 null,userDetails.getAuthorities());
                 authToken.setDetails( new WebAuthenticationDetailsSource()
                         .buildDetails(request));
                 SecurityContextHolder.getContext().setAuthentication(authToken);
             }
        }
        filterChain.doFilter(request,response);



    }
}
