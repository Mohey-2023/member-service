package com.mohey.memberservice.domain;

import static javax.persistence.FetchType.*;

import java.time.LocalDateTime;

import javax.persistence.*;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Getter
@Entity
@Table(name  ="member_device_alive_status_tb")
public class MemberDeviceAliveStatus {

	@GeneratedValue
	@Id
	Long id;

	@OneToOne(fetch = LAZY)
	@JoinColumn(name = "member_device_tb_id")
	MemberDevice memberDeviceId;

	@Column(nullable = false)
	private Boolean aliveStatus ;

	@CreatedDate
	@Column(nullable = false)
	private LocalDateTime createdDatetime;

	@Builder
	public MemberDeviceAliveStatus(long id, MemberDevice memberDeviceId, Boolean aliveStatus,
		LocalDateTime createdDatetime) {
		this.id = id;
		this.memberDeviceId = memberDeviceId;
		this.aliveStatus = aliveStatus;
		this.createdDatetime = createdDatetime;
	}
}
