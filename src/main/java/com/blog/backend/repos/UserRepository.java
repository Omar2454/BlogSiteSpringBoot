package com.blog.backend.repos;

import com.blog.backend.entities.Friendship;
import com.blog.backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);



}
