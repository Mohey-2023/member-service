package com.mohey.memberservice.service;import org.springframework.stereotype.Service;import org.springframework.transaction.annotation.Transactional;import com.mohey.memberservice.domain.Member;import com.mohey.memberservice.dto.memberInfo.getInfoRespDto;import com.mohey.memberservice.repository.MemberRepository;import lombok.RequiredArgsConstructor;@RequiredArgsConstructor@Servicepublic class MemberInfoServiceImpl implements MemberInfoService {	private final MemberRepository memberRepository;	@Transactional	@Override	public getInfoRespDto getInfo(String uuid) {		Member member = memberRepository.findByMemberUuid(uuid);		long memberId = member.getId();		return null;	}}