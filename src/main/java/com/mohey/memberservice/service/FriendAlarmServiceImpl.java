package com.mohey.memberservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.mohey.memberservice.domain.AlarmStatusEnum;
import com.mohey.memberservice.domain.FriendRequest;
import com.mohey.memberservice.domain.FriendRequestStatus;
import com.mohey.memberservice.domain.Member;
import com.mohey.memberservice.domain.MemberDevice;
import com.mohey.memberservice.domain.MemberDeviceNotiStatus;
import com.mohey.memberservice.domain.MemberInfo;
import com.mohey.memberservice.dto.memberalarm.FriendReqAlarmReqDto;
import com.mohey.memberservice.dto.memberalarm.FriendReqAlarmRespDto;
import com.mohey.memberservice.dto.memberalarm.FriendRespAlarmRespDto;
import com.mohey.memberservice.dto.memberalarm.NotificationDto;
import com.mohey.memberservice.ex.CustomApiException;
import com.mohey.memberservice.kafka.KafkaProducer;
import com.mohey.memberservice.repository.FriendRelationRepository;
import com.mohey.memberservice.repository.FriendRequestRepository;
import com.mohey.memberservice.repository.FriendRequestStatusRepository;
import com.mohey.memberservice.repository.MemberDeviceNotiStatusRepository;
import com.mohey.memberservice.repository.MemberDeviceRepository;
import com.mohey.memberservice.repository.MemberInfoRepository;
import com.mohey.memberservice.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FriendAlarmServiceImpl implements FriendAlarmService {
	private final MemberRepository memberRepository;
	private final MemberInfoRepository memberInfoRepository;
	private final MemberDeviceRepository memberDeviceRepository;
	private final MemberDeviceNotiStatusRepository memberDeviceNotiStatusRepository;
	private final FriendRequestRepository friendRequestRepository;
	private final KafkaProducer kafkaProducer;
	private final FriendRelationRepository friendRelationRepository;
	private final FriendRequestStatusRepository friendRequestStatusRepository;

	@Transactional
	@Override
	public FriendReqAlarmRespDto sendAlarm(FriendReqAlarmReqDto friendReqAlarmReqDto, String uuid) {
		try {
			Member my = memberRepository.findByMemberUuid(friendReqAlarmReqDto.getMyUuid());
			Member friend = memberRepository.findByMemberUuid(friendReqAlarmReqDto.getFriendUuid());
			if (my == null || friend == null) {
				throw new CustomApiException("사용자가 없습니다.");
			}
			if (friendRelationRepository.existsByMemberIdAndFriendIdAndFriendStatusTrue(my, friend)) {
				throw new CustomApiException("이미 친구입니다.");
			}
			;
			Long id = friendRequestRepository.findLatestIdByMemberIdAndResponseId(my, friend);
			if (friendRequestStatusRepository.existsByIdAndStatus(id, AlarmStatusEnum.WAIT)) {
				throw new CustomApiException("이미 요청한 친구 입니다.");
			}
			FriendRequest friendRequest =
				friendReqAlarmReqDto.toFriendRequestEntity(my, friend, null, uuid);

			FriendRequestStatus friendRequestStatus = FriendRequestStatus.builder()
				.status(AlarmStatusEnum.valueOf("WAIT"))
				.build();

			friendRequestStatus.setFriendRequest(friendRequest); // FriendRequest와 연결
			friendRequest.setFriendRequestStatus(friendRequestStatus); // 양방향 연관관계 설정

			friendRequestRepository.save(friendRequest); // 한 번만 저장하여 FriendRequestStatus 함께 저장

			//알람 보내기
			MemberInfo myinfo = memberInfoRepository.findMemberInfoByMemberId(my);
			MemberInfo youinfo = memberInfoRepository.findMemberInfoByMemberId(friend);
			List<String> deviceTokenList = new ArrayList<>();
			deviceTokenList = memberDeviceRepository.getDeviceToken(friend);

			NotificationDto notificationDto = NotificationDto.builder()
				.topic("friend-request")
				.type("friend")
				.senderUuid(my.getMemberUuid())
				.senderName(myinfo.getNickname())
				.receiverName(youinfo.getNickname())
				.receiverUuid(friend.getMemberUuid())
				.deviceTokenList(deviceTokenList)
				.build();
			kafkaProducer.send("friend-request", notificationDto);

			return new FriendReqAlarmRespDto(friendRequest);

		} catch (DataIntegrityViolationException e) {
			throw new CustomApiException("알림보내기 실패");
		}
	}

	@Transactional
	@Override
	public Boolean stopAlarm(String deviceUuid) {
		try {
			MemberDevice memberDevice = memberDeviceRepository.findMemberDeviceByDeviceUuid(deviceUuid);
			Optional<MemberDeviceNotiStatus> latestStatus = memberDeviceNotiStatusRepository.findFirstByMemberDeviceIdOrderByCreatedDatetimeDesc(
				memberDevice);
			MemberDeviceNotiStatus returnMemberDeviceNotiStatus = null;
			if (latestStatus.isPresent()) {
				MemberDeviceNotiStatus status = latestStatus.get();
				MemberDeviceNotiStatus memberDeviceNotiStatus = MemberDeviceNotiStatus.builder()
					.memberDeviceId(memberDevice)
					.notiStatus(!status.getNotiStatus())
					.build();
				returnMemberDeviceNotiStatus = memberDeviceNotiStatusRepository.save(memberDeviceNotiStatus);
				return returnMemberDeviceNotiStatus.getNotiStatus();

			} else {
				throw new CustomApiException("등록된 기기기 아닙니다.");
			}

		} catch (DataIntegrityViolationException e) {
			throw new CustomApiException("알림변경 실패");
		}
	}

	@Transactional
	@Override
	public List<FriendRespAlarmRespDto> getFriendReqList(String memberUuid) {
		try {
			Member member = memberRepository.findByMemberUuid(memberUuid);
			if (member == null) {
				throw new CustomApiException("사용자가 없습니다.");
			}
			List<FriendRespAlarmRespDto> friendRespAlarmRespDtoList = new ArrayList<>();
			friendRespAlarmRespDtoList = friendRequestRepository.getFriendReqList(member.getId());
			if (friendRespAlarmRespDtoList.isEmpty()) {
				throw new CustomApiException("친구요청이 없습니다.");
			}
			return friendRespAlarmRespDtoList;
		} catch (DataIntegrityViolationException e) {
			throw new CustomApiException("친구리스트 실패");
		}
	}
}

