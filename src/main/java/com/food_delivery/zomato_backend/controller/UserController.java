package com.food_delivery.zomato_backend.controller;

import com.food_delivery.zomato_backend.dtos.UserDtos.UserRequestDto;
import com.food_delivery.zomato_backend.dtos.UserDtos.UserResponseDto;
import com.food_delivery.zomato_backend.service.UserService.UserServiceInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name="User", description = "User Management APIs")
public class UserController {
    private final UserServiceInterface userService;

    @Operation(summary = "Create a new user")
    @ApiResponse(responseCode = "201", description = "User created successfully")
    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserRequestDto userRequestDto){
            /// Create the user
            return ResponseEntity.status(201).body(userService.createUser(userRequestDto));
        }

    /// Read endpoints
    @Operation(summary ="Get a user by id")
    @ApiResponses(value =
    {
        @ApiResponse(responseCode = "200", description = "User found successfully"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
     @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable Long userId){
        return ResponseEntity.ok(userService.getUser(userId));
    }

    @Operation(summary = "Get all users")
    @ApiResponses(value =
            {
                    @ApiResponse(responseCode = "200", description = "User found successfully"),
                    @ApiResponse(responseCode = "404", description = "User not found")
            })
    @GetMapping
    public ResponseEntity<Page<UserResponseDto>> getAllUsers(@PageableDefault(size = 10, sort ="createdAt") Pageable pageable){
         return ResponseEntity.ok(userService.getAllUsers(pageable));
    }


    @Operation(summary = "Update a user by id")
    @ApiResponses(value =
            {
                    @ApiResponse(responseCode = "200", description = "User found successfully"),
                    @ApiResponse(responseCode = "404", description = "User not found")
            })
    @PutMapping("/{userId}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long userId, @Valid @RequestBody UserRequestDto userRequestDto){
        return ResponseEntity.ok(userService.updateUser(userId, userRequestDto));
    }
    @Operation(summary = "Partially update a user by id")
    @ApiResponses(value =
            {
                    @ApiResponse(responseCode = "200", description = "User found successfully"),
                    @ApiResponse(responseCode = "404", description = "User not found")
            })
    @PatchMapping("/{userId}")
    public ResponseEntity<UserResponseDto> partialUpdate(@PathVariable Long userId, @Valid @RequestBody UserRequestDto userRequestDto){
        return ResponseEntity.ok(userService.updateUser(userId, userRequestDto));
    }

    ///delete endpoints
    @Operation(summary = "Delete a user by id")
    @ApiResponses(value =
            {
                    @ApiResponse(responseCode = "204", description = "User deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "User not found")
            })
    @DeleteMapping("/{userId}")
        public ResponseEntity<String> deleteUser(@PathVariable Long userId){
            userService.deleteUser(userId);
            return ResponseEntity.status(204).body("User with id " + userId + " deleted successfully");
        }

}
