package com.mohey.memberservice.controller;

import com.mohey.memberservice.dto.ResponseDto;
import com.mohey.memberservice.dto.memberFriend.FriendListSearchRespDto;
import com.mohey.memberservice.service.FriendSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/members/friendSearch")
@RestController
public class FriendSearchController {

    private final FriendSearchService friendSearchService;

    //nickName 어케 걸지..
    @GetMapping("/members/{memberUuId}")
    //?search={nickName}
    public ResponseEntity<?> SearchFriendList(@PathVariable String memberUuId){
        List<FriendListSearchRespDto> friendListSearchRespDtos = friendSearchService.searchFriendList(memberUuId);

        return new ResponseEntity<>(new ResponseDto<>(1,"친구 목록 조회",friendListSearchRespDtos), HttpStatus.OK);

    }
}
