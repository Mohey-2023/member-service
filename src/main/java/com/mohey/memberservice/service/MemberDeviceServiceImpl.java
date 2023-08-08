
package com.mohey.memberservice.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mohey.memberservice.domain.Member;
import com.mohey.memberservice.domain.MemberDevice;
import com.mohey.memberservice.domain.MemberInfo;
import com.mohey.memberservice.dto.memberalarm.TokenRespDto;
import com.mohey.memberservice.dto.memberdevice.DeviceRegisterReqDto;
import com.mohey.memberservice.dto.memberdevice.DeviceRegisterRespDto;
import com.mohey.memberservice.ex.CustomApiException;
import com.mohey.memberservice.repository.MemberDeviceAliveStatusRepository;
import com.mohey.memberservice.repository.MemberDeviceNotiStatusRepository;
import com.mohey.memberservice.repository.MemberDeviceRepository;
import com.mohey.memberservice.repository.MemberInfoRepository;
import com.mohey.memberservice.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberDeviceServiceImpl implements MemberDeviceService {

	private final MemberDeviceRepository memberDeviceRepository;
	private final MemberDeviceAliveStatusRepository memberDeviceAliveStatusRepository;
	private final MemberDeviceNotiStatusRepository memberDeviceNotiStatusRepository;
	private final MemberRepository memberRepository;
	private final MemberInfoRepository memberInfoRepository;

	@Transactional
	@Override
	public DeviceRegisterRespDto registerDevice(DeviceRegisterReqDto deviceRegisterReqDto) {
		try {
			Member member = memberRepository.findByMemberUuid(deviceRegisterReqDto.getMemberUuid());
			System.out.println("테스트트트" + member);
			MemberDevice memberDevice = memberDeviceRepository.save(deviceRegisterReqDto.toMemberDeviceEntity(member));
			memberDeviceAliveStatusRepository.save(deviceRegisterReqDto.toMemberDeviceAliveStatusEntity(memberDevice));
			memberDeviceNotiStatusRepository.save(deviceRegisterReqDto.toMemberDeviceNotiStatusEntity(memberDevice));
			return new DeviceRegisterRespDto(member);
		} catch (DataIntegrityViolationException e) {
			throw new CustomApiException("기기등록 실패");
		}
	}

	@Transactional
	@Override
	public TokenRespDto getToken(String receiverUuid) {
		try {
			List<String> receiverDeviceToken = new ArrayList<>();

			Member reciver = memberRepository.findByMemberUuid(receiverUuid);

			if (reciver != null) {

				receiverDeviceToken = memberDeviceRepository.getDeviceToken(reciver);

				MemberInfo receiverName = memberInfoRepository.findMemberInfoByMemberId(reciver);
				TokenRespDto tokenRespDto = new TokenRespDto(receiverDeviceToken, receiverName.getNickname());

				return tokenRespDto;
			} else {
				throw new CustomApiException("회원정보가 없습니다.");
			}

		} catch (DataIntegrityViolationException e) {
			throw new CustomApiException("실패");
		}

	}
}

