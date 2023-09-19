package com.blog.backend.repos;

import com.blog.backend.entities.React;
import com.blog.backend.entities.ReactId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReactRepository extends JpaRepository<React, ReactId> {

List<React> findAllReactByPostId(Integer postId);
}
