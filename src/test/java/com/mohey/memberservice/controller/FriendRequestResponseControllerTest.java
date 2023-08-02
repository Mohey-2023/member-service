package com.mohey.memberservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mohey.memberservice.domain.FriendRelation;
import com.mohey.memberservice.domain.Member;
import com.mohey.memberservice.dto.memberFriend.FriendRegisterReqDto;
import com.mohey.memberservice.dto.memberFriend.FriendStarReqDto;
import com.mohey.memberservice.dto.memberjoin.JoinReqDto;
import com.mohey.memberservice.repository.FriendRelationRepository;
import com.mohey.memberservice.repository.MemberInfoRepository;
import com.mohey.memberservice.repository.MemberInterestRepository;
import com.mohey.memberservice.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    static final String memberUuid = UUID.randomUUID().toString();
    static final String friendUuid = UUID.randomUUID().toString();
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
                        .build();
        friendRelationRepository.save(friendRelation);

        joinReqDto.setNickname("qqq");
        joinReqDto.setProfile_url("aaaaa");
        joinReqDto.setSelfIntroduction("hahahahahaha");
        memberInfoRepository.save(joinReqDto.toMemberInfoEntity(member));


    }
    @Transactional
    @Test
    public void friendRegister_success_test() throws Exception {
        // given
        FriendRegisterReqDto friendRegisterReqDto = new FriendRegisterReqDto();
        friendRegisterReqDto.setMyUuid(memberUuid);
        friendRegisterReqDto.setFriendUuid(friendUuid);
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

    @Transactional
    @Test
    public void friendRegister_fail_test() throws Exception {
        // given
        FriendRegisterReqDto friendRegisterReqDto = new FriendRegisterReqDto();
        friendRegisterReqDto.setMyUuid(memberUuid);
        friendRegisterReqDto.setFriendUuid("3333");
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

}