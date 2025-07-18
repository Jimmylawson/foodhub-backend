package com.food_delivery.zomato_backend.controller;


import com.food_delivery.zomato_backend.dtos.DeliveryDtos.DeliveryRequestDto;
import com.food_delivery.zomato_backend.dtos.DeliveryDtos.DeliveryResponseDto;
import com.food_delivery.zomato_backend.service.DeliveryService.DeliveryServiceInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/deliveries")
@RequiredArgsConstructor
@Tag(name = "Delivery",description = "Delivery Management APIs")
public class DeliveryController {
    private final DeliveryServiceInterface deliveryServiceInterface;

    /// Create endpoints
    @Operation(summary = "Create a new delivery")
    @ApiResponse(responseCode = "201", description = "Delivery created successfully")
    @PostMapping
    public ResponseEntity<DeliveryResponseDto> createDelivery(@Valid @RequestBody DeliveryRequestDto deliveryRequestDto) {
        return ResponseEntity.status(201).body(deliveryServiceInterface.saveDelivery(deliveryRequestDto));
    }

    /// Read endpoints
    @Operation(summary = "Get a delivery by id")
    @ApiResponse(responseCode = "200", description = "Delivery found successfully")
    @GetMapping("/{id}")
    public ResponseEntity<DeliveryResponseDto> getDelivery(@PathVariable Long id) {
        return ResponseEntity.ok(deliveryServiceInterface.getDelivery(id));
    }

    @Operation(summary = "Get all deliveries")
    @ApiResponse(responseCode = "200", description = "Deliveries found successfully")
    @GetMapping
    public ResponseEntity<Page<DeliveryResponseDto>> getAllDeliveries(@PageableDefault(size = 10, sort = "createdAt") Pageable pageable) {
        return ResponseEntity.ok(deliveryServiceInterface.getAllDeliveries(pageable));
    }

    ///Update endpoints
    @Operation(summary = "Update a delivery by id")
    @ApiResponse(responseCode = "200", description = "Delivery found successfully")
    @PreAuthorize("hasAnyAuthority('DELIVERY','ADMIN')")
    @PutMapping("/{deliveryId}")
    public ResponseEntity<DeliveryResponseDto> updateDelivery(@PathVariable Long deliveryId, @Valid @RequestBody DeliveryRequestDto deliveryRequestDto) {
        return ResponseEntity.ok(deliveryServiceInterface.updateDelivery(deliveryId, deliveryRequestDto));
    }

    /// Partial Update
    @Operation(summary = "Update a delivery by id")
    @ApiResponse(responseCode = "200", description = "Delivery found successfully")
    @PatchMapping("/{deliveryId}")
    public ResponseEntity<DeliveryResponseDto> partialUpdate(@PathVariable Long deliveryId, @Valid @RequestBody DeliveryRequestDto deliveryRequestDto) {
        return ResponseEntity.ok(deliveryServiceInterface.updateDelivery(deliveryId, deliveryRequestDto));
    }

    ///delete endpoints
    @Operation(summary = "Delete a delivery by id")
    @ApiResponse(responseCode = "204", description = "Delivery deleted successfully")
    @DeleteMapping("/{deliveryId}")
    public ResponseEntity<Void> deleteDelivery(@PathVariable Long deliveryId) {
        deliveryServiceInterface.deleteDelivery(deliveryId);
        return ResponseEntity.noContent().build();
    }
}
