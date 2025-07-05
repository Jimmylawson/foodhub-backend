package com.jimmydev.blog_api.service.PostService;

import com.jimmydev.blog_api.dtos.postdtos.PostRequestDto;
import com.jimmydev.blog_api.dtos.postdtos.PostResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {
    public PostResponseDto createPost(PostRequestDto postRequestDto);
    public PostResponseDto getPost(Long id);
    public PostResponseDto updatePost(Long id, PostRequestDto postRequestDto);
    public void deletePost(Long id);
    Page<PostResponseDto> getAllPosts(Pageable pageable);
    List<PostResponseDto> getPostsByAuthor(Long authorId);


}
