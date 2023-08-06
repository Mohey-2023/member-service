package com.mohey.memberservice.service;

import com.mohey.memberservice.domain.Member;
import com.mohey.memberservice.dto.memberFriend.FriendInFriendListSearchRespDto;
import com.mohey.memberservice.dto.memberFriend.FriendListSearchRespDto;
import com.mohey.memberservice.ex.CustomApiException;
import com.mohey.memberservice.repository.FriendSearchRepository;
import com.mohey.memberservice.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class FriendSearchServiceImpl implements FriendSearchService {

    private final FriendSearchRepository friendSearchRepository;
    private final MemberRepository memberRepository;

    @Override
    public List<FriendListSearchRespDto> searchFriendList(String memberUuId) {

        try{
            Member my = memberRepository.findByMemberUuid(memberUuId);

            //1. 그냥 친구 불러오기
            List<FriendListSearchRespDto> friendList = friendSearchRepository.findNotFavoriteFriendList(my.getId());
            //2. 친한 친구 불러오기
            friendList.addAll(friendSearchRepository.findFavoriteFriendList(my.getId()));

            //notFavoriteFriendList.addAll(favoriteFriendList) 이거는 리스트를 반환해주는 게아님 !! 함수

            if (!friendList.isEmpty()){
                return friendList;
            } else {
                throw new CustomApiException("목록에 표시할 친구가 없습니다.");

            }

        } catch(Exception e) {
            throw new CustomApiException("친구목록 가져오기 실패");
        }
    }

    @Override
    public List<FriendInFriendListSearchRespDto> SearchFriendInFriendList(String nickname, String memberUuId) {

        List<FriendInFriendListSearchRespDto> SearchedFriendList;
        try {
            Member me = memberRepository.findByMemberUuid(memberUuId);

            //검색한 닉네임인 친구 모두 찾기
            List<Member> memberList = friendSearchRepository.findAllByNickname(nickname, me);
            System.out.println("get memberList");
            System.out.println(memberList.size());

            for (int i = 0; i < memberList.size(); i++) {
                System.out.println(memberList.get(i).toString());
            }

            //1. 그냥 친구에 대한 정보
            SearchedFriendList = friendSearchRepository.findNotFavoriteFriendById(memberList.get(0).getId(), me);
            for (int i = 1; i < memberList.size(); i++) {
                SearchedFriendList.addAll(friendSearchRepository.findNotFavoriteFriendById(memberList.get(i).getId(), me));
            }
            //2. 친한 친구에 대한 정보
            for (int i = 0; i < memberList.size(); i++) {
                SearchedFriendList.addAll(friendSearchRepository.findFavoriteFriendById(memberList.get(i).getId(), me));
            }
        } catch (IndexOutOfBoundsException e) {

            System.out.println(e.getMessage());
            throw new CustomApiException("해당 닉네임을 가진 친구가 없습니다.");

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new CustomApiException("친구 검색 실패");
        }
        return SearchedFriendList;
    }
}
