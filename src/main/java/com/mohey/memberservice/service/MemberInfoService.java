package com.mohey.memberservice.service;import java.util.List;import com.mohey.memberservice.dto.memberInfo.GetInfoRespDto;import com.mohey.memberservice.dto.memberInfo.GetProfileAndStatusRespDto;import com.mohey.memberservice.dto.memberupdate.UpdateInfoReqDto;import com.mohey.memberservice.dto.memberupdate.UpdateInfoRespDto;public interface MemberInfoService {	public GetInfoRespDto getInfo(String uuid);	public List<String> getInterest(String uuid);	public UpdateInfoRespDto updateInfo(UpdateInfoReqDto updateInfoReqDto);	public String getUsername(String uuid);	String getProfileImage(String memberUuid);	GetProfileAndStatusRespDto getProfileAndStatus(String memberUuid);}