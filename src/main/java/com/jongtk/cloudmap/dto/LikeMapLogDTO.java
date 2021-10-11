package com.jongtk.cloudmap.dto;

import lombok.*;

import java.time.LocalDateTime;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LikeMapLogDTO {

    private Long lno;

    private String title;

    private double lat;

    private double lng;

    private String writerEmail;
    private String writerName;
    private String writerProfileImg;

    private LocalDateTime likedDate;
    private LocalDateTime writeDate;
    private LocalDateTime modDate;

}
