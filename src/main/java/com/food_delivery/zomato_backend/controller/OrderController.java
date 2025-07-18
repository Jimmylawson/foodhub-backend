package com.food_delivery.zomato_backend.controller;


import com.food_delivery.zomato_backend.dtos.OrderDtos.OrderRequestDto;
import com.food_delivery.zomato_backend.dtos.OrderDtos.OrderResponseDto;
import com.food_delivery.zomato_backend.service.OrderService.OrderServiceInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Tag(name= "Order", description = "Order Management APIs")
public class OrderController {
    private final OrderServiceInterface orderServiceInterface;

    @Operation(summary = "Create a new order")
    @ApiResponse(responseCode = "201", description = "Order created successfully")
    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(@Valid @RequestBody OrderRequestDto orderRequestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(orderServiceInterface.saveOrder(orderRequestDto));
    }

    /// Read endpoints
    @Operation(summary = "Get a order by id")
    @ApiResponses(value ={
            @ApiResponse(responseCode = "200", description = "Order found successfully"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDto> getMyOrder(@PathVariable Long orderId){
        return ResponseEntity.ok(orderServiceInterface.getOrder(orderId));
    }

    @Operation(summary = "Get all orders")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Orders found successfully"),
            @ApiResponse(responseCode = "404", description = "Orders not found")
    })
    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<OrderResponseDto>> getAllOrders(@PageableDefault(size = 10,
    sort="createdAt") Pageable pageable){
        return ResponseEntity.ok(orderServiceInterface.getAllOrders(pageable));
    }

    ///Update endpoints
    @Operation(summary = "Update a order by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order found successfully"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    @PutMapping("/{orderId}")
    public ResponseEntity<OrderResponseDto> updateOrder(@PathVariable Long orderId, @Valid @RequestBody OrderRequestDto orderRequestDto){
        return ResponseEntity.ok(orderServiceInterface.updateOrder(orderId, orderRequestDto));
    }
    @Operation(summary = "Partially update a order by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order found successfully"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    @PatchMapping("/{orderId}")
    public ResponseEntity<OrderResponseDto> partialUpdate(@PathVariable Long orderId, @Valid @RequestBody OrderRequestDto orderRequestDto){
        return ResponseEntity.ok(orderServiceInterface.updateOrder(orderId, orderRequestDto));
    }

    ///delete endpoints
    @Operation(summary = "Delete a order by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Order deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long orderId){
        orderServiceInterface.deleteOrder(orderId);
        return ResponseEntity.noContent().build();
    }

}
