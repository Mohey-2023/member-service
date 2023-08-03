package com.mohey.memberservice.domain;

import static javax.persistence.FetchType.*;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIdentityInfo(
	generator = ObjectIdGenerators.PropertyGenerator.class,
	property = "id")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Getter
@Setter
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

	@OneToOne(mappedBy = "friendRequest", cascade = CascadeType.ALL, fetch = LAZY)
	private FriendRequestStatus friendRequestStatus;

	@CreatedDate
	@Column(nullable = false)
	private LocalDateTime createdDatetime;

	@Builder
	public FriendRequest(Long id, Member memberId, Member responseId, LocalDateTime createdDatetime
		, FriendRequestStatus friendRequestStatus) {
		this.id = id;
		this.memberId = memberId;
		this.responseId = responseId;
		this.createdDatetime = createdDatetime;
		this.friendRequestStatus = friendRequestStatus;
	}
}
