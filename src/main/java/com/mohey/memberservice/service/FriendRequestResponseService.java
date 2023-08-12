package com.mohey.memberservice.service;

import com.mohey.memberservice.dto.memberFriend.*;
import com.mohey.memberservice.dto.memberalarm.AlarmRequest;

public interface FriendRequestResponseService {
	public FriendRegisterRespDto register(FriendRegisterReqDto friendRegisterReqDto);

	public FriendStarRespDto Star(FriendStarReqDto FriendStarReqDto);

	public FriendDeleteRespDto DeleteFriend(FriendDeleteReqDto friendDeleteReqDto);

	public Boolean reject(AlarmRequest alarmUuid);

}
