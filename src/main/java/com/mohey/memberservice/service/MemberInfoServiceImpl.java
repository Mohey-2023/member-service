package com.mohey.memberservice.service;import org.springframework.dao.DataIntegrityViolationException;import org.springframework.stereotype.Service;import org.springframework.transaction.annotation.Transactional;import com.mohey.memberservice.domain.Member;import com.mohey.memberservice.dto.memberInfo.GetInfoRespDto;import com.mohey.memberservice.ex.CustomApiException;import com.mohey.memberservice.repository.MemberInfoRepository;import com.mohey.memberservice.repository.MemberRepository;import lombok.RequiredArgsConstructor;@RequiredArgsConstructor@Servicepublic class MemberInfoServiceImpl implements MemberInfoService {	private final MemberRepository memberRepository;	private final MemberInfoRepository memberInfoRepository;	@Transactional	@Override	public GetInfoRespDto getInfo(String uuid) {		try {			Member member = memberRepository.findByMemberUuid(uuid);			Long memberId = member.getId();			GetInfoRespDto getInfoRespDto = memberInfoRepository.getUserInfo(memberId);			return getInfoRespDto;		} catch (DataIntegrityViolationException e) {			throw new CustomApiException("실패");		}	}}