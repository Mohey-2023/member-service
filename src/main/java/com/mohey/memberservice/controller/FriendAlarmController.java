package com.mohey.memberservice.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mohey.memberservice.dto.ResponseDto;
import com.mohey.memberservice.dto.memberalram.FriendReqAlarmReqDto;
import com.mohey.memberservice.dto.memberalram.FriendReqAlarmRespDto;
import com.mohey.memberservice.service.FriendAlarmService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/members/friendAlarm")
@RestController
public class FriendAlarmController {
	private final FriendAlarmService friendAlarmService;

	@PostMapping("/send")
	public ResponseEntity<?> sendAlarm(@RequestBody @Valid FriendReqAlarmReqDto friendReqAlarmReqDto,
		BindingResult bindingResult) {
		FriendReqAlarmRespDto friendReqAlarmRespDto = friendAlarmService.sendAlarm(friendReqAlarmReqDto);

		//알람 보내기
		return new ResponseEntity<>(new ResponseDto<>(1, "알람 보내기 성공", friendReqAlarmRespDto), HttpStatus.CREATED);
	}

}
