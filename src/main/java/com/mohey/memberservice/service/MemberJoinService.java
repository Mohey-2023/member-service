package com.mohey.memberservice.service;import com.mohey.memberservice.dto.memberjoin.JoinReqDto;import com.mohey.memberservice.dto.memberjoin.JoinRespDto;public interface MemberJoinService {	public JoinRespDto register(JoinReqDto joinReqDto);}