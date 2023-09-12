package com.blog.backend.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "comments")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id", nullable = false)
    private Integer id;

    @Column(name = "comment_text", nullable = false, length = 10000)
    private String comment_text;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)//"user_id" is the primary key of table users
    @JsonBackReference //Back reference from (foreign key) to "user_id" that's inside "users" table
    private User user; //foreign key from table "users"

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_id", nullable = false)//"post_id" is the primary key of table posts
    @JsonBackReference //Back reference from (foreign key) to "post_id" that's inside posts table
    private Post post;// foreign key from table "posts"

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
