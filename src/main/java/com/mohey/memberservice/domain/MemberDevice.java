package com.mohey.memberservice.domain;

import static javax.persistence.FetchType.*;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
public class MemberDevice {

	@GeneratedValue
	@Id
	long id;

	@ManyToOne
	@JoinColumn(name = "member_id")
	Member memberId;

	@Column(nullable = false, length = 36)
	private String deviceUuid;

	@Column(nullable = false, length = 255)
	private String deviceToken;

	@CreatedDate
	@Column(nullable = false)
	private LocalDateTime createdDatetime;

	@OneToOne(mappedBy = "memberDeviceId", fetch = LAZY)
	private MemberDeviceAliveStatus memberDeviceAliveStatus;

	@OneToOne(mappedBy = "memberDeviceId", fetch = LAZY)
	private MemberDeviceNotiStatus memberDeviceNotiStatus;

	@Builder
	public MemberDevice(long id, Member memberId, String deviceUuid, String deviceToken, LocalDateTime createdDatetime,
		MemberDeviceAliveStatus memberDeviceAliveStatus, MemberDeviceNotiStatus memberDeviceNotiStatus) {
		this.id = id;
		this.memberId = memberId;
		this.deviceUuid = deviceUuid;
		this.deviceToken = deviceToken;
		this.createdDatetime = createdDatetime;
		this.memberDeviceAliveStatus = memberDeviceAliveStatus;
		this.memberDeviceNotiStatus = memberDeviceNotiStatus;
	}
}
