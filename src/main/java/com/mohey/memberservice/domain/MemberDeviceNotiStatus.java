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
public class MemberDeviceNotiStatus {

	@GeneratedValue
	@Id
	long id;

	@OneToOne(fetch = LAZY)
	@JoinColumn(name = "member_device_id")
	MemberDevice memberDeviceId;

	@Column(nullable = false)
	private Boolean notiStatus = true;

	@CreatedDate
	@Column(nullable = false)
	private LocalDateTime createdDatetime;

	@Builder
	public MemberDeviceNotiStatus(long id, MemberDevice memberDeviceId, Boolean notiStatus,
		LocalDateTime createdDatetime) {
		this.id = id;
		this.memberDeviceId = memberDeviceId;
		this.notiStatus = notiStatus;
		this.createdDatetime = createdDatetime;
	}
}

