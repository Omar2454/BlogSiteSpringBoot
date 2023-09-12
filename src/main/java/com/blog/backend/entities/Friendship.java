package com.blog.backend.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "friendships")
public class Friendship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FriendshipID", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "UserID1", nullable = false)
    @JsonBackReference

    private User userID1;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "UserID2", nullable = false)
    @JsonBackReference
    private User userID2;

    @Lob
    @Column(name = "status")
    private String status;

    @Column(name = "accepted_at")
    private Instant acceptedAt;

    @Column(name = "requested_at")
    private Instant requestedAt;

    @Column(name = "friend_id")
    private Integer friendId;

}
