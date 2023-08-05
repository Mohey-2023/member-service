package com.mohey.memberservice.dto.memberFriend;

import com.mohey.memberservice.domain.GenderEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FriendListSearchRespDto {

    private String nickname;

    private GenderEnum gender;

    private String birthDate;

    private String profileUrl;

    private Boolean favoriteStatus;

    public FriendListSearchRespDto(String nickname, GenderEnum gender, String birthDate, String profileUrl, Boolean favoriteStatus) {
        this.nickname = nickname;
        this.gender = gender;
        this.birthDate = birthDate;
        this.profileUrl = profileUrl;
        this.favoriteStatus = favoriteStatus;
    }

    @Override
    public String toString() {
        return "FriendListSearchRespDto{" +
                "nickname='" + nickname + '\'' +
                ", gender=" + gender +
                ", birthDate='" + birthDate + '\'' +
                ", profileUrl='" + profileUrl + '\'' +
                ", favoriteStatus=" + favoriteStatus +
                '}';
    }
}
