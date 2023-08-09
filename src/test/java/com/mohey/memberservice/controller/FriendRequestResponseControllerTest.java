package com.mohey.memberservice.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mohey.memberservice.domain.AlarmStatusEnum;
import com.mohey.memberservice.domain.FriendRelation;
import com.mohey.memberservice.domain.FriendRequest;
import com.mohey.memberservice.domain.FriendRequestStatus;
import com.mohey.memberservice.domain.Member;
import com.mohey.memberservice.dto.memberFriend.FriendRegisterReqDto;
import com.mohey.memberservice.dto.memberFriend.FriendStarReqDto;
import com.mohey.memberservice.dto.memberalarm.AlarmRequest;
import com.mohey.memberservice.dto.memberalarm.FriendReqAlarmReqDto;
import com.mohey.memberservice.dto.memberjoin.JoinReqDto;
import com.mohey.memberservice.repository.FriendRelationRepository;
import com.mohey.memberservice.repository.FriendRequestRepository;
import com.mohey.memberservice.repository.MemberInfoRepository;
import com.mohey.memberservice.repository.MemberRepository;
import com.mohey.memberservice.service.FriendAlarmService;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class FriendRequestResponseControllerTest {
	@Autowired
	private MockMvc mvc;
	@Autowired
	private ObjectMapper om;
	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private MemberInfoRepository memberInfoRepository;
	@Autowired
	private FriendRelationRepository friendRelationRepository;
	@Autowired
	private FriendAlarmService friendAlarmService;
	@Autowired
	private FriendRequestRepository friendRequestRepository;

	static final String memberUuid = UUID.randomUUID().toString();
	static final String friendUuid = UUID.randomUUID().toString();

	static final String alarmUuid = UUID.randomUUID().toString();

	@BeforeEach
	public void setUp() {
		//공통 given
		JoinReqDto joinReqDto = new JoinReqDto();
		joinReqDto.setBirthDate("1994-12-01");
		joinReqDto.setMemberUuid(memberUuid);
		joinReqDto.setGender("MAN");
		Member member = memberRepository.save(joinReqDto.toMemberEntity());

		joinReqDto.setBirthDate("1999-12-01");
		joinReqDto.setMemberUuid(friendUuid);
		joinReqDto.setGender("WOMAN");
		Member member2 = memberRepository.save(joinReqDto.toMemberEntity());

		joinReqDto.setBirthDate("1999-12-06");
		joinReqDto.setMemberUuid("1234");
		joinReqDto.setGender("WOMAN");
		Member member3 = memberRepository.save(joinReqDto.toMemberEntity());

		FriendRelation friendRelation =
			FriendRelation.builder()
				.memberId(member)
				.friendId(member3)
				.favoriteStatus(false)
				.friendStatus(true)
				.build();
		friendRelationRepository.save(friendRelation);
		FriendRelation friendRelationReverse =
			FriendRelation.builder()
				.memberId(member3)
				.friendId(member)
				.favoriteStatus(false)
				.friendStatus(true)
				.build();
		friendRelationRepository.save(friendRelationReverse);

		FriendRelation friendRelation2 =
			FriendRelation.builder()
				.memberId(member2)
				.friendId(member3)
				.favoriteStatus(false)
				.friendStatus(false)
				.build();
		friendRelationRepository.save(friendRelation2);
		FriendRelation friendRelation2Reverse =
			FriendRelation.builder()
				.memberId(member3)
				.friendId(member2)
				.favoriteStatus(false)
				.friendStatus(false)
				.build();
		friendRelationRepository.save(friendRelation2Reverse);

		//친구 요청 정보 사항 저장 수락 거절시 status 변경임
		FriendReqAlarmReqDto friendReqAlarmReqDto = new FriendReqAlarmReqDto();
		FriendRequest friendRequest =
			friendReqAlarmReqDto.toFriendRequestEntity(member, member2, null, alarmUuid);
		FriendRequestStatus friendRequestStatus = FriendRequestStatus.builder()
			.status(AlarmStatusEnum.valueOf("WAIT"))
			.build();
		friendRequestStatus.setFriendRequest(friendRequest); // FriendRequest와 연결
		friendRequest.setFriendRequestStatus(friendRequestStatus); // 양방향 연관관계 설정
		friendRequestRepository.save(friendRequest);

		FriendRequest friendRequest2 =
			friendReqAlarmReqDto.toFriendRequestEntity(member2, member3, null, "12345");
		FriendRequestStatus friendRequestStatus2 = FriendRequestStatus.builder()
			.status(AlarmStatusEnum.valueOf("WAIT"))
			.build();
		friendRequestStatus2.setFriendRequest(friendRequest2); // FriendRequest와 연결
		friendRequest2.setFriendRequestStatus(friendRequestStatus2); // 양방향 연관관계 설정
		friendRequestRepository.save(friendRequest2);

	}

	@Transactional
	@Test
	public void friendRegister_success_test() throws Exception {
		// given
		FriendRegisterReqDto friendRegisterReqDto = new FriendRegisterReqDto();
		friendRegisterReqDto.setMyUuid(memberUuid);
		friendRegisterReqDto.setFriendUuid(friendUuid);
		friendRegisterReqDto.setAlarmUuid(alarmUuid);
		String requestBody = om.writeValueAsString(friendRegisterReqDto);
		System.out.println("테스트 : " + requestBody);

		// when
		ResultActions resultActions = mvc
			.perform(post("/members/friendAct/register").content(requestBody).contentType(MediaType.APPLICATION_JSON));
		String responseBody =
			resultActions.andReturn().getResponse().getContentAsString();
		System.out.println("테스트 : " + responseBody);

		// then
		resultActions.andExpect(status().isCreated());
	}

	//삭제했던 친구 다시 친구할 경우
	@Transactional
	@Test
	public void friendRegister_success_test2() throws Exception {
		// given
		FriendRegisterReqDto friendRegisterReqDto = new FriendRegisterReqDto();
		friendRegisterReqDto.setMyUuid(friendUuid);
		friendRegisterReqDto.setFriendUuid("1234");
		friendRegisterReqDto.setAlarmUuid("12345");
		String requestBody = om.writeValueAsString(friendRegisterReqDto);
		System.out.println("테스트 : " + requestBody);

		// when
		ResultActions resultActions = mvc
			.perform(post("/members/friendAct/register").content(requestBody).contentType(MediaType.APPLICATION_JSON));
		String responseBody =
			resultActions.andReturn().getResponse().getContentAsString();
		System.out.println("테스트 : " + responseBody);

		// then
		resultActions.andExpect(status().isCreated());
	}

	//이미 있는 친구이고 상태도 친구인데 등록할 경우
	@Transactional
	@Test
	public void friendRegister_fail_test() throws Exception {
		// given
		FriendRegisterReqDto friendRegisterReqDto = new FriendRegisterReqDto();
		friendRegisterReqDto.setMyUuid(memberUuid);
		friendRegisterReqDto.setFriendUuid("1234");
		friendRegisterReqDto.setAlarmUuid("12345");
		String requestBody = om.writeValueAsString(friendRegisterReqDto);
		System.out.println("테스트 : " + requestBody);

		// when
		ResultActions resultActions = mvc
			.perform(post("/members/friendAct/register").content(requestBody).contentType(MediaType.APPLICATION_JSON));
		String responseBody =
			resultActions.andReturn().getResponse().getContentAsString();
		System.out.println("테스트 : " + responseBody);

		// then
		resultActions.andExpect(status().isBadRequest());
	}

	@Transactional
	@Test
	public void star_success_test() throws Exception {
		// given
		FriendStarReqDto friendStarReqDto = new FriendStarReqDto();
		friendStarReqDto.setMyUuid(memberUuid);
		friendStarReqDto.setFriendUuid("1234");
		String requestBody = om.writeValueAsString(friendStarReqDto);
		System.out.println("테스트 : " + requestBody);

		// when
		ResultActions resultActions = mvc
			.perform(post("/members/friendAct/star").content(requestBody).contentType(MediaType.APPLICATION_JSON));
		String responseBody =
			resultActions.andReturn().getResponse().getContentAsString();
		System.out.println("테스트 : " + responseBody);

		// then
		resultActions.andExpect(status().isCreated());
	}

	@Transactional
	@Test
	public void star_fail_test() throws Exception {
		// given
		FriendStarReqDto friendStarReqDto = new FriendStarReqDto();
		friendStarReqDto.setMyUuid(memberUuid);
		friendStarReqDto.setFriendUuid(friendUuid);
		String requestBody = om.writeValueAsString(friendStarReqDto);
		System.out.println("테스트 : " + requestBody);

		// when
		ResultActions resultActions = mvc
			.perform(post("/members/friendAct/star").content(requestBody).contentType(MediaType.APPLICATION_JSON));
		String responseBody =
			resultActions.andReturn().getResponse().getContentAsString();
		System.out.println("테스트 : " + responseBody);

		// then
		resultActions.andExpect(status().isBadRequest());
	}

	@Transactional
	@Test
	public void friendDelete_success_test() throws Exception {
		// given
		FriendRegisterReqDto friendRegisterReqDto = new FriendRegisterReqDto();
		friendRegisterReqDto.setMyUuid(memberUuid);
		friendRegisterReqDto.setFriendUuid("1234");
		String requestBody = om.writeValueAsString(friendRegisterReqDto);
		System.out.println("테스트 : " + requestBody);

		// when
		ResultActions resultActions = mvc
			.perform(post("/members/friendAct/delete").content(requestBody).contentType(MediaType.APPLICATION_JSON));
		String responseBody =
			resultActions.andReturn().getResponse().getContentAsString();
		System.out.println("테스트 : " + responseBody);

		// then
		resultActions.andExpect(status().isOk());
	}

	@Transactional
	@Test
	public void friendDelete_fail_test() throws Exception {
		// given
		FriendRegisterReqDto friendRegisterReqDto = new FriendRegisterReqDto();
		friendRegisterReqDto.setMyUuid(friendUuid);
		friendRegisterReqDto.setFriendUuid("1234");
		String requestBody = om.writeValueAsString(friendRegisterReqDto);
		System.out.println("테스트 : " + requestBody);

		// when
		ResultActions resultActions = mvc
			.perform(post("/members/friendAct/delete").content(requestBody).contentType(MediaType.APPLICATION_JSON));
		String responseBody =
			resultActions.andReturn().getResponse().getContentAsString();
		System.out.println("테스트 : " + responseBody);

		// then
		resultActions.andExpect(status().isBadRequest());
	}

	@Transactional
	@Test
	public void friendReject_success_test() throws Exception {
		// given
		AlarmRequest alarmRequest = new AlarmRequest(alarmUuid);

		String requestBody = om.writeValueAsString(alarmRequest);
		System.out.println("테스트 : " + requestBody);

		// when
		ResultActions resultActions = mvc
			.perform(post("/members/friendAct/reject").content(requestBody).contentType(MediaType.APPLICATION_JSON));
		String responseBody =
			resultActions.andReturn().getResponse().getContentAsString();
		System.out.println("테스트 : " + responseBody);

		// then
		resultActions.andExpect(status().isOk());
	}

	@Transactional
	@Test
	public void friendReject_fail_test() throws Exception {
		// given
		String requestBody = "12332234";
		System.out.println("테스트 : " + requestBody);

		// when
		ResultActions resultActions = mvc
			.perform(post("/members/friendAct/reject").content(requestBody).contentType(MediaType.APPLICATION_JSON));
		String responseBody =
			resultActions.andReturn().getResponse().getContentAsString();
		System.out.println("테스트 : " + responseBody);

		// then
		resultActions.andExpect(status().isBadRequest());
	}
}