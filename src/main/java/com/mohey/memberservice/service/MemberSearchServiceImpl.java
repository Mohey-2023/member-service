package com.mohey.memberservice.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.mohey.memberservice.domain.Member;
import com.mohey.memberservice.dto.memberSearch.MemberSearchRespDto;
import com.mohey.memberservice.ex.CustomApiException;
import com.mohey.memberservice.repository.MemberRepository;
import com.mohey.memberservice.repository.MemberSearchRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberSearchServiceImpl implements MemberSearchService {

	private final MemberSearchRepository memberSearchRepository;
	private final MemberRepository memberRepository;

	@Transactional
	@Override
	public List<MemberSearchRespDto> Search(String nickname, String memberUuId) {

		List<MemberSearchRespDto> memberList;
		try {

			//닉네임으로 멤버들 찾아오기
			Member me = memberRepository.findByMemberUuid(memberUuId);
			System.out.println(nickname);
			List<Member> memberIdList = memberSearchRepository.findAllByNickname(nickname + "%", me);
			System.out.println("getMy");
			System.out.println(me);

			//1. 찾은 멤버들로 필요한 정보 불러오기
			memberList = memberSearchRepository.findMemberList(memberIdList.get(0).getId(), me,me);
			System.out.println("mmList1");

			for (int i = 1; i < memberIdList.size(); i++) {
				memberList.addAll(memberSearchRepository.findMemberList(memberIdList.get(i).getId(), me,me));
				System.out.println("mmList2");
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

	@Override
	public List<MemberSearchRespDto> yourFriendSearchByNickname(String nickname, String memberUuId, String friendUuId) {
		List<MemberSearchRespDto> memberList;
		try {

			//닉네임으로 멤버들 찾아오기
			Member me = memberRepository.findByMemberUuid(memberUuId);
			System.out.println(nickname);
			List<Member> memberIdList = memberSearchRepository.findAllByMemberUuidNickname(friendUuId,nickname + "%");
			System.out.println("getOthers");
			System.out.println(me);

			//1. 찾은 멤버들로 필요한 정보 불러오기
			memberList = memberSearchRepository.findMemberList(memberIdList.get(0).getId(), me,me);
			System.out.println("mmList1");

			for (int i = 1; i < memberIdList.size(); i++) {
				memberList.addAll(memberSearchRepository.findMemberList(memberIdList.get(i).getId(), me,me));
				System.out.println("mmList2");
			}

		} catch (IndexOutOfBoundsException e) {
			throw new CustomApiException("목록에 표시할 사용자가 없습니다.");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new CustomApiException("사용자목록 가져오기 실패");
		}
		return memberList;
	}

	@Override
	public List<MemberSearchRespDto> yourFriendSearch(String memberUuId, String friendUuId) {
		List<MemberSearchRespDto> memberList;
		try {

			//닉네임으로 멤버들 찾아오기
			Member me = memberRepository.findByMemberUuid(memberUuId);

			List<Member> memberIdList = memberSearchRepository.findAllByMemberUuid(friendUuId);
			System.out.println("getOthers");
			System.out.println(me);

			//1. 찾은 멤버들로 필요한 정보 불러오기
			memberList = memberSearchRepository.findMemberList(memberIdList.get(0).getId(), me,me);
			System.out.println("mmList1");

			for (int i = 1; i < memberIdList.size(); i++) {
				memberList.addAll(memberSearchRepository.findMemberList(memberIdList.get(i).getId(), me,me));
				System.out.println("mmList2");
			}

		} catch (IndexOutOfBoundsException e) {
			throw new CustomApiException("목록에 표시할 사용자가 없습니다.");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new CustomApiException("사용자목록 가져오기 실패");
		}
		return memberList;
	}
}


