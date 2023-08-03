package com.mohey.memberservice.dto.memberalram;

import com.mohey.memberservice.domain.FriendRequest;
import com.mohey.memberservice.domain.FriendRequestStatus;
import com.mohey.memberservice.domain.Member;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Setter
@Getter
public class FriendReqAlarmReqDto {

	@NonNull
	private String myUuid;
	@NonNull
	private String friendUuid;

	public FriendRequest toFriendRequestEntity(Member my, Member friend, FriendRequestStatus friendRequestStatus) {
		return FriendRequest.builder()
			.memberId(my)
			.responseId(friend)
			.friendRequestStatus(friendRequestStatus)
			.build();
	}

}
