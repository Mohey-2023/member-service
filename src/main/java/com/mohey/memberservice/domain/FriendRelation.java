package com.mohey.memberservice.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.NoArgsConstructor;

import static javax.persistence.FetchType.LAZY;

@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Getter
@Entity
public class FriendRelation {

	@GeneratedValue
	@Id
	Long id;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "member_id")
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

}
