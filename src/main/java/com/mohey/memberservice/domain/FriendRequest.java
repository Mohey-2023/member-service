package com.mohey.memberservice.domain;

import java.time.LocalDateTime;

import javax.persistence.*;

import lombok.Builder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.NoArgsConstructor;

import static javax.persistence.FetchType.LAZY;

@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Getter
@Entity
@Table(name = "friend_request_tb")
public class FriendRequest {
	@GeneratedValue
	@Id
	Long id;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "member_tb_id")
	Member memberId;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "response_id")
	Member responseId;

	@CreatedDate
	@Column(nullable = false)
	private LocalDateTime createdDatetime;
	@Builder
	public FriendRequest(Long id, Member memberId, Member responseId, LocalDateTime createdDatetime) {
		this.id = id;
		this.memberId = memberId;
		this.responseId = responseId;
		this.createdDatetime = createdDatetime;
	}
}
