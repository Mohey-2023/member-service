package com.mohey.memberservice.dto.memberalram;import com.mohey.memberservice.domain.FriendRequest;import lombok.Getter;import lombok.NoArgsConstructor;@NoArgsConstructor@Getterpublic class FriendReqAlarmRespDto {	private FriendRequest friendRequest;	public FriendReqAlarmRespDto(FriendRequest friendRequest) {		this.friendRequest = friendRequest;	}}