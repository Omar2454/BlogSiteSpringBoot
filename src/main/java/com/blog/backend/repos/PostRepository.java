package com.blog.backend.repos;

import com.blog.backend.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {

    List<Post> findAllByUserId(Integer userId);

    List<Post> findBySharePost(Post sharePost);


}
