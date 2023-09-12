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
    @Column(name = "commentId", nullable = false)
    private Integer id;

    @Column(name = "comment_text", nullable = false, length = 10000)
    private String comment_text;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "post_id", nullable = false)
    @JsonBackReference
    private Post post;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
