package com.mohey.memberservice.service;

import com.mohey.memberservice.dto.memberSearch.MemberSearchRespDto;

import java.util.List;

public interface MemberSearchService {

    List<MemberSearchRespDto> Search(String nickname, String memberUuId);

    List<MemberSearchRespDto> yourFriendSearchByNickname(String nickname, String memberUuId, String friendUuId);

    List<MemberSearchRespDto> yourFriendSearch(String memberUuId, String friendUuId);
}

