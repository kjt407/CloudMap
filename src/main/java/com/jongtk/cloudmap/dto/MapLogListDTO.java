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
public class MapLogListDTO {

    private Long lno;

    private String title;

    private double lat;

    private double lng;

    private ImageDTO imageDTOList;

    private LocalDateTime writeDate;
    private LocalDateTime modDate;

}
