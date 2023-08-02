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
@Table(name  ="member_info_tb")
public class MemberInfo {

	@GeneratedValue
	@Id
	Long id;

	@OneToOne(fetch = LAZY)
	@JoinColumn(name = "member_tb_id")
	Member memberId;

	@Column(nullable = false, length = 100)
	private String selfIntroduction;

	@Column(nullable = false, length = 10)
	private String nickname;

	@CreatedDate
	@Column(nullable = false)
	private LocalDateTime createdDatetime;

	@Builder
	public MemberInfo(long id, Member memberId, String selfIntroduction, String nickname,
		LocalDateTime createdDatetime) {
		this.id = id;
		this.memberId = memberId;
		this.selfIntroduction = selfIntroduction;
		this.nickname = nickname;
		this.createdDatetime = createdDatetime;
	}
}
