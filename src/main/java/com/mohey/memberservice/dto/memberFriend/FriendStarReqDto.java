package com.mohey.memberservice.dto.memberFriend;

import com.mohey.memberservice.domain.FriendRelation;
import com.mohey.memberservice.domain.FriendRelationFavoriteStatus;
import com.mohey.memberservice.domain.FriendRelationStatus;
import com.mohey.memberservice.domain.Member;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Setter
@Getter
public class FriendStarReqDto {

    @NonNull
    private String myUuid;
    @NonNull
    private String friendUuid;


    public FriendRelationFavoriteStatus toFriendRelationFavoriteStatusEntity(FriendRelation friendRelation) {
        return FriendRelationFavoriteStatus.builder()
                .favoriteStatus(friendRelation.getFavoriteStatus())
                .friendRelationId(friendRelation)
                .favoriteStatus(friendRelation.getFavoriteStatus())
                .build();
    }

}
