package com.mohey.memberservice.domain;

import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

public class FriendRequestStatus {
    @Id
    private Long id;
    @OneToOne(fetch = LAZY)
    @MapsId
    @JoinColumn(name = "friend_request_id")
    private FriendRequest friendRequest;

    @Column(nullable = false)
    private Boolean status;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdDatetime;
}