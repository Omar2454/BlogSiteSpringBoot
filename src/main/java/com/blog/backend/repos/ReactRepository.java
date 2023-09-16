package com.blog.backend.repos;

import com.blog.backend.entities.React;
import com.blog.backend.entities.ReactId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReactRepository extends JpaRepository<React, ReactId> {


}
