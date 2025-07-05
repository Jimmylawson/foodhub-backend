package com.jimmydev.blog_api.controllers;


import com.jimmydev.blog_api.dtos.userdto.UserRequestDto;
import com.jimmydev.blog_api.dtos.userdto.UserResponseDto;
import com.jimmydev.blog_api.service.UserService.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/user")
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserRequestDto userRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userRequestDto));
    }

    // GetMapping endpoints
    @GetMapping("/user/{id}")
    public ResponseEntity<UserResponseDto> getUserWithId(@Valid @PathVariable Long id){
        return ResponseEntity.ok().body(userService.getUser(id));
    }
    // Count Users
    @GetMapping("/users/count")
    public ResponseEntity<Map<String, Object>> countUsers() {
        Long count = userService.getUserCount();
        return ResponseEntity.ok(Map.of(
                "message", "Total users: " + count,
                "count", count
        ));
    }
    @GetMapping("/users")
    public ResponseEntity<Page<UserResponseDto>> getAllUsers(
            @PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable){
        return ResponseEntity.ok().body(userService.getAllUsers(pageable));
    }


    @DeleteMapping("/user/{id}")
    public ResponseEntity<String> deleteUser(@Valid @PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.ok().body("User with id " + id + " has been deleted");
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@Valid @PathVariable Long id, @Valid @RequestBody  UserRequestDto userRequestDto){
        return ResponseEntity.ok().body(userService.updateUser(id, userRequestDto));
    }



}
