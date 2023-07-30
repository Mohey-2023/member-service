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
public class Member {

    @GeneratedValue
    @Id
    long id;

    @Column(nullable = false, length = 36)
    private String memberUuid;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GenderEnum gender;

    @Column(nullable = false)
    private LocalDateTime birthDate;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdDatetime;
}
