package com.sweetshop.service;

import com.sweetshop.dto.LoginRequest;
import com.sweetshop.dto.RegisterRequest;
import com.sweetshop.entity.User;
import com.sweetshop.exception.UserAlreadyExistsException;
import com.sweetshop.repository.UserRepository;
import com.sweetshop.security.UserDetailsImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
    
    @Mock
    private UserRepository userRepository;
    
    @Mock
    private PasswordEncoder passwordEncoder;
    
    @Mock
    private AuthenticationManager authenticationManager;
    
    @InjectMocks
    private AuthService authService;
    
    private RegisterRequest registerRequest;
    private LoginRequest loginRequest;
    private User user;
    
    @BeforeEach
    void setUp() {
        registerRequest = new RegisterRequest("testuser", "test@example.com", "password123");
        loginRequest = new LoginRequest("testuser", "password123");
        
        user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setPassword("encodedPassword");
        user.setRole(User.Role.USER);
    }
    
    @Test
    @DisplayName("Should successfully register a new user")
    void testRegisterUser_Success() {
        // Arrange
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);
        
        // Act
        User result = authService.registerUser(registerRequest);
        
        // Assert
        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
        assertEquals("test@example.com", result.getEmail());
        verify(userRepository, times(1)).save(any(User.class));
    }
    
    @Test
    @DisplayName("Should throw exception when username already exists")
    void testRegisterUser_UsernameExists() {
        // Arrange
        when(userRepository.existsByUsername("testuser")).thenReturn(true);
        
        // Act & Assert
        assertThrows(UserAlreadyExistsException.class, () -> {
            authService.registerUser(registerRequest);
        });
        verify(userRepository, never()).save(any(User.class));
    }
    
    @Test
    @DisplayName("Should throw exception when email already exists")
    void testRegisterUser_EmailExists() {
        // Arrange
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.existsByEmail("test@example.com")).thenReturn(true);
        
        // Act & Assert
        assertThrows(UserAlreadyExistsException.class, () -> {
            authService.registerUser(registerRequest);
        });
        verify(userRepository, never()).save(any(User.class));
    }
    
    @Test
    @DisplayName("Should successfully authenticate user with valid credentials")
    void testAuthenticateUser_Success() {
        // Arrange
        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        
        // Act
        Authentication result = authService.authenticateUser(loginRequest);
        
        // Assert
        assertNotNull(result);
        verify(authenticationManager, times(1))
                .authenticate(any(UsernamePasswordAuthenticationToken.class));
    }
    
    @Test
    @DisplayName("Should throw exception with invalid credentials")
    void testAuthenticateUser_InvalidCredentials() {
        // Arrange
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Invalid credentials"));
        
        // Act & Assert
        assertThrows(BadCredentialsException.class, () -> {
            authService.authenticateUser(loginRequest);
        });
    }
    
    @Test
    @DisplayName("Should find user by username")
    void testFindByUsername_Success() {
        // Arrange
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        
        // Act
        Optional<User> result = authService.findByUsername("testuser");
        
        // Assert
        assertTrue(result.isPresent());
        assertEquals("testuser", result.get().getUsername());
    }
}
