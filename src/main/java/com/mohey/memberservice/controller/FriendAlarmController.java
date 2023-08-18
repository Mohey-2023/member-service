package com.mohey.memberservice.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mohey.memberservice.domain.MemberDeviceNotiStatus;
import com.mohey.memberservice.dto.ResponseDto;
import com.mohey.memberservice.dto.memberalarm.FriendReqAlarmReqDto;
import com.mohey.memberservice.dto.memberalarm.FriendReqAlarmRespDto;
import com.mohey.memberservice.dto.memberalarm.FriendRespAlarmRespDto;
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
		String Uuid = UUID.randomUUID().toString();
		FriendReqAlarmRespDto friendReqAlarmRespDto = friendAlarmService.sendAlarm(friendReqAlarmReqDto, Uuid);

		//알람 보내기
		return new ResponseEntity<>(new ResponseDto<>(1, "알람 보내기 성공", friendReqAlarmRespDto), HttpStatus.CREATED);
	}

	@PostMapping("/change")
	public ResponseEntity<?> stopAlarm(@RequestBody @Valid String deviceId,
		BindingResult bindingResult) {

		Boolean memberDeviceNotiStatus = friendAlarmService.stopAlarm(deviceId);

		//알람 보내기
		return new ResponseEntity<>(new ResponseDto<>(1, "알림변경 성공", memberDeviceNotiStatus), HttpStatus.CREATED);
	}

	@GetMapping(value = "/{memberUuid}/getFriendReqList", produces = "application/json;charset=UTF-8")
	public ResponseEntity<?> getFriendReqList(@PathVariable String memberUuid) {
		List<FriendRespAlarmRespDto> friendRespAlarmRespDtoList = new ArrayList<>();
		friendRespAlarmRespDtoList = friendAlarmService.getFriendReqList(memberUuid);
		return new ResponseEntity<>(new ResponseDto<>(1, "성공", friendRespAlarmRespDtoList), HttpStatus.OK);
	}

}
