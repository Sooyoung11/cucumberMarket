package com.sohwakmo.cucumbermarket.dto;

import com.sohwakmo.cucumbermarket.domain.Member;
import lombok.*;

@AllArgsConstructor
@Builder
@Data
public class ProfileImageReadDto {
    private Integer memberNo;
    private String userImgUrl;
    private String userImgName;
    public static ProfileImageReadDto fromEntity(Member entity){
        return ProfileImageReadDto.builder()
                .memberNo(entity.getMemberNo())
                .userImgUrl(entity.getUserImgUrl())
                .userImgName(entity.getUserImgName())
                .build();
    }
}
