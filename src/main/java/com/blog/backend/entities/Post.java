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
    @Column(name = "post_id", nullable = false)
    private Integer id;

    @Column(name = "post_title", length = 1000)
    private String postTitle;

    @Column(name = "post_description", length = 10000)
    private String postDescription;

    @Column(name = "image_base")
    private String imageBase;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)//"user_id" is the primary key of table users
    @JsonBackReference //Back reference from (foreign key) to "user_id" that's inside "users" table
    private User user; //foreign key from table "users"
    @Column(name = "created_at")
    private LocalDateTime createdAt;


    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    @OneToMany(mappedBy = "post")
    @JsonManagedReference
    private Set<Comment> comments = new LinkedHashSet<>();







}
