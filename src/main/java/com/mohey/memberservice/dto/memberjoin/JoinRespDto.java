package com.mohey.memberservice.dto.memberjoin;import com.mohey.memberservice.domain.Member;import lombok.Getter;@Getterpublic class JoinRespDto {	private String memberUuid;	public JoinRespDto(Member member) {		this.memberUuid = member.getMemberUuid();	}}