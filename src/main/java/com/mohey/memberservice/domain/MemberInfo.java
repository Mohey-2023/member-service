package com.mohey.memberservice.domain;

import static javax.persistence.FetchType.*;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Getter
@Entity
public class MemberInfo {

	@GeneratedValue
	@Id
	long id;

	@OneToOne(fetch = LAZY)
	@JoinColumn(name = "member_id")
	Member memberId;

	@Column(nullable = false, length = 100)
	private String selfIntroduction;

	@Column(nullable = false, length = 10)
	private String nickname;

	@CreatedDate
	@Column(nullable = false)
	private LocalDateTime createdDatetime;

	@Builder
	public MemberInfo(long id, Member memberId, String selfIntroduction, String nickname,
		LocalDateTime createdDatetime) {
		this.id = id;
		this.memberId = memberId;
		this.selfIntroduction = selfIntroduction;
		this.nickname = nickname;
		this.createdDatetime = createdDatetime;
	}
}
