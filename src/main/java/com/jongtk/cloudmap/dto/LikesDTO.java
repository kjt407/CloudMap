package com.jongtk.cloudmap.dto;

import lombok.*;

import java.net.URLEncoder;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LikesDTO {

    private long idx;
    private String email;
    private String name;
    private String profileImg;

}
