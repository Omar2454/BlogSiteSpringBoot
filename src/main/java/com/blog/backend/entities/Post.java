package com.blog.backend.entities;

import com.blog.backend.Serializers.CommentSerializer;
import com.blog.backend.Serializers.ReactsSerializer;
import com.blog.backend.entities.enums.Privacy;
import com.blog.backend.entities.enums.Reacts;
import com.blog.backend.entities.enums.Role;
import com.fasterxml.jackson.annotation.*;
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
    @Column(name = "post_id", nullable = false , unique = false)
    private Integer id;

    @Column(name = "post_title", length = 1000)
    @JsonProperty(value = "title")
    private String postTitle;

    @Column(name = "post_description", length = 10000)
    @JsonProperty(value = "content")
    private String postDescription;

    @Column(name = "image_base")
    @JsonProperty(value = "image")
    @JsonIgnore
    private String imageBase;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    @OneToMany(mappedBy = "post")
    @JsonSerialize(using = CommentSerializer.class)
    private Set<Comment> comments = new LinkedHashSet<>();


    @OneToOne
    @JoinColumn(name = "shared_post_id",unique = false)
    private Post sharePost;



    @OneToMany(mappedBy = "post")
    @JsonManagedReference
    @JsonSerialize(using = ReactsSerializer.class)
    private Set<React> reacts = new LinkedHashSet<>();


    @Column(name = "number_of_reacts")
    private Integer numberOfReacts;

    @Column(name = "number_of_comment")
    private Integer numberOfComment;

    @Column(name = "privacy", nullable = true, length = 30)
    @JsonProperty("privacy")
    @Enumerated(EnumType.STRING)
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Privacy privacy;


    @Transient
    private Integer isReact;

}
