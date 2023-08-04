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
@Table(name  ="member_device_noti_status_tb")
public class MemberDeviceNotiStatus {

	@GeneratedValue
	@Id
	Long id;

	@OneToOne(fetch = LAZY)
	@JoinColumn(name = "member_device_tb_id")
	MemberDevice memberDeviceId;

	@Column(nullable = false)
	private Boolean notiStatus ;

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

