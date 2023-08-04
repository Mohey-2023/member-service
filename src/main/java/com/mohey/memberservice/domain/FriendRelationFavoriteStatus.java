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
@Table(name = "friend_relation_favorite_status_tb")
public class FriendRelationFavoriteStatus {

	@GeneratedValue
	@Id
	Long id;

	@OneToOne(fetch = LAZY)
	@JoinColumn(name = "friend_relation_tb_id")
	FriendRelation friendRelationId;

	@Column(nullable = false)
	private Boolean favoriteStatus;

	@CreatedDate
	@Column(nullable = false)
	private LocalDateTime createdDatetime;
	@Builder
	public FriendRelationFavoriteStatus(Long id, FriendRelation friendRelationId, Boolean favoriteStatus, LocalDateTime createdDatetime) {
		this.id = id;
		this.friendRelationId = friendRelationId;
		this.favoriteStatus = favoriteStatus;
		this.createdDatetime = createdDatetime;
	}
}
