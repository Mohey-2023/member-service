package com.mohey.memberservice.service;

import com.mohey.memberservice.domain.Member;
import com.mohey.memberservice.dto.memberFriend.FriendListSearchRespDto;
import com.mohey.memberservice.ex.CustomApiException;
import com.mohey.memberservice.repository.FriendSearchRepository;
import com.mohey.memberservice.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
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

        List<FriendListSearchRespDto> friendList;
        try {
            Member my = memberRepository.findByMemberUuid(memberUuId);
            //System.out.println("getMy");

            //친구인 사용자 멤버 객체로 쭉 불러오기
            List<Member> friendIdList = friendSearchRepository.findAllByMemberId(my.getId());
           // System.out.println("getFriendIdList");

            //System.out.println(friendIdList);

//            if (friendIdList.isEmpty()) {
//                throw new CustomApiException("목록에 표시할 친구가 없습니다.");
//            }

            //1. 그냥 친구 불러오기
            friendList = friendSearchRepository.getNotFavoriteFriendList(friendIdList.get(0).getId());
            for (int i = 0; i < friendIdList.size() - 1; i++) {
                friendList.addAll(friendSearchRepository.getNotFavoriteFriendList(friendIdList.get(i).getId()));
            }
            System.out.println(friendList.toString());
            //2. 친한 친구 불러오기 << 여기서 favorite_status = null이 추가된다 왜지?? 계속 나에 대한 정보를 불러왔어서 그런듯

            for (int i = 0; i < friendIdList.size() - 1; i++) {
                friendList.addAll(friendSearchRepository.getFavoriteFriendList(friendIdList.get(i).getId()));
            }

            //notFavoriteFriendList.addAll(favoriteFriendList) 이거는 리스트를 반환해주는 게아님 !! 함수


        } catch(IndexOutOfBoundsException e ){
            throw new CustomApiException("목록에 표시할 친구가 없습니다.");
        }

        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new CustomApiException("친구목록 가져오기 실패");
        }
        return friendList;
    }
}
