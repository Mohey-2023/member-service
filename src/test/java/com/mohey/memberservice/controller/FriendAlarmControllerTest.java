package com.mohey.memberservice.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.UUID;

import javax.persistence.EntityManager;

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
import com.mohey.memberservice.domain.FriendRequest;
import com.mohey.memberservice.domain.FriendRequestStatus;
import com.mohey.memberservice.domain.Member;
import com.mohey.memberservice.domain.MemberDevice;
import com.mohey.memberservice.dto.memberalarm.FriendReqAlarmReqDto;
import com.mohey.memberservice.dto.memberdevice.DeviceRegisterReqDto;
import com.mohey.memberservice.dto.memberjoin.JoinReqDto;
import com.mohey.memberservice.repository.FriendRelationRepository;
import com.mohey.memberservice.repository.FriendRequestRepository;
import com.mohey.memberservice.repository.MemberDeviceAliveStatusRepository;
import com.mohey.memberservice.repository.MemberDeviceNotiStatusRepository;
import com.mohey.memberservice.repository.MemberDeviceRepository;
import com.mohey.memberservice.repository.MemberInfoRepository;
import com.mohey.memberservice.repository.MemberRepository;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class FriendAlarmControllerTest {
	@Autowired
	private MockMvc mvc;
	@Autowired
	private ObjectMapper om;
	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private FriendRelationRepository friendRelationRepository;
	@Autowired
	private MemberDeviceRepository memberDeviceRepository;
	@Autowired
	private MemberDeviceAliveStatusRepository memberDeviceAliveStatusRepository;
	@Autowired
	private MemberDeviceNotiStatusRepository memberDeviceNotiStatusRepository;
	@Autowired
	private FriendRequestRepository friendRequestRepository;
	@Autowired
	private EntityManager em;
	@Autowired
	private MemberInfoRepository memberInfoRepository;
	static final String memberUuid = UUID.randomUUID().toString();
	static final String friendUuid = UUID.randomUUID().toString();
	static final String deviceUUid = UUID.randomUUID().toString();
	static final String alarmUuid = UUID.randomUUID().toString();

	@BeforeEach
	public void setUp() {
		//공통 given
		JoinReqDto joinReqDto = new JoinReqDto();
		joinReqDto.setBirthDate("1994-12-01");
		joinReqDto.setMemberUuid(memberUuid);
		joinReqDto.setGender("MAN");
		joinReqDto.setNickname("qqq");
		joinReqDto.setSelfIntroduction("hahahahahaha");
		Member member = memberRepository.save(joinReqDto.toMemberEntity());
		memberInfoRepository.save(joinReqDto.toMemberInfoEntity(member));

		joinReqDto.setBirthDate("1999-12-01");
		joinReqDto.setMemberUuid(friendUuid);
		joinReqDto.setGender("WOMAN");
		Member member2 = memberRepository.save(joinReqDto.toMemberEntity());

		joinReqDto.setBirthDate("1999-12-06");
		joinReqDto.setMemberUuid("1234");
		joinReqDto.setGender("WOMAN");
		joinReqDto.setNickname("qqwwee");
		joinReqDto.setSelfIntroduction("hahwrwhaha");
		Member member3 = memberRepository.save(joinReqDto.toMemberEntity());
		memberInfoRepository.save(joinReqDto.toMemberInfoEntity(member3));

		//디바이스 등록하기
		DeviceRegisterReqDto deviceRegisterReqDto = new DeviceRegisterReqDto();
		deviceRegisterReqDto.setDeviceToken("1234");
		deviceRegisterReqDto.setDeviceUuid(deviceUUid);
		deviceRegisterReqDto.setMemberUuid(memberUuid);
		Member member4 = memberRepository.findByMemberUuid(deviceRegisterReqDto.getMemberUuid());

		MemberDevice memberDevice = memberDeviceRepository.save(deviceRegisterReqDto.toMemberDeviceEntity(member4));
		memberDeviceNotiStatusRepository.save(deviceRegisterReqDto.toMemberDeviceNotiStatusEntity(memberDevice));
		memberDeviceAliveStatusRepository.save(deviceRegisterReqDto.toMemberDeviceAliveStatusEntity(memberDevice));

		//친구 요청 등록
		FriendReqAlarmReqDto friendReqAlarmReqDto = new FriendReqAlarmReqDto();
		FriendRequest friendRequest =
			friendReqAlarmReqDto.toFriendRequestEntity(member3, member, null, alarmUuid);
		FriendRequestStatus friendRequestStatus = FriendRequestStatus.builder()
			.status(AlarmStatusEnum.valueOf("WAIT"))
			.build();
		friendRequestStatus.setFriendRequest(friendRequest); // FriendRequest와 연결
		friendRequest.setFriendRequestStatus(friendRequestStatus); // 양방향 연관관계 설정
		friendRequestRepository.save(friendRequest);

		FriendRequest friendRequest2 =
			friendReqAlarmReqDto.toFriendRequestEntity(member2, member, null, "12345");
		FriendRequestStatus friendRequestStatus2 = FriendRequestStatus.builder()
			.status(AlarmStatusEnum.valueOf("WAIT"))
			.build();
		friendRequestStatus2.setFriendRequest(friendRequest2); // FriendRequest와 연결
		friendRequest2.setFriendRequestStatus(friendRequestStatus2); // 양방향 연관관계 설정
		friendRequestRepository.save(friendRequest2);

	}

	@Transactional
	@Test
	public void sendAlarm_success_test() throws Exception {
		// given
		FriendReqAlarmReqDto friendReqAlarmReqDto = new FriendReqAlarmReqDto();
		friendReqAlarmReqDto.setMyUuid(memberUuid);
		friendReqAlarmReqDto.setFriendUuid("1234");
		String requestBody = om.writeValueAsString(friendReqAlarmReqDto);
		System.out.println("테스트 : " + requestBody);

		// when
		ResultActions resultActions = mvc
			.perform(post("/members/friendAlarm/send").content(requestBody).contentType(MediaType.APPLICATION_JSON));
		String responseBody =
			resultActions.andReturn().getResponse().getContentAsString();
		System.out.println("테스트 : " + responseBody);

		// then
		resultActions.andExpect(status().isCreated());
	}

	@Transactional
	@Test
	public void changeAlarm_success_test() throws Exception {
		// given

		String requestBody = deviceUUid;
		System.out.println("테스트 : " + requestBody);

		// when
		ResultActions resultActions = mvc
			.perform(post("/members/friendAlarm/change").content(requestBody).contentType(MediaType.APPLICATION_JSON));
		String responseBody =
			resultActions.andReturn().getResponse().getContentAsString();
		System.out.println("테스트 : " + responseBody);

		// then
		resultActions.andExpect(status().isCreated());
	}

	@Transactional
	@Test
	public void getFriendReqList_success_test() throws Exception {
		// given
		// when
		ResultActions resultActions = mvc.perform(
			get("/members/friendAlarm/{memberUuid}/getFriendReqList", memberUuid)
				.contentType(MediaType.APPLICATION_JSON)
		);
		String responseBody =
			resultActions.andReturn().getResponse().getContentAsString();
		System.out.println("테스트 : " + responseBody);
		// then
		resultActions.andExpect(status().isOk());

	}

	@Transactional
	@Test
	public void getFriendReqList_fail_test() throws Exception {
		// given
		// when
		ResultActions resultActions = mvc.perform(
			get("/members/friendAlarm/{memberUuid}/getFriendReqList", friendUuid)
				.contentType(MediaType.APPLICATION_JSON)
		);
		String responseBody =
			resultActions.andReturn().getResponse().getContentAsString();
		System.out.println("테스트 : " + responseBody);
		// then
		resultActions.andExpect(status().isBadRequest());

	}

}

