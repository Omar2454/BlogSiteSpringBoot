package com.blog.backend.repos;

import com.blog.backend.entities.Friendship;
import com.blog.backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface FriendshipRepository extends JpaRepository<Friendship, Integer> {

    Optional<Friendship> findByUserID1AndUserID2(User userID1, User userID2);
}
