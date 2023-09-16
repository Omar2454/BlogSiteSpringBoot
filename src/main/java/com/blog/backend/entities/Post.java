package com.blog.backend.entities;

import com.blog.backend.Serializers.CommentSerializer;
import com.blog.backend.Serializers.ReactsSerializer;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
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
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")//foreign key from table "users"
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    @OneToMany(mappedBy = "post")
    @JsonSerialize(using = CommentSerializer.class)
    private Set<Comment> comments = new LinkedHashSet<>();


    @OneToMany(mappedBy = "post")
    @JsonManagedReference
    @JsonSerialize(using = ReactsSerializer.class)
    private Set<React> reacts = new LinkedHashSet<>();

}
