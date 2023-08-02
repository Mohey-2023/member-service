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
@Table(name = "friend_relation_tb")
public class FriendRelation {

	@GeneratedValue
	@Id
	Long id;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "member_tb_id")
	Member memberId;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "friend_id")
	Member friendId;

	@Column(nullable = false)
	private Boolean favoriteStatus;

	@Column(nullable = false)
	private Boolean friendStatus;

	@CreatedDate
	@Column(nullable = false)
	private LocalDateTime createdDatetime;
	@Builder
	public FriendRelation(Long id, Member memberId, Member friendId, Boolean favoriteStatus, Boolean friendStatus, LocalDateTime createdDatetime) {
		this.id = id;
		this.memberId = memberId;
		this.friendId = friendId;
		this.favoriteStatus = favoriteStatus;
		this.friendStatus = friendStatus;
		this.createdDatetime = createdDatetime;
	}

	public void changeFavoriteStatus(boolean bol){
		this.favoriteStatus=bol;
	}
}
