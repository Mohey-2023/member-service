package com.mohey.memberservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mohey.memberservice.domain.AlarmStatusEnum;
import com.mohey.memberservice.domain.FriendRelation;
import com.mohey.memberservice.domain.FriendRequest;
import com.mohey.memberservice.domain.FriendRequestStatus;
import com.mohey.memberservice.domain.Member;
import com.mohey.memberservice.domain.MemberInfo;
import com.mohey.memberservice.dto.memberFriend.FriendDeleteReqDto;
import com.mohey.memberservice.dto.memberFriend.FriendDeleteRespDto;
import com.mohey.memberservice.dto.memberFriend.FriendRegisterReqDto;
import com.mohey.memberservice.dto.memberFriend.FriendRegisterRespDto;
import com.mohey.memberservice.dto.memberFriend.FriendStarReqDto;
import com.mohey.memberservice.dto.memberFriend.FriendStarRespDto;
import com.mohey.memberservice.dto.memberalarm.AlarmRequest;
import com.mohey.memberservice.dto.memberalarm.NotificationDto;
import com.mohey.memberservice.ex.CustomApiException;
import com.mohey.memberservice.kafka.KafkaProducer;
import com.mohey.memberservice.repository.FriendRelationFavoriteStatusRepository;
import com.mohey.memberservice.repository.FriendRelationRepository;
import com.mohey.memberservice.repository.FriendRelationStatusRepository;
import com.mohey.memberservice.repository.FriendRequestRepository;
import com.mohey.memberservice.repository.FriendRequestStatusRepository;
import com.mohey.memberservice.repository.MemberDeviceRepository;
import com.mohey.memberservice.repository.MemberInfoRepository;
import com.mohey.memberservice.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FriendRequestResponseServiceImpl implements FriendRequestResponseService {

	private final MemberRepository memberRepository;
	private final MemberInfoRepository memberInfoRepository;
	private final MemberDeviceRepository memberDeviceRepository;
	private final FriendRelationStatusRepository friendRelationStatusRepository;
	private final FriendRelationRepository friendRelationRepository;
	private final FriendRelationFavoriteStatusRepository friendRelationFavoriteStatusRepository;
	private final FriendRequestStatusRepository friendRequestStatusRepository;
	private final FriendRequestRepository friendRequestRepository;
	private final KafkaProducer kafkaProducer;

	@Transactional
	@Override
	public FriendRegisterRespDto register(FriendRegisterReqDto friendRegisterReqDto) {

		try {
			Member my = memberRepository.findByMemberUuid(friendRegisterReqDto.getMyUuid());
			Member friend = memberRepository.findByMemberUuid(friendRegisterReqDto.getFriendUuid());
			if (my == null || friend == null) {
				throw new CustomApiException("사용자가 없습니다.");
			}
			boolean alreadyFriend = friendRelationRepository.existsByMemberIdAndFriendId(my, friend);
			boolean alreadyFriend2 = friendRelationRepository.existsByMemberIdAndFriendId(friend,
				my);
			//이미 친구가 있을떄는 에러 또는 상태변경
			if (alreadyFriend || alreadyFriend2) {
				FriendRelation friendRelation = friendRelationRepository.findByMemberIdAndFriendId(my, friend);
				FriendRelation friendRelation2 = friendRelationRepository.findByMemberIdAndFriendId(friend, my);
				if (friendRelation.getFriendStatus() || friendRelation2.getFriendStatus()) {
					throw new CustomApiException("이미 친구입니다");
				} else {
					friendRelation.changeFriendStatus(!friendRelation.getFriendStatus());
					friendRelation2.changeFriendStatus(!friendRelation2.getFriendStatus());
					friendRelationStatusRepository.save(
						friendRegisterReqDto.toFriendRelationStatusEntity(friendRelation));
					friendRelationStatusRepository.save(
						friendRegisterReqDto.toFriendRelationStatusEntity(friendRelation2));
					FriendRequest friendRequest = friendRequestRepository.findFriendRequestByAlarmUuid(
						friendRegisterReqDto.getAlarmUuid());

					Optional<FriendRequestStatus> friendRequestStatus = friendRequestStatusRepository.findById(
						friendRequest.getId());

					if (friendRequestStatus.isPresent()) {
						friendRequestStatus.get().changeStatus(AlarmStatusEnum.valueOf("YES"));

						//알람 전송
						MemberInfo myinfo = memberInfoRepository.findMemberInfoByMemberId(my);
						MemberInfo youinfo = memberInfoRepository.findMemberInfoByMemberId(friend);
						List<String> deviceTokenList = new ArrayList<>();
						deviceTokenList = memberDeviceRepository.getDeviceToken(friend);

						NotificationDto notificationDto = NotificationDto.builder()
							.topic("friend-accept")
							.type("friend")
							.senderUuid(my.getMemberUuid())
							.senderName(myinfo.getNickname())
							.receiverName(youinfo.getNickname())
							.receiverUuid(friend.getMemberUuid())
							.deviceTokenList(deviceTokenList)
							.build();
						kafkaProducer.send("friend-request", notificationDto);

					} else {
						throw new CustomApiException("친구요청 상태변경 실패");
					}
					return new FriendRegisterRespDto(friendRelation);
				}
			}

			FriendRelation friendRelation = friendRelationRepository.save(
				friendRegisterReqDto.toFriendRelationEntity(my, friend));
			friendRelationStatusRepository.save(friendRegisterReqDto.toFriendRelationStatusEntity(friendRelation));
			FriendRelation friendRelation2 = friendRelationRepository.save(
				friendRegisterReqDto.toFriendRelationEntity(friend, my));
			friendRelationStatusRepository.save(friendRegisterReqDto.toFriendRelationStatusEntity(friendRelation2));
			//친구 신청의 상태 승인으로 바꿔주기

			FriendRequest friendRequest = friendRequestRepository.findFriendRequestByAlarmUuid(
				friendRegisterReqDto.getAlarmUuid());

			Optional<FriendRequestStatus> friendRequestStatus = friendRequestStatusRepository.findById(
				friendRequest.getId());
			if (friendRequestStatus.isPresent()) {
				friendRequestStatus.get().changeStatus(AlarmStatusEnum.valueOf("YES"));

				//알람 전송
				MemberInfo myinfo = memberInfoRepository.findMemberInfoByMemberId(my);
				MemberInfo youinfo = memberInfoRepository.findMemberInfoByMemberId(friend);
				List<String> deviceTokenList = new ArrayList<>();
				deviceTokenList = memberDeviceRepository.getDeviceToken(friend);

				NotificationDto notificationDto = NotificationDto.builder()
					.topic("friend-accept")
					.type("friend")
					.senderUuid(my.getMemberUuid())
					.senderName(myinfo.getNickname())
					.receiverName(youinfo.getNickname())
					.receiverUuid(friend.getMemberUuid())
					.deviceTokenList(deviceTokenList)
					.build();
				kafkaProducer.send("friend-request", notificationDto);

			} else {
				throw new CustomApiException("친구요청 상태변경 실패");
			}

			return new FriendRegisterRespDto(friendRelation);
		} catch (DataIntegrityViolationException e) {
			throw new CustomApiException("친구등록 실패");
		}
	}

	@Transactional
	@Override
	public FriendStarRespDto Star(FriendStarReqDto friendStarReqDto) {

		try {
			Member my = memberRepository.findByMemberUuid(friendStarReqDto.getMyUuid());
			Member friend = memberRepository.findByMemberUuid(friendStarReqDto.getFriendUuid());
			if (my == null || friend == null) {
				throw new CustomApiException("사용자가 없습니다.");
			}
			boolean alreadyFriend = friendRelationRepository.existsByMemberIdAndFriendIdAndFriendStatusTrue(my, friend);
			if (!alreadyFriend) {
				throw new CustomApiException("친구가 아닙니다.");
			}
			//즐겨찾기 상태 업데이트
			FriendRelation friendRelation = friendRelationRepository.findByMemberIdAndFriendId(my, friend);

			System.out.println("테스트2" + friendRelation.getFavoriteStatus());
			friendRelation.changeFavoriteStatus(!friendRelation.getFavoriteStatus());
			System.out.println("테스트3" + friendRelation.getFavoriteStatus());

			//로그 남기기
			friendRelationFavoriteStatusRepository.save(
				friendStarReqDto.toFriendRelationFavoriteStatusEntity(friendRelation));

			return new FriendStarRespDto(friendRelation);
		} catch (DataIntegrityViolationException e) {
			throw new CustomApiException("즐겨찾기 실패");
		}
	}

	@Transactional
	@Override
	public FriendDeleteRespDto DeleteFriend(FriendDeleteReqDto friendDeleteReqDto) {
		try {
			Member my = memberRepository.findByMemberUuid(friendDeleteReqDto.getMyUuid());
			Member friend = memberRepository.findByMemberUuid(friendDeleteReqDto.getFriendUuid());
			if (my == null || friend == null) {
				throw new CustomApiException("사용자가 없습니다.");
			}
			boolean alreadyFriend = friendRelationRepository.existsByMemberIdAndFriendIdAndFriendStatusTrue(my, friend);
			if (!alreadyFriend) {
				throw new CustomApiException("친구가 아닙니다.");
			}

			FriendRelation friendRelation = friendRelationRepository.findByMemberIdAndFriendId(my, friend);
			FriendRelation friendRelation2 = friendRelationRepository.findByMemberIdAndFriendId(friend, my);
			if (!friendRelation.getFriendStatus()) {
				throw new CustomApiException("삭제한 친구입니다.");
			}
			friendRelation.changeFriendStatus(!friendRelation.getFriendStatus());
			friendRelation2.changeFriendStatus(!friendRelation2.getFriendStatus());

			friendRelationStatusRepository.save(friendDeleteReqDto.toFriendRelationStatusEntity(friendRelation));
			friendRelationStatusRepository.save(friendDeleteReqDto.toFriendRelationStatusEntity(friendRelation2));

			return new FriendDeleteRespDto(friendRelation);
		} catch (DataIntegrityViolationException e) {
			throw new CustomApiException("친구삭제 실패");
		}
	}

	@Transactional
	@Override
	public Boolean reject(AlarmRequest alarmUuid) {
		//알람 유유아이디로freindRequest객체 받아오고 그 pk로
		//친구신청 상태 테이블 가져와서 그냥 그 상태값을 NO로 바꾸면 끝. 그리고 반환
		System.out.println("qqqqqq" + alarmUuid.getAlarmUuid());
		try {
			FriendRequest friendRequest = friendRequestRepository.findFriendRequestByAlarmUuid(
				alarmUuid.getAlarmUuid());
			if (friendRequest == null) {
				throw new CustomApiException("신청 정보가 없어요");
			}
			FriendRequestStatus friendRequestStatus = friendRequestStatusRepository.findFriendRequestStatusById(
				friendRequest.getId());
			if (friendRequestStatus == null) {
				throw new CustomApiException("신청 정보가 없어요");
			}
			friendRequestStatus.changeStatus(AlarmStatusEnum.valueOf("NO"));
			return true;
		} catch (DataIntegrityViolationException e) {
			throw new CustomApiException("친구거절 실패");
		}
	}

}
