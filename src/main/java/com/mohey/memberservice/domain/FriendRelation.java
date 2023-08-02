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

@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Getter
@Entity
public class FriendRelation {

	@GeneratedValue
	@Id
	Long id;

	@ManyToOne
	@JoinColumn(name = "member_id")
	Member memberId;

	@ManyToOne
	@JoinColumn(name = "friend_id")
	Member friendId;

	@Column(nullable = false)
	private Boolean favoriteStatus;

	@Column(nullable = false)
	private Boolean fiendStatus;

	@CreatedDate
	@Column(nullable = false)
	private LocalDateTime createdDatetime;

}
