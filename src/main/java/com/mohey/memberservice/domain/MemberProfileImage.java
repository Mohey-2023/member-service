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
@Table(name  ="member_profile_image_tb")
public class MemberProfileImage {
	@GeneratedValue
	@Id
	Long id;

	@OneToOne(fetch = LAZY)
	@JoinColumn(name = "member_tb_id")
	Member memberId;

	@Column(nullable = false, length = 255)
	private String profileUrl;

	@CreatedDate
	@Column(nullable = false)
	private LocalDateTime createdDatetime;

	@Builder
	public MemberProfileImage(long id, Member memberId, String profileUrl, LocalDateTime createdDatetime) {
		this.id = id;
		this.memberId = memberId;
		this.profileUrl = profileUrl;
		this.createdDatetime = createdDatetime;
	}
}
