package com.sweetshop.controller;

import com.sweetshop.dto.AuthResponse;
import com.sweetshop.dto.LoginRequest;
import com.sweetshop.dto.RegisterRequest;
import com.sweetshop.entity.User;
import com.sweetshop.security.JwtTokenProvider;
import com.sweetshop.security.UserDetailsImpl;
import com.sweetshop.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthService authService;
    private final JwtTokenProvider tokenProvider;
    
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        User user = authService.registerUser(request);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new AuthResponse(null, user.getId(), user.getUsername(), 
                        user.getEmail(), user.getRole().name())
        );
    }
    
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        Authentication authentication = authService.authenticateUser(request);
        
        String jwt = tokenProvider.generateToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        
        User user = authService.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        return ResponseEntity.ok(
                new AuthResponse(jwt, user.getId(), user.getUsername(), 
                        user.getEmail(), user.getRole().name())
        );
    }
}
