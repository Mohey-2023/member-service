package com.mohey.memberservice.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mohey.memberservice.domain.FriendRelation;
import com.mohey.memberservice.domain.Member;
import com.mohey.memberservice.dto.memberFriend.FriendDeleteReqDto;
import com.mohey.memberservice.dto.memberFriend.FriendDeleteRespDto;
import com.mohey.memberservice.dto.memberFriend.FriendRegisterReqDto;
import com.mohey.memberservice.dto.memberFriend.FriendRegisterRespDto;
import com.mohey.memberservice.dto.memberFriend.FriendStarReqDto;
import com.mohey.memberservice.dto.memberFriend.FriendStarRespDto;
import com.mohey.memberservice.ex.CustomApiException;
import com.mohey.memberservice.repository.FriendRelationFavoriteStatusRepository;
import com.mohey.memberservice.repository.FriendRelationRepository;
import com.mohey.memberservice.repository.FriendRelationStatusRepository;
import com.mohey.memberservice.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FriendRequestResponseServiceImpl implements FriendRequestResponseService {

	private final MemberRepository memberRepository;
	private final FriendRelationStatusRepository friendRelationStatusRepository;
	private final FriendRelationRepository friendRelationRepository;
	private final FriendRelationFavoriteStatusRepository friendRelationFavoriteStatusRepository;

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
			//이미 친구가 있을떄는 에러 또는 상태변경
			if (alreadyFriend) {
				FriendRelation friendRelation = friendRelationRepository.findByMemberIdAndFriendId(my, friend);
				if (friendRelation.getFriendStatus()) {
					throw new CustomApiException("이미 친구입니다");
				} else {
					friendRelation.changeFriendStatus(!friendRelation.getFriendStatus());
					friendRelationStatusRepository.save(
						friendRegisterReqDto.toFriendRelationStatusEntity(friendRelation));
					return new FriendRegisterRespDto(friendRelation);
				}
			}

			FriendRelation friendRelation = friendRelationRepository.save(
				friendRegisterReqDto.toFriendRelationEntity(my, friend));
			friendRelationStatusRepository.save(friendRegisterReqDto.toFriendRelationStatusEntity(friendRelation));
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
			boolean alreadyFriend = friendRelationRepository.existsByMemberIdAndFriendId(my, friend);
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
			boolean alreadyFriend = friendRelationRepository.existsByMemberIdAndFriendId(my, friend);
			if (!alreadyFriend) {
				throw new CustomApiException("친구가 아닙니다.");
			}

			FriendRelation friendRelation = friendRelationRepository.findByMemberIdAndFriendId(my, friend);
			if (!friendRelation.getFriendStatus()) {
				throw new CustomApiException("삭제한 친구입니다.");
			}
			friendRelation.changeFriendStatus(!friendRelation.getFriendStatus());

			friendRelationStatusRepository.save(friendDeleteReqDto.toFriendRelationStatusEntity(friendRelation));

			return new FriendDeleteRespDto(friendRelation);
		} catch (DataIntegrityViolationException e) {
			throw new CustomApiException("친구삭제 실패");
		}
	}

}
