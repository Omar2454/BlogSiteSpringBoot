package com.blog.backend.repos;

import com.blog.backend.entities.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PostRepository extends JpaRepository<Post, Integer> {

    Page<Post> findAllByUserId(Integer userId, Pageable pageable);
}
