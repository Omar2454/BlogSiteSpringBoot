package com.blog.backend.entities;

import com.blog.backend.Serializers.TestSerializer;
import com.blog.backend.entities.enums.Reacts;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "reacts")
@ToString
public class React {
    @EmbeddedId
    private ReactId id;

    @MapsId("user")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user", nullable = false)
    private User user;

    @MapsId("post")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "post", nullable = false)
    private Post post;

    @Enumerated(EnumType.STRING)
    @Column(name = "Emoji", nullable = false)
    private Reacts emoji;

}
