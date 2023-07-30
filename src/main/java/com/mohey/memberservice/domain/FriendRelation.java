package com.mohey.memberservice.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Getter
@Entity
public class FriendRelation {

    @GeneratedValue
    @Id
    long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    Member memberId;

    @ManyToOne
    @JoinColumn(name = "friend_id")
    Member friendId;

    @Column(nullable = false)
    private Boolean favoriteStatus;

    @Column(nullable = false)
    private Boolean fiendStatus;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdDatetime;

}
