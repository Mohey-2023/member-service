package com.mohey.memberservice.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Getter
@Entity
@Table(name = "friend_requst_status_tb")
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
    @Builder
    public FriendRequestStatus(Long id, FriendRequest friendRequest, Boolean status, LocalDateTime createdDatetime) {
        this.id = id;
        this.friendRequest = friendRequest;
        this.status = status;
        this.createdDatetime = createdDatetime;
    }
}