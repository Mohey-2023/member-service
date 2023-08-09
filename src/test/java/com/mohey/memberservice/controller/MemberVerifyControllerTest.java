package com.mohey.memberservice.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;
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
import com.mohey.memberservice.domain.Member;
import com.mohey.memberservice.dto.memberjoin.JoinReqDto;
import com.mohey.memberservice.dto.memberupdate.UpdateInfoReqDto;
import com.mohey.memberservice.repository.MemberInfoRepository;
import com.mohey.memberservice.repository.MemberRepository;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class MemberVerifyControllerTest {
	@Autowired
	private MockMvc mvc;
	@Autowired
	private ObjectMapper om;
	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private MemberInfoRepository memberInfoRepository;
	static final String memberUuid = UUID.randomUUID().toString();

	@BeforeEach
	public void setUp() {
		//공통 given
		JoinReqDto joinReqDto = new JoinReqDto();
		joinReqDto.setBirthDate("1994-12-01");
		joinReqDto.setMemberUuid(memberUuid);
		joinReqDto.setGender("MAN");
		Member member = memberRepository.save(joinReqDto.toMemberEntity());

		joinReqDto.setNickname("qqq");
		joinReqDto.setProfileUrl("aaaaa");
		joinReqDto.setSelfIntroduction("hahahahahaha");
		memberInfoRepository.save(joinReqDto.toMemberInfoEntity(member));

	}

	@Transactional
	@Test
	public void getInfo_success_test() throws Exception {
		// given

		// when
		ResultActions resultActions = mvc.perform(
			get("/members/verify/{memberUuid}/getInfo", memberUuid)
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
	public void getInfo_fail_test() throws Exception {
		// given

		// when
		ResultActions resultActions = mvc.perform(
			get("/members/verify/{memberUuid}/getInfo", "2532")
				.contentType(MediaType.APPLICATION_JSON)
		);
		String responseBody =
			resultActions.andReturn().getResponse().getContentAsString();
		System.out.println("테스트 : " + responseBody);
		// then
		resultActions.andExpect(status().isBadRequest());
	}

	@Transactional
	@Test
	public void infoUpdate_success_test() throws Exception {
		// given

		List<String> interests = new ArrayList<>();
		interests.add("운동");
		interests.add("기타");
		interests.add("학습");
		UpdateInfoReqDto updateInfoReqDto = new UpdateInfoReqDto();
		updateInfoReqDto.setSelfIntroduction("gdsgs");
		//	updateInfoReqDto.setInterests(interests);
		//updateReqDto.setNickname("바꿔바꿔");
		updateInfoReqDto.setMemberUuid(memberUuid);
		//updateReqDto.setProfile_url("www.어쩌고자짜거");

		String requestBody = om.writeValueAsString(updateInfoReqDto);
		System.out.println("테스트 : " + requestBody);

		// when
		ResultActions resultActions = mvc
			.perform(post("/members/verify/updateInfo").content(requestBody).contentType(MediaType.APPLICATION_JSON));
		String responseBody =
			resultActions.andReturn().getResponse().getContentAsString();
		System.out.println("테스트 : " + responseBody);

		// then
		resultActions.andExpect(status().isCreated());
	}

	@Transactional
	@Test
	public void infoUpdate_fail_test() throws Exception {
		// given
		List<String> interests = new ArrayList<>();
		interests.add("운동");
		interests.add("기타");
		interests.add("학습");
		UpdateInfoReqDto updateInfoReqDto = new UpdateInfoReqDto();
		updateInfoReqDto.setSelfIntroduction("gdsgs");
		updateInfoReqDto.setInterests(interests);
		//updateReqDto.setNickname("바꿔바꿔");
		//updateReqDto.setMemberUuid(memberUuid);
		//updateReqDto.setProfile_url("www.어쩌고자짜거");

		String requestBody = om.writeValueAsString(updateInfoReqDto);
		System.out.println("테스트 : " + requestBody);

		// when
		ResultActions resultActions = mvc
			.perform(post("/members/verify/updateInfo").content(requestBody).contentType(MediaType.APPLICATION_JSON));
		String responseBody =
			resultActions.andReturn().getResponse().getContentAsString();
		System.out.println("테스트 : " + responseBody);

		// then
		resultActions.andExpect(status().isBadRequest());
	}
}