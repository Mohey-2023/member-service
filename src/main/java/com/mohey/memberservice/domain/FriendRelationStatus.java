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
@Table(name = "friend_relation_status_tb")
public class FriendRelationStatus {

	@GeneratedValue
	@Id
	Long id;

	@OneToOne(fetch = LAZY)
	@JoinColumn(name = "friend_relation_id")
	FriendRelation friendRelationId;

	@Column(nullable = false)
	private Boolean friendStatus;

	@CreatedDate
	@Column(nullable = false)
	private LocalDateTime createdDatetime;
	@Builder
	public FriendRelationStatus(Long id, FriendRelation friendRelationId, Boolean friendStatus, LocalDateTime createdDatetime) {
		this.id = id;
		this.friendRelationId = friendRelationId;
		this.friendStatus = friendStatus;
		this.createdDatetime = createdDatetime;
	}
}
