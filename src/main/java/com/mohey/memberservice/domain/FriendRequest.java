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
public class FriendRequest {
    @GeneratedValue
    @Id
    long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    Member memberId;

    @ManyToOne
    @JoinColumn(name = "response_id")
    Member responseId;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdDatetime;

}
