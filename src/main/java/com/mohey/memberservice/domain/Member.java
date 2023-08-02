package com.mohey.memberservice.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Getter
@Entity
public class Member {

	@GeneratedValue
	@Id
	Long id;

	@Column(nullable = false, length = 36)
	private String memberUuid;

	@Column(nullable = false)
	private GenderEnum gender;

	@Column(nullable = false)
	private String birthDate;

	@CreatedDate
	@Column(nullable = false)
	private LocalDateTime createdDatetime;

	@Builder
	public Member(long id, String memberUuid, GenderEnum gender, String birthDate,
		LocalDateTime createdDatetime) {
		this.id = id;
		this.memberUuid = memberUuid;
		this.gender = gender;
		this.birthDate = birthDate;
		this.createdDatetime = createdDatetime;
	}
}
