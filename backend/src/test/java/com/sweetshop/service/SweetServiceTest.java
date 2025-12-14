package com.sweetshop.service;

import com.sweetshop.dto.SweetRequest;
import com.sweetshop.entity.Sweet;
import com.sweetshop.exception.InsufficientStockException;
import com.sweetshop.exception.ResourceNotFoundException;
import com.sweetshop.repository.SweetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SweetServiceTest {
    
    @Mock
    private SweetRepository sweetRepository;
    
    @InjectMocks
    private SweetService sweetService;
    
    private Sweet sweet;
    private SweetRequest sweetRequest;
    
    @BeforeEach
    void setUp() {
        sweet = new Sweet();
        sweet.setId(1L);
        sweet.setName("Chocolate Bar");
        sweet.setCategory("Chocolate");
        sweet.setPrice(new BigDecimal("2.50"));
        sweet.setQuantity(100);
        sweet.setDescription("Delicious chocolate bar");
        
        sweetRequest = new SweetRequest(
                "Chocolate Bar",
                "Chocolate",
                new BigDecimal("2.50"),
                100,
                "Delicious chocolate bar"
        );
    }
    
    @Test
    @DisplayName("Should create a new sweet")
    void testCreateSweet_Success() {
        // Arrange
        when(sweetRepository.save(any(Sweet.class))).thenReturn(sweet);
        
        // Act
        Sweet result = sweetService.createSweet(sweetRequest);
        
        // Assert
        assertNotNull(result);
        assertEquals("Chocolate Bar", result.getName());
        assertEquals("Chocolate", result.getCategory());
        assertEquals(new BigDecimal("2.50"), result.getPrice());
        assertEquals(100, result.getQuantity());
        verify(sweetRepository, times(1)).save(any(Sweet.class));
    }
    
    @Test
    @DisplayName("Should get all sweets")
    void testGetAllSweets_Success() {
        // Arrange
        List<Sweet> sweets = Arrays.asList(sweet);
        when(sweetRepository.findAll()).thenReturn(sweets);
        
        // Act
        List<Sweet> result = sweetService.getAllSweets();
        
        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Chocolate Bar", result.get(0).getName());
        verify(sweetRepository, times(1)).findAll();
    }
    
    @Test
    @DisplayName("Should get sweet by id")
    void testGetSweetById_Success() {
        // Arrange
        when(sweetRepository.findById(1L)).thenReturn(Optional.of(sweet));
        
        // Act
        Sweet result = sweetService.getSweetById(1L);
        
        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Chocolate Bar", result.getName());
        verify(sweetRepository, times(1)).findById(1L);
    }
    
    @Test
    @DisplayName("Should throw exception when sweet not found")
    void testGetSweetById_NotFound() {
        // Arrange
        when(sweetRepository.findById(anyLong())).thenReturn(Optional.empty());
        
        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            sweetService.getSweetById(1L);
        });
    }
    
    @Test
    @DisplayName("Should update sweet")
    void testUpdateSweet_Success() {
        // Arrange
        when(sweetRepository.findById(1L)).thenReturn(Optional.of(sweet));
        when(sweetRepository.save(any(Sweet.class))).thenReturn(sweet);
        
        SweetRequest updateRequest = new SweetRequest(
                "Updated Chocolate",
                "Chocolate",
                new BigDecimal("3.00"),
                150,
                "Updated description"
        );
        
        // Act
        Sweet result = sweetService.updateSweet(1L, updateRequest);
        
        // Assert
        assertNotNull(result);
        verify(sweetRepository, times(1)).findById(1L);
        verify(sweetRepository, times(1)).save(any(Sweet.class));
    }
    
    @Test
    @DisplayName("Should delete sweet")
    void testDeleteSweet_Success() {
        // Arrange
        when(sweetRepository.findById(1L)).thenReturn(Optional.of(sweet));
        doNothing().when(sweetRepository).delete(any(Sweet.class));
        
        // Act
        sweetService.deleteSweet(1L);
        
        // Assert
        verify(sweetRepository, times(1)).findById(1L);
        verify(sweetRepository, times(1)).delete(sweet);
    }
    
    @Test
    @DisplayName("Should purchase sweet successfully")
    void testPurchaseSweet_Success() {
        // Arrange
        when(sweetRepository.findById(1L)).thenReturn(Optional.of(sweet));
        when(sweetRepository.save(any(Sweet.class))).thenReturn(sweet);
        
        // Act
        Sweet result = sweetService.purchaseSweet(1L, 10);
        
        // Assert
        assertNotNull(result);
        verify(sweetRepository, times(1)).findById(1L);
        verify(sweetRepository, times(1)).save(any(Sweet.class));
    }
    
    @Test
    @DisplayName("Should throw exception when insufficient stock")
    void testPurchaseSweet_InsufficientStock() {
        // Arrange
        when(sweetRepository.findById(1L)).thenReturn(Optional.of(sweet));
        
        // Act & Assert
        assertThrows(InsufficientStockException.class, () -> {
            sweetService.purchaseSweet(1L, 150);
        });
        verify(sweetRepository, never()).save(any(Sweet.class));
    }
    
    @Test
    @DisplayName("Should restock sweet successfully")
    void testRestockSweet_Success() {
        // Arrange
        when(sweetRepository.findById(1L)).thenReturn(Optional.of(sweet));
        when(sweetRepository.save(any(Sweet.class))).thenReturn(sweet);
        
        // Act
        Sweet result = sweetService.restockSweet(1L, 50);
        
        // Assert
        assertNotNull(result);
        verify(sweetRepository, times(1)).findById(1L);
        verify(sweetRepository, times(1)).save(any(Sweet.class));
    }
    
    @Test
    @DisplayName("Should search sweets by criteria")
    void testSearchSweets_Success() {
        // Arrange
        List<Sweet> sweets = Arrays.asList(sweet);
        when(sweetRepository.searchSweets(anyString(), anyString(), any(), any())).thenReturn(sweets);
        
        // Act
        List<Sweet> result = sweetService.searchSweets("Chocolate", "Chocolate", 
                new BigDecimal("1.00"), new BigDecimal("5.00"));
        
        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(sweetRepository, times(1)).searchSweets(anyString(), anyString(), any(), any());
    }
}
