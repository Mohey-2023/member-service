package com.mohey.memberservice.service;

import com.mohey.memberservice.domain.Member;
import com.mohey.memberservice.dto.memberSearch.MemberSearchRespDto;
import com.mohey.memberservice.ex.CustomApiException;
import com.mohey.memberservice.repository.MemberRepository;
import com.mohey.memberservice.repository.MemberSearchRepository;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MemberSearchServiceImpl implements MemberSearchService{

    private final MemberSearchRepository memberSearchRepository;
    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public List<MemberSearchRespDto> Search(String nickname, String memberUuId) {

        List<MemberSearchRespDto> memberList;
        try {
            //내 uuid도 ,,보내야될거같은데
            //닉네임으로 멤버들 찾아오기
            Member me = memberRepository.findByMemberUuid(memberUuId);
            //System.out.println(nickname);
            List<Member> memberIdList = memberSearchRepository.findAllByNickname(nickname);
            //System.out.println("getMy");
            //System.out.println(my);

            //1. 찾은 멤버들로 필요한 정보 불러오기
            memberList = memberSearchRepository.getMemberList(memberIdList.get(0).getId(), me);
            //System.out.println("mmList1");

            for (int i = 0; i < memberIdList.size() - 1; i++) {
                memberList.addAll(memberSearchRepository.getMemberList(memberIdList.get(i).getId(),me));
                //System.out.println("mmList2");
            }
            //System.out.println(memberList.toString());
            //친구가 아닌 사용자면 FriendStatus가 null로 떠버린다...이거 괜찮은가?


        } catch (IndexOutOfBoundsException e) {
            throw new CustomApiException("목록에 표시할 사용자가 없습니다.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new CustomApiException("사용자목록 가져오기 실패");
        }
        return memberList;
    }

    }

