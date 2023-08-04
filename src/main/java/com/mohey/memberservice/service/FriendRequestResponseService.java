package com.mohey.memberservice.service;

import com.mohey.memberservice.domain.FriendRequestStatus;
import com.mohey.memberservice.dto.memberFriend.FriendDeleteReqDto;
import com.mohey.memberservice.dto.memberFriend.FriendDeleteRespDto;
import com.mohey.memberservice.dto.memberFriend.FriendRegisterReqDto;
import com.mohey.memberservice.dto.memberFriend.FriendRegisterRespDto;
import com.mohey.memberservice.dto.memberFriend.FriendStarReqDto;
import com.mohey.memberservice.dto.memberFriend.FriendStarRespDto;

public interface FriendRequestResponseService {
	public FriendRegisterRespDto register(FriendRegisterReqDto friendRegisterReqDto);

	public FriendStarRespDto Star(FriendStarReqDto FriendStarReqDto);

	public FriendDeleteRespDto DeleteFriend(FriendDeleteReqDto friendDeleteReqDto);

	public FriendRequestStatus reject(String alarmUuid);

}
