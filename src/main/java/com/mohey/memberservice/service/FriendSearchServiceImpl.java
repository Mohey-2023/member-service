package com.mohey.memberservice.service;

import com.mohey.memberservice.domain.Member;
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

    //친구목록 조회 테스트 필요....
    @Override
    public List<FriendListSearchRespDto> searchFriendList(String memberUuId) {

        try{
            Member my = memberRepository.findByMemberUuid(memberUuId);

            //1. 그냥 친구 불러오기
            List<FriendListSearchRespDto> friendList = friendSearchRepository.getNotFavoriteFriendList(my.getId());
            //2. 친한 친구 불러오기
            friendList.addAll(friendSearchRepository.getFavoriteFriendList(my.getId()));

            //notFavoriteFriendList.addAll(favoriteFriendList) 이거는 리스트를 반환해주는 게아님 !! 함수

            if (!friendList.isEmpty()){
                return friendList;
            } else {
                throw new CustomApiException("목록에 표시할 친구가 없습니다.");

            }

        } catch(DataIntegrityViolationException e) {
            throw new CustomApiException("친구목록 가져오기 실패");
        }
    }
}
