package com.sweetshop.service;

import com.sweetshop.dto.SweetRequest;
import com.sweetshop.entity.Sweet;
import com.sweetshop.exception.InsufficientStockException;
import com.sweetshop.exception.ResourceNotFoundException;
import com.sweetshop.repository.SweetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SweetService {
    
    private final SweetRepository sweetRepository;
    
    @Transactional
    public Sweet createSweet(SweetRequest request) {
        Sweet sweet = new Sweet();
        sweet.setName(request.getName());
        sweet.setCategory(request.getCategory());
        sweet.setPrice(request.getPrice());
        sweet.setQuantity(request.getQuantity());
        sweet.setDescription(request.getDescription());
        
        return sweetRepository.save(sweet);
    }
    
    public List<Sweet> getAllSweets() {
        return sweetRepository.findAll();
    }
    
    public Sweet getSweetById(Long id) {
        return sweetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sweet not found with id: " + id));
    }
    
    @Transactional
    public Sweet updateSweet(Long id, SweetRequest request) {
        Sweet sweet = getSweetById(id);
        
        sweet.setName(request.getName());
        sweet.setCategory(request.getCategory());
        sweet.setPrice(request.getPrice());
        sweet.setQuantity(request.getQuantity());
        sweet.setDescription(request.getDescription());
        
        return sweetRepository.save(sweet);
    }
    
    @Transactional
    public void deleteSweet(Long id) {
        Sweet sweet = getSweetById(id);
        sweetRepository.delete(sweet);
    }
    
    @Transactional
    public Sweet purchaseSweet(Long id, Integer quantity) {
        Sweet sweet = getSweetById(id);
        
        if (sweet.getQuantity() < quantity) {
            throw new InsufficientStockException(
                    "Insufficient stock. Available: " + sweet.getQuantity() + ", Requested: " + quantity);
        }
        
        sweet.setQuantity(sweet.getQuantity() - quantity);
        return sweetRepository.save(sweet);
    }
    
    @Transactional
    public Sweet restockSweet(Long id, Integer quantity) {
        Sweet sweet = getSweetById(id);
        sweet.setQuantity(sweet.getQuantity() + quantity);
        return sweetRepository.save(sweet);
    }
    
    public List<Sweet> searchSweets(String name, String category, BigDecimal minPrice, BigDecimal maxPrice) {
        return sweetRepository.searchSweets(name, category, minPrice, maxPrice);
    }
    
    public List<Sweet> getSweetsByCategory(String category) {
        return sweetRepository.findByCategory(category);
    }
    
    public List<Sweet> searchSweetsByName(String name) {
        return sweetRepository.findByNameContainingIgnoreCase(name);
    }
}
