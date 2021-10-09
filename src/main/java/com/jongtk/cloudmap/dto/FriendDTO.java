package com.jongtk.cloudmap.dto;

import lombok.*;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FriendDTO {

    private long id;
    private String email;
    private String name;
    private String profileImg;
    String state;

}
