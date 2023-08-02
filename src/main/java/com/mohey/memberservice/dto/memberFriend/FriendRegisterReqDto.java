package com.mohey.memberservice.dto.memberFriend;

import com.mohey.memberservice.domain.FriendRelation;
import com.mohey.memberservice.domain.FriendRelationStatus;
import com.mohey.memberservice.domain.Member;
import com.mohey.memberservice.domain.MemberInfo;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Setter
@Getter
public class FriendRegisterReqDto {

    @NonNull
    private String myUuid;
    @NonNull
    private String friendUuid;

    public FriendRelation toFriendRelationEntity(Member my , Member friend) {
        return FriendRelation.builder()
                .memberId(my)
                .friendId(friend)
                .favoriteStatus(false)
                .friendStatus(true)
                .build();
    }
    public FriendRelationStatus toFriendRelationStatusEntity(FriendRelation friendRelation) {
        return FriendRelationStatus.builder()
                .friendRelationId(friendRelation)
                .friendStatus(true)
                .build();
    }
}
