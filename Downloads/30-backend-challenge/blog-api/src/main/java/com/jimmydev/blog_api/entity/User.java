package com.jimmydev.blog_api.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@Getter
@Setter
@Builder
@NoArgsConstructor
@Table(name = "users")
public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String username;
    private String email;
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy="author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> post = new ArrayList<>();
    
    // Helper method to maintain bidirectional relationship
    public void addPost(Post post) {
        this.post.add(post);
        post.setAuthor(this);
    }
}
