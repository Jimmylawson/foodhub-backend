package com.jimmydev.blog_api.repository;

import com.jimmydev.blog_api.dtos.postdtos.PostResponseDto;
import com.jimmydev.blog_api.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {
    public List<Post> findByAuthorId(Long authorId);
}
