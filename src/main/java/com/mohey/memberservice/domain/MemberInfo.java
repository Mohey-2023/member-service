package com.mohey.memberservice.domain;

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
public class MemberInfo {

    @GeneratedValue
    @Id
    long id;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "member_id" )
    Member memberId;

    @Column(nullable = false, length = 100)
    private String selfIntroduction;

    @Column(nullable = false, length = 10)
    private String nickname;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdDatetime;

}
