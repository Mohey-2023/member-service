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
public class FriendRelationFavoriteStatus {

    @GeneratedValue
    @Id
    long id;

    @OneToOne
    @JoinColumn(name = "friend_relation_id")
    FriendRelation friendRelationId;

    @Column(nullable = false)
    private Boolean favoriteStatus;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdDatetime;

}
