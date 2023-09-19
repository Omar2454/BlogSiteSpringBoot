package com.blog.backend.entities;

import com.blog.backend.entities.enums.Reacts;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "reacts")
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class React {
    @EmbeddedId
    private ReactId id;

    @MapsId("user")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user", nullable = false)
    @ToString.Exclude
    private User user;

    @MapsId("post")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "post", nullable = false)
    @ToString.Exclude
    private Post post;

    @Column(name = "Emoji", nullable = false)
    private Reacts emoji;

}
