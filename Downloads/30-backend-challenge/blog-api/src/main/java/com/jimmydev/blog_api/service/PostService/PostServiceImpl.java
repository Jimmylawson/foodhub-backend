package com.jimmydev.blog_api.service.PostService;

import com.jimmydev.blog_api.dtos.postdtos.PostRequestDto;
import com.jimmydev.blog_api.dtos.postdtos.PostResponseDto;
import com.jimmydev.blog_api.exceptions.PostNotFoundException;
import com.jimmydev.blog_api.mapper.PostMapper;
import com.jimmydev.blog_api.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostMapper postMapper;
    private final PostRepository postRepository;
    @Override
    public PostResponseDto createPost(PostRequestDto postRequestDto) {
        var post = postMapper.toEntity(postRequestDto);

        return postMapper.toPostResponseDto(postRepository.save(post));
    }

    public PostResponseDto findPostById(Long id){
        return postMapper.toPostResponseDto(postRepository.findById(id).orElseThrow(()-> new PostNotFoundException("Post not found")));
    }

    @Override
    public PostResponseDto getPost(Long id) {
       return findPostById(id);
    }

    @Override
    public PostResponseDto updatePost(Long id, PostRequestDto postRequestDto) {
        return null;
    }

    @Override
    public void deletePost(Long id) {
        postRepository.deleteById(id);

    }

    @Override
    public Page<PostResponseDto> getAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(postMapper::toPostResponseDto);
    }

    @Override
    public List<PostResponseDto> getPostsByAuthor(Long authorId) {
        return postRepository.findByAuthorId(authorId)
                .stream()
                .map(postMapper::toPostResponseDto)
                .toList();
    }
}
