package com.mohey.memberservice.domain;

import static javax.persistence.FetchType.*;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "friend_requst_status_tb")
public class FriendRequestStatus {
	@Id
	private Long id;

	@OneToOne(fetch = LAZY)
	@MapsId
	@JoinColumn(name = "id")
	private FriendRequest friendRequest;

	@Column(nullable = false)
	private AlarmStatusEnum status;

	@CreatedDate
	@Column(nullable = false)
	private LocalDateTime createdDatetime;

	@Builder
	public FriendRequestStatus(Long id, FriendRequest friendRequest, AlarmStatusEnum status,
		LocalDateTime createdDatetime) {
		this.id = id;
		this.friendRequest = friendRequest;
		this.status = status;
		this.createdDatetime = createdDatetime;
	}

	public void changeStatus(AlarmStatusEnum status) {
		this.status = status;
	}
}