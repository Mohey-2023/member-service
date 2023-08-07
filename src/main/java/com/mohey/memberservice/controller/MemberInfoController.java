package com.mohey.memberservice.controller;import java.util.ArrayList;import java.util.List;import javax.validation.Valid;import org.springframework.http.HttpStatus;import org.springframework.http.ResponseEntity;import org.springframework.validation.BindingResult;import org.springframework.web.bind.annotation.GetMapping;import org.springframework.web.bind.annotation.PathVariable;import org.springframework.web.bind.annotation.PostMapping;import org.springframework.web.bind.annotation.RequestBody;import org.springframework.web.bind.annotation.RequestMapping;import org.springframework.web.bind.annotation.RestController;import com.mohey.memberservice.dto.ResponseDto;import com.mohey.memberservice.dto.memberInfo.GetInfoRespDto;import com.mohey.memberservice.dto.memberupdate.UpdateInfoReqDto;import com.mohey.memberservice.dto.memberupdate.UpdateInfoRespDto;import com.mohey.memberservice.service.MemberInfoService;import lombok.RequiredArgsConstructor;@RequiredArgsConstructor@RequestMapping("/members/info")@RestControllerpublic class MemberInfoController {	private final MemberInfoService memberInfoService;	@GetMapping("/{memberUuid}/getInfo")	public ResponseEntity<?> getInfo(@PathVariable String memberUuid) {		GetInfoRespDto getInfoRespDto = memberInfoService.getInfo(memberUuid);		return new ResponseEntity<>(new ResponseDto<>(1, "성공", getInfoRespDto), HttpStatus.OK);	}	@GetMapping("/{memberUuid}/getInterest")	public ResponseEntity<?> getInterest(@PathVariable String memberUuid) {		List<String> interestList = new ArrayList<>();		interestList = memberInfoService.getInterest(memberUuid);		return new ResponseEntity<>(new ResponseDto<>(1, "성공", interestList), HttpStatus.OK);	}	@GetMapping("/{memberUuid}/getUsername")	public ResponseEntity<?> getUsername(@PathVariable String memberUuid) {		String nickname = memberInfoService.getUsername(memberUuid);		return new ResponseEntity<>(new ResponseDto<>(1, "성공", nickname), HttpStatus.OK);	}	@GetMapping("/{memberUuid}/getProflieImage")	public ResponseEntity<?> getProflieImage(@PathVariable String memberUuid) {		String profileImage = memberInfoService.getProfileImage(memberUuid);		return new ResponseEntity<>(new ResponseDto<>(1, "성공", profileImage), HttpStatus.OK);	}}