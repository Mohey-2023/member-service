package com.mohey.memberservice.service;import com.mohey.memberservice.domain.MemberDevice;import com.mohey.memberservice.domain.MemberInterest;import com.mohey.memberservice.repository.MemberInterestRepository;import org.springframework.dao.DataIntegrityViolationException;import org.springframework.stereotype.Service;import org.springframework.transaction.annotation.Transactional;import com.mohey.memberservice.domain.Member;import com.mohey.memberservice.dto.memberInfo.GetInfoRespDto;import com.mohey.memberservice.ex.CustomApiException;import com.mohey.memberservice.repository.MemberInfoRepository;import com.mohey.memberservice.repository.MemberRepository;import lombok.RequiredArgsConstructor;import java.util.ArrayList;import java.util.List;@RequiredArgsConstructor@Servicepublic class MemberInfoServiceImpl implements MemberInfoService {    private final MemberRepository memberRepository;    private final MemberInfoRepository memberInfoRepository;    private final MemberInterestRepository memberInterestRepository;    @Transactional    @Override    public GetInfoRespDto getInfo(String uuid) {        try {            Member member = memberRepository.findByMemberUuid(uuid);            if (member != null) {                Long memberId = member.getId();                GetInfoRespDto getInfoRespDto = memberInfoRepository.getUserInfo(memberId);                return getInfoRespDto;            } else {                throw new CustomApiException("회원정보가 없습니다.");            }        } catch (DataIntegrityViolationException e) {            throw new CustomApiException("실패");        }    }    @Override    public List<String> getInterest(String uuid) {        try {            List<String> interests = new ArrayList<>();            List<MemberInterest> interestList = new ArrayList<>();            Member member = memberRepository.findByMemberUuid(uuid);            if (member != null) {                interestList = memberInterestRepository.findMemberInterestsByMemberId(member);                // 조회한 장치의 deviceToken을 deviceToken 리스트에 추가                for (MemberInterest interest : interestList) {                    interests.add(interest.getInterest());                }            } else {                throw new CustomApiException("회원정보가 없습니다.");            }            return interests;        } catch (DataIntegrityViolationException e) {            throw new CustomApiException("실패");        }    }}