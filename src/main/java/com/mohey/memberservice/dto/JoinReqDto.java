package com.mohey.memberservice.dto;import java.time.LocalDateTime;import javax.validation.constraints.NotEmpty;import javax.validation.constraints.Pattern;import javax.validation.constraints.Size;import com.mohey.memberservice.domain.GenderEnum;import com.mohey.memberservice.domain.Member;import com.mohey.memberservice.domain.MemberDevice;import com.mohey.memberservice.domain.MemberDeviceAliveStatus;import com.mohey.memberservice.domain.MemberDeviceNotiStatus;import com.mohey.memberservice.domain.MemberInfo;import com.mohey.memberservice.domain.MemberProfileImage;import lombok.Getter;@Getterpublic class JoinReqDto {	@NotEmpty // null이거나, 공백일 수 없다.	private String memberUuid;	@NotEmpty	private String gender;	@NotEmpty	private LocalDateTime birthDate;	@NotEmpty	private String deviceToken;	@NotEmpty	private String deviceUuid;	@NotEmpty	@Pattern(regexp = "^[a-zA-Z가-힣]{1,10}$", message = "한글/영문 1~10자 이내로 작성해주세요")	private String nickname;	@NotEmpty	@Size(max = 100)	private String selfIntroduction;	@NotEmpty	private String profile_url;	public Member toMemberEntity() {		return Member.builder()			.memberUuid(this.memberUuid)			.birthDate(this.birthDate)			.gender(GenderEnum.valueOf(gender))			.build();	}	public MemberInfo toMemberInfoEntity(Member member) {		return MemberInfo.builder()			.nickname(this.nickname)			.selfIntroduction(this.selfIntroduction)			.memberId(member)			.build();	}	public MemberProfileImage toMemberProfileEntity() {		return MemberProfileImage.builder()			.profileUrl(this.getProfile_url())			.build();	}	public MemberDevice toMemberDeviceEntity(Member member) {		return MemberDevice.builder()			.memberId(member)			.deviceToken(this.deviceToken)			.deviceUuid(this.deviceUuid)			.build();	}	public MemberDeviceAliveStatus toMemberDeviceAliveStatusEntity(MemberDevice memberDevice) {		return MemberDeviceAliveStatus.builder()			.memberDeviceId(memberDevice)			.build();	}	public MemberDeviceNotiStatus toMemberDeviceNotiStatusEntity(MemberDevice memberDevice) {		return MemberDeviceNotiStatus.builder()			.memberDeviceId(memberDevice)			.build();	}}