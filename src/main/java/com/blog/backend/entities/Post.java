package com.blog.backend.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "postId", nullable = false)
    private Integer id;

    @Column(name = "post_title", length = 1000)
    private String postTitle;

    @Column(name = "post_description", length = 10000)
    private String postDescription;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;

    @OneToMany(mappedBy = "post")
    @JsonManagedReference
    private Set<Comment> comments = new LinkedHashSet<>();

    @Column(name = "post_url")
    private String postUrl;

    @Column(name = "created_at")
    private LocalDateTime createdAt;


    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


}
