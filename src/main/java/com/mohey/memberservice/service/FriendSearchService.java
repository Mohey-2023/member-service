package com.mohey.memberservice.service;

import java.util.List;

import com.mohey.memberservice.dto.memberFriend.FriendInFriendListSearchRespDto;
import com.mohey.memberservice.dto.memberFriend.FriendListSearchRespDto;

public interface FriendSearchService {

	List<FriendListSearchRespDto> searchFriendList(String memberUuId);

	List<FriendInFriendListSearchRespDto> SearchFriendInFriendList(String nickname, String memberUuId);

	List<String> searchFeinFriendList(String memberUuId);
}


