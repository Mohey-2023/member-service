package com.mohey.memberservice.service;

import com.mohey.memberservice.dto.memberFriend.FriendInFriendListSearchRespDto;
import com.mohey.memberservice.dto.memberFriend.FriendListSearchRespDto;

import java.util.List;

public interface FriendSearchService {

    List<FriendListSearchRespDto> searchFriendList(String memberUuId);

    List<FriendInFriendListSearchRespDto> SearchFriendInFriendList(String nickname, String memberUuId);
}


