package com.mohey.memberservice.service;

import com.mohey.memberservice.dto.memberFriend.FriendRegisterReqDto;
import com.mohey.memberservice.dto.memberFriend.FriendRegisterRespDto;
import com.mohey.memberservice.dto.memberFriend.FriendStarReqDto;
import com.mohey.memberservice.dto.memberFriend.FriendStarRespDto;

public interface FriendRequestResponseService {
    public FriendRegisterRespDto register(FriendRegisterReqDto friendRegisterReqDto);

    public FriendStarRespDto Star(FriendStarReqDto FriendStarReqDto);
}
