package com.jongtk.cloudmap.dto;

import com.jongtk.cloudmap.entity.Member;
import lombok.*;

import javax.persistence.*;
import java.util.Date;


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

    private Date writeDate;
    private Date ModDate;

}
