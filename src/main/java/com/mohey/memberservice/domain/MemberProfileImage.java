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
public class MemberProfileImage {
	@GeneratedValue
	@Id
	long id;

	@OneToOne(fetch = LAZY)
	@JoinColumn(name = "membeR_id")
	Member memberId;

	@Column(nullable = false, length = 255)
	private String profileUrl;

	@CreatedDate
	@Column(nullable = false)
	private LocalDateTime createdDatetime;

	@Builder
	public MemberProfileImage(long id, Member memberId, String profileUrl, LocalDateTime createdDatetime) {
		this.id = id;
		this.memberId = memberId;
		this.profileUrl = profileUrl;
		this.createdDatetime = createdDatetime;
	}
}
