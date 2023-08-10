package com.mohey.memberservice.dto.memberSearch;

import com.mohey.memberservice.domain.AlarmStatusEnum;
import com.mohey.memberservice.domain.GenderEnum;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class MemberSearchRespDto {

    private String memberUuid;

    private String nickname;

    private GenderEnum gender;

    private String birthDate;

    private String profileUrl;

    private Boolean friendStatus;

    //친구요청상태
    private AlarmStatusEnum status;
}
