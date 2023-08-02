package com.mohey.memberservice.domain;

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

import lombok.Getter;
import lombok.NoArgsConstructor;

@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Getter
@Entity
public class FriendRelationStatus {

	@GeneratedValue
	@Id
	Long id;

	@OneToOne
	@JoinColumn(name = "friend_relation_id")
	FriendRelation friendRelationId;

	@Column(nullable = false)
	private Boolean friendStatus;

	@CreatedDate
	@Column(nullable = false)
	private LocalDateTime createdDatetime;

}
