package com.mohey.memberservice.controller;

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

import com.mohey.memberservice.dto.ResponseDto;
import com.mohey.memberservice.dto.memberInfo.GetInfoRespDto;
import com.mohey.memberservice.dto.memberupdate.UpdateInfoReqDto;
import com.mohey.memberservice.dto.memberupdate.UpdateInfoRespDto;
import com.mohey.memberservice.service.MemberInfoService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/members/verify")
@RestController
public class MemberVerifyController {

	private final MemberInfoService memberInfoService;

	@GetMapping(value = "/{memberUuid}/getInfo", produces = "application/json;charset=UTF-8")
	public ResponseEntity<?> getInfo(@PathVariable String memberUuid) {

		GetInfoRespDto getInfoRespDto = memberInfoService.getInfo(memberUuid);
		return new ResponseEntity<>(new ResponseDto<>(1, "성공", getInfoRespDto), HttpStatus.OK);
	}

	@PostMapping("/updateInfo")
	public ResponseEntity<?> updateInfo(@RequestBody @Valid UpdateInfoReqDto updateInfoReqDto,
		BindingResult bindingResult) {
		UpdateInfoRespDto updateInfoRespDto = memberInfoService.updateInfo(updateInfoReqDto);

		return new ResponseEntity<>(new ResponseDto<>(1, "회원수정 성공", updateInfoRespDto), HttpStatus.CREATED);
	}

}
