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
public class MemberDevice {

    @GeneratedValue
    @Id
    long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    Member memberId;

    @Column(nullable = false, length = 36)
    private String deviceUuid;

    @Column(nullable = false, length = 255)
    private String deviceToken;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdDatetime;

    @OneToOne(mappedBy = "memberDeviceId", fetch = LAZY)
    private MemberDeviceAliveStatus memberDeviceAliveStatus;

    @OneToOne(mappedBy = "memberDeviceId", fetch = LAZY)
    private MemberDeviceNotiStatus memberDeviceNotiStatus;

}
