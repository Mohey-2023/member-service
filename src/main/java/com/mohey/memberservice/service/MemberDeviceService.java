package com.mohey.memberservice.service;import com.mohey.memberservice.dto.memberdevice.DeviceRegisterReqDto;import com.mohey.memberservice.dto.memberdevice.DeviceRegisterRespDto;import java.util.List;public interface MemberDeviceService {	public DeviceRegisterRespDto registerDevice(DeviceRegisterReqDto deviceRegisterReqDto);	public List<String> getToken(String uuid);}