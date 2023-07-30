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
public class MemberDeviceNotiStatus {

    @GeneratedValue
    @Id
    long id;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "member_device_id")
    MemberDevice memberDeviceId;

    @Column(nullable = false)
    private Boolean notiStatus;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdDatetime;

}

