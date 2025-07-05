package com.jimmydev.blog_api.controllers;


import com.jimmydev.blog_api.dtos.postdtos.PostRequestDto;
import com.jimmydev.blog_api.dtos.postdtos.PostResponseDto;
import com.jimmydev.blog_api.dtos.userdto.UserRequestDto;
import com.jimmydev.blog_api.dtos.userdto.UserResponseDto;
import com.jimmydev.blog_api.service.PostService.PostService;
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

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping("/post")
    public ResponseEntity<PostResponseDto> creatPost(@Valid @RequestBody PostRequestDto postRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.createPost(postRequestDto));
    }

    /// GetMapping endpoints
    @GetMapping("/post/{id}")
    public ResponseEntity<PostResponseDto> getPostWithId(@Valid @PathVariable Long id){
        return ResponseEntity.ok().body(postService.getPost(id));
    }

    @GetMapping("/posts")
    public ResponseEntity<Page<PostResponseDto>> getAllPost(
            @PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC)Pageable pageable){
        return ResponseEntity.ok().body(postService.getAllPosts(pageable));
    }


    @DeleteMapping("/post/{id}")
    public ResponseEntity<String> deletePost(@Valid @PathVariable Long id){
        postService.deletePost(id);
        return ResponseEntity.ok().body("Post with id " + id + " has been deleted");
    }

    @PutMapping("/post/{id}")
    public ResponseEntity<PostResponseDto> updatePost(@Valid @PathVariable Long id, @Valid @RequestBody PostRequestDto postRequestDto){
        return ResponseEntity.ok().body(postService.updatePost(id, postRequestDto));
    }
}
