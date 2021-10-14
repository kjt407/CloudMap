package com.jongtk.cloudmap.dto;

import lombok.*;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MyInfoDTO {

    boolean fromSocial;

    boolean isSocialImg;

    String email;
    String name;
    String profileImg;
    String socialProfileImg;

}
