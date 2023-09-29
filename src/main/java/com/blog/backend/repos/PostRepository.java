package com.blog.backend.repos;

import com.blog.backend.entities.Post;
import com.blog.backend.entities.User;
import com.blog.backend.entities.enums.Privacy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PostRepository extends JpaRepository<Post, Integer> {


    List<Post> findAllByUserId(Integer userId);

    List<Post> findBySharePost(Post sharePost);


    Page<Post> findAllByUserId(Integer userId, Pageable pageable);
    List<Post> findPostsByPrivacyIs(Privacy privacy);
    List<Post> findPostsByUserAndPrivacy(User user, Privacy privacy);

}
