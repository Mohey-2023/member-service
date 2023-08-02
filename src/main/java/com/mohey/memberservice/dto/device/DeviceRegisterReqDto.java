package com.mohey.memberservice.dto.device;import com.mohey.memberservice.domain.Member;import com.mohey.memberservice.domain.MemberDevice;import lombok.Getter;import lombok.Setter;@Setter@Getterpublic class DeviceRegisterReqDto {	private String memberUuid;	private String deviceUuid;	private String deviceToken;	public MemberDevice toMemberDeviceEntity(Member member) {		return MemberDevice.builder()			.deviceUuid(this.deviceUuid)			.deviceToken(this.deviceToken)			.memberId(member)			.build();	}}