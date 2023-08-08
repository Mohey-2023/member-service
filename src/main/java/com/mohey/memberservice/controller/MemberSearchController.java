package com.mohey.memberservice.controller;

import com.mohey.memberservice.dto.ResponseDto;
import com.mohey.memberservice.dto.memberSearch.MemberSearchRespDto;
import com.mohey.memberservice.service.MemberSearchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class MemberSearchController {

    private final MemberSearchService memberSearchService;

    @GetMapping("/members/{memberUuId}/search/{friendNickname}")
    public ResponseEntity<?> Search(@PathVariable("friendNickname") String nickname, @PathVariable("memberUuId") String memberUuId){

        List<MemberSearchRespDto> memberSearchRespDtos = memberSearchService.Search(nickname, memberUuId);

        return new ResponseEntity<>(new ResponseDto<>(1,"사용자 찾기 성공", memberSearchRespDtos), HttpStatus.OK);

    }


}
