package com.mohey.memberservice.dto.memberSearch;

import com.mohey.memberservice.domain.GenderEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberSearchRespDto {

    private String nickname;

    private GenderEnum gender;

    private String birthDate;

    private String profileUrl;

    private Boolean friendStatus;

    public MemberSearchRespDto(String nickname, GenderEnum gender, String birthDate, String profileUrl, Boolean friendStatus) {
        this.nickname = nickname;
        this.gender = gender;
        this.birthDate = birthDate;
        this.profileUrl = profileUrl;
        this.friendStatus = friendStatus;
    }

    @Override
    public String toString() {
        return "MemberSearchRespDto{" +
                "nickname='" + nickname + '\'' +
                ", gender=" + gender +
                ", birthDate='" + birthDate + '\'' +
                ", profileUrl='" + profileUrl + '\'' +
                ", freindStatus=" + friendStatus+
                '}';
    }
}