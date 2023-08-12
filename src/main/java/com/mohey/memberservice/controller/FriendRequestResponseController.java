package com.mohey.memberservice.controller;

import javax.validation.Valid;

import com.mohey.memberservice.dto.memberFriend.*;
import com.mohey.memberservice.dto.memberalarm.FriendRespAlarmRespDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.mohey.memberservice.dto.ResponseDto;
import com.mohey.memberservice.dto.memberalarm.AlarmRequest;
import com.mohey.memberservice.service.FriendRequestResponseService;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/members/friendAct")
@RestController
public class FriendRequestResponseController {
	private final FriendRequestResponseService friendRequestResponseService;

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody @Valid FriendRegisterReqDto friendRegisterReqDto,
		BindingResult bindingResult) {

		FriendRegisterRespDto friendRegisterRespDto = friendRequestResponseService.register(friendRegisterReqDto);
		//알람 보내기
		return new ResponseEntity<>(new ResponseDto<>(1, "친구등록 성공", friendRegisterRespDto), HttpStatus.CREATED);
	}

	@PostMapping("/star")
	public ResponseEntity<?> star(@RequestBody @Valid FriendStarReqDto friendStarReqDto, BindingResult bindingResult) {

		FriendStarRespDto friendStarRespDto = friendRequestResponseService.Star(friendStarReqDto);
		//알람 보내기
		return new ResponseEntity<>(new ResponseDto<>(1, "즐겨찾기 반영", friendStarRespDto), HttpStatus.CREATED);
	}

	@PostMapping("/delete")
	public ResponseEntity<?> delete(@RequestBody @Valid FriendDeleteReqDto friendDeleteReqDto,
		BindingResult bindingResult) {

		FriendDeleteRespDto friendDeleteRespDto = friendRequestResponseService.DeleteFriend(friendDeleteReqDto);
		//알람 보내기
		return new ResponseEntity<>(new ResponseDto<>(1, "친구삭제 성공", friendDeleteRespDto), HttpStatus.OK);
	}

	@PostMapping("/reject")
	public ResponseEntity<?> reject(@RequestBody @Valid AlarmRequest alarmRequest,
		BindingResult bindingResult) {
		System.out.println("alarmRequest" + alarmRequest);
		Boolean friendRequestStatus = friendRequestResponseService.reject(alarmRequest);

		return new ResponseEntity<>(new ResponseDto<>(1, "친구거절 성공", friendRequestStatus), HttpStatus.OK);
	}

}
