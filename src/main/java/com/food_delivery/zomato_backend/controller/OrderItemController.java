package com.food_delivery.zomato_backend.controller;

import com.food_delivery.zomato_backend.dtos.OrderItemDtos.OrderItemRequestDto;
import com.food_delivery.zomato_backend.dtos.OrderItemDtos.OrderItemResponseDto;
import com.food_delivery.zomato_backend.service.OrderItemService.OrderItemServiceInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order-items")
@RequiredArgsConstructor
@Tag(name = "Order Items", description = "OrderItem Management APIs")
public class OrderItemController {
    private final OrderItemServiceInterface orderItemService;

    @Operation(summary = "Create a new order item")
    @ApiResponse(responseCode = "201", description = "Order item created successfully")
    @PostMapping
    public ResponseEntity<OrderItemResponseDto> createOrderItem(@Valid @RequestBody OrderItemRequestDto orderItemRequestDto) {
        return ResponseEntity.status(201).body(orderItemService.saveOrderItem(orderItemRequestDto));
    }

    /// Read endpoints
     @Operation(summary = "Get a order item by id")
     @ApiResponses(value ={
             @ApiResponse(responseCode = "200", description = "Order item found successfully"),
             @ApiResponse(responseCode = "404", description = "Order item not found")
     })
    @GetMapping("/{orderItemId}")
    public ResponseEntity<OrderItemResponseDto> getOrderItem(Long orderItemId){
        return ResponseEntity.ok(orderItemService.getOrderItem(orderItemId));
    }


    ///Update endpoints
    @Operation(summary = "Update a order item by id")
   @ApiResponses(value =
           {
                   @ApiResponse(responseCode = "200", description = "Order item found successfully"),
                   @ApiResponse(responseCode = "404", description = "Order item not found")
           })
    @PutMapping("/{orderItemId}")
    public ResponseEntity<OrderItemResponseDto> updateOrderItem(@PathVariable Long orderItemId, @Valid @RequestBody OrderItemRequestDto orderItemRequestDto){
        return ResponseEntity.ok(orderItemService.updateOrderItem(orderItemId, orderItemRequestDto));
    }
    @Operation(summary = "Partially update a order item by id")
    @ApiResponses(value =
            {
                    @ApiResponse(responseCode = "200", description = "Order item found successfully"),
                    @ApiResponse(responseCode = "404", description = "Order item not found")
            })
    @PatchMapping("/{orderItemId}")
    public ResponseEntity<OrderItemResponseDto> partialUpdate(@PathVariable Long orderItemId, @Valid @RequestBody OrderItemRequestDto orderItemRequestDto){
        return ResponseEntity.ok(orderItemService.updateOrderItem(orderItemId, orderItemRequestDto));
    }

    ///delete endpoints
    @Operation(summary = "Delete a order item by id")
    @ApiResponses(value =
            {
                    @ApiResponse(responseCode = "204", description = "Order item deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Order item not found")
            })
    @DeleteMapping("/{orderItemId}")
    public ResponseEntity<String> deleteOrderItem(@PathVariable Long orderItemId){
        orderItemService.deleteOrderItem(orderItemId);
        return ResponseEntity.status(204).body("Order item with id " + orderItemId + " deleted successfully");
    }
}
