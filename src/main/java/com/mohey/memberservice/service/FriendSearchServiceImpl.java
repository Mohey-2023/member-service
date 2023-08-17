package com.mohey.memberservice.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mohey.memberservice.domain.Member;
import com.mohey.memberservice.dto.memberFriend.FriendInFriendListSearchRespDto;
import com.mohey.memberservice.dto.memberFriend.FriendListSearchRespDto;
import com.mohey.memberservice.ex.CustomApiException;
import com.mohey.memberservice.repository.FriendSearchRepository;
import com.mohey.memberservice.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FriendSearchServiceImpl implements FriendSearchService {

	private final FriendSearchRepository friendSearchRepository;
	private final MemberRepository memberRepository;

	@Override
	public List<FriendListSearchRespDto> searchFriendList(String memberUuId) {

		List<FriendListSearchRespDto> friendList;
		try {
			Member my = memberRepository.findByMemberUuid(memberUuId);

			friendList = friendSearchRepository.findFriendDetailsByMemberId(my.getId());
			//System.out.println("getMy");

			//친구인 사용자 멤버 객체로 쭉 불러오기
			//List<Member> friendIdList = friendSearchRepository.findAllByMemberId(my.getId());
			 //System.out.println("getFriendIdList");
			//System.out.println(friendIdList.toString());
			//System.out.println(friendIdList.size());

			//            if (friendIdList.isEmpty()) {
			//                throw new CustomApiException("목록에 표시할 친구가 없습니다.");
			//            }
			//System.out.println(friendIdList.size());
			//System.out.println(friendIdList.get(0).getId());
			///!!!!!!여기서 추가해줬으면 리스트에서 빼자 !!!!!!!!

			//친한 친구 불러오기 << 여기서 favorite_status = null이 추가된다 왜지?? 계속 나에 대한 정보를 불러왔어서 그런듯
			//즐겨찾기를 하지 않으면 친구 목록이 뜨지 않는다...왜지?
//			friendList = friendSearchRepository.findFavoriteFriendList(friendIdList.get(0).getId(),my);
//			if (!friendList.isEmpty() && friendList.get(0).getMemberUuid().equals(friendIdList.get(0).getMemberUuid()))
//				friendIdList.remove(0);

			//System.out.println(friendIdList.size());
//			for (int i = 1; i < friendIdList.size(); i++) {
//				friendList.addAll(friendSearchRepository.findFavoriteFriendList(friendIdList.get(i).getId(),my));
//				//들어갔으면
//				if (friendList.get(friendList.size()-1).getMemberUuid().equals(friendIdList.get(i).getMemberUuid()))
//					friendIdList.remove(i);
//			}
			//System.out.println(friendIdList.size());

			//그냥 친구 불러오기
			//친한 친구...인걸 여기서도 불러오는구나
//			for (int i = 0; i < friendIdList.size(); i++) {
//				friendList.addAll(friendSearchRepository.findNotFavoriteFriendList(friendIdList.get(i).getId(),my));
//				System.out.println("i: "+ i);
//			}
			//System.out.println(friendList.size());
			//notFavoriteFriendList.addAll(favoriteFriendList) 이거는 리스트를 반환해주는 게아님 !! 함수

		} catch (IndexOutOfBoundsException e) {
			throw new CustomApiException("목록에 표시할 친구가 없습니다.");
		} catch (Exception e) {
			throw new CustomApiException("친구목록 가져오기 실패");
		}
		return friendList;
	}

	@Override
	public List<FriendInFriendListSearchRespDto> SearchFriendInFriendList(String nickname, String memberUuId) {

		List<FriendInFriendListSearchRespDto> SearchedFriendList;
		try {
			Member me = memberRepository.findByMemberUuid(memberUuId);

			SearchedFriendList = friendSearchRepository.findFriendInFriendDetailsByNickname(nickname + "%", me.getId());

			//검색한 닉네임인 친구 모두 찾기
//			List<Member> memberList = friendSearchRepository.findAllByNickname(nickname + "%", me);
//			System.out.println("get memberList");
//			System.out.println(memberList.size());
//
//			if (!memberList.isEmpty() && memberList.get(0).getMemberUuid().equals(memberList.get(0).getMemberUuid()))
//				memberList.remove(0);
//
//			//1. 친한 친구에 대한 정보
//			SearchedFriendList = friendSearchRepository.findFavoriteFriendById(memberList.get(0).getId(), me);
//			for (int i = 1; i < memberList.size(); i++) {
//				SearchedFriendList.addAll(
//					friendSearchRepository.findFavoriteFriendById(memberList.get(i).getId(), me));
//				//추가된 거면 제외해주기
//				if (memberList.get(memberList.size()-1).getMemberUuid().equals(memberList.get(i).getMemberUuid()))
//					memberList.remove(i);
//			}
//			//2. 그냥 친구에 대한 정보
//			for (int i = 0; i < memberList.size(); i++) {
//				SearchedFriendList.addAll(friendSearchRepository.findNotFavoriteFriendById(memberList.get(i).getId(), me));
//			}
		} catch (IndexOutOfBoundsException e) {

			System.out.println(e.getMessage());
			throw new CustomApiException("해당 닉네임을 가진 친구가 없습니다.");

		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new CustomApiException("친구 검색 실패");
		}
		return SearchedFriendList;
	}

	@Override
	public List<String> searchFeinFriendList(String memberUuId) {
		List<String> friendList = new ArrayList<>();
		try {
			Member my = memberRepository.findByMemberUuid(memberUuId);
			//System.out.println("getMy");

			//친구인 사용자 멤버 객체로 쭉 불러오기
			List<Member> friendIdList = friendSearchRepository.findAllByMemberId(my.getId());
			for (Member friend : friendIdList) {
				friendList.add(friend.getMemberUuid());
			}

		} catch (IndexOutOfBoundsException e) {
			throw new CustomApiException("목록에 표시할 친구가 없습니다.");
		} catch (Exception e) {
			throw new CustomApiException("친구목록 가져오기 실패");
		}
		return friendList;
	}
}
