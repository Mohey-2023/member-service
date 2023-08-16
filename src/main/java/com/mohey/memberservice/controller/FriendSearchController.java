package com.mohey.memberservice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mohey.memberservice.dto.ResponseDto;
import com.mohey.memberservice.dto.memberFriend.FriendInFriendListSearchRespDto;
import com.mohey.memberservice.dto.memberFriend.FriendListSearchRespDto;
import com.mohey.memberservice.service.FriendSearchService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/members/friendSearch")
@RestController
public class FriendSearchController {

	private final FriendSearchService friendSearchService;

	//nickName 어케 걸지..
	@GetMapping(value = "/{memberUuId}", produces = "application/json;charset=UTF-8")
	//?search={nickName}
	public ResponseEntity<?> SearchFriendList(@PathVariable String memberUuId) {
		List<FriendListSearchRespDto> friendListSearchRespDtos = friendSearchService.searchFriendList(memberUuId);

		return new ResponseEntity<>(new ResponseDto<>(1, "친구 목록 조회", friendListSearchRespDtos), HttpStatus.OK);

	}

	@GetMapping(value = "/{memberUuId}/{friendNickname}", produces = "application/json;charset=UTF-8")
	public ResponseEntity<?> SearchFriendInFriendList(@PathVariable("memberUuId") String memberUuId,
		@PathVariable("friendNickname") String nickname) {
		List<FriendInFriendListSearchRespDto> friendInFriendListSearchRespDtos = friendSearchService.SearchFriendInFriendList(
			nickname, memberUuId);

		return new ResponseEntity<>(new ResponseDto<>(1, "친구 검색", friendInFriendListSearchRespDtos), HttpStatus.OK);

	}

	@GetMapping(value = "/fein/{memberUuId}", produces = "application/json;charset=UTF-8")
	//?search={nickName}
	public ResponseEntity<?> FeinSearchFriendList(@PathVariable String memberUuId) {
		List<String> friendUuid = friendSearchService.searchFeinFriendList(memberUuId);

		return new ResponseEntity<>(new ResponseDto<>(1, "친구 목록 조회", friendUuid), HttpStatus.OK);

	}
}
