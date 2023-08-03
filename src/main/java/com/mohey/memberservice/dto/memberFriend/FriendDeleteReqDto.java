package com.mohey.memberservice.dto.memberFriend;

import com.mohey.memberservice.domain.FriendRelation;
import com.mohey.memberservice.domain.FriendRelationStatus;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Setter
@Getter
public class FriendDeleteReqDto {

	@NonNull
	private String myUuid;
	@NonNull
	private String friendUuid;

	public FriendRelationStatus toFriendRelationStatusEntity(FriendRelation friendRelation) {
		return FriendRelationStatus.builder()
			.friendRelationId(friendRelation)
			.friendStatus(false)
			.build();
	}
}
