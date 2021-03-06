package com.jongtk.cloudmap.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MapLogDTO{

    private Long lno;

    private String title;

    private String content;

    private double lat;

    private double lng;

    private String writer;
    private String writerName;
    private String writerImg;

    @Builder.Default
    private List<ImageDTO> imageDTOList = new ArrayList<>();

    private LocalDateTime writeDate;
    private LocalDateTime modDate;

}
