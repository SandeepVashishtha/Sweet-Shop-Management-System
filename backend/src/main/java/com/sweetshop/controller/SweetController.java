package com.sweetshop.controller;

import com.sweetshop.dto.PurchaseRequest;
import com.sweetshop.dto.RestockRequest;
import com.sweetshop.dto.SweetRequest;
import com.sweetshop.dto.SweetResponse;
import com.sweetshop.entity.Sweet;
import com.sweetshop.service.SweetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/sweets")
@RequiredArgsConstructor
public class SweetController {
    
    private final SweetService sweetService;
    
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SweetResponse> createSweet(@Valid @RequestBody SweetRequest request) {
        Sweet sweet = sweetService.createSweet(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(sweet));
    }
    
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<SweetResponse>> getAllSweets() {
        List<Sweet> sweets = sweetService.getAllSweets();
        List<SweetResponse> responses = sweets.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SweetResponse> getSweetById(@PathVariable Long id) {
        Sweet sweet = sweetService.getSweetById(id);
        return ResponseEntity.ok(toResponse(sweet));
    }
    
    @GetMapping("/search")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<SweetResponse>> searchSweets(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice) {
        
        List<Sweet> sweets = sweetService.searchSweets(name, category, minPrice, maxPrice);
        List<SweetResponse> responses = sweets.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SweetResponse> updateSweet(
            @PathVariable Long id,
            @Valid @RequestBody SweetRequest request) {
        Sweet sweet = sweetService.updateSweet(id, request);
        return ResponseEntity.ok(toResponse(sweet));
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteSweet(@PathVariable Long id) {
        sweetService.deleteSweet(id);
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping("/{id}/purchase")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SweetResponse> purchaseSweet(
            @PathVariable Long id,
            @Valid @RequestBody PurchaseRequest request) {
        Sweet sweet = sweetService.purchaseSweet(id, request.getQuantity());
        return ResponseEntity.ok(toResponse(sweet));
    }
    
    @PostMapping("/{id}/restock")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SweetResponse> restockSweet(
            @PathVariable Long id,
            @Valid @RequestBody RestockRequest request) {
        Sweet sweet = sweetService.restockSweet(id, request.getQuantity());
        return ResponseEntity.ok(toResponse(sweet));
    }
    
    private SweetResponse toResponse(Sweet sweet) {
        return new SweetResponse(
                sweet.getId(),
                sweet.getName(),
                sweet.getCategory(),
                sweet.getPrice(),
                sweet.getQuantity(),
                sweet.getDescription(),
                sweet.getCreatedAt(),
                sweet.getUpdatedAt()
        );
    }
}
