package com.jongtk.cloudmap.dto;

import lombok.*;

import java.util.List;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LikesResultDTO {

    boolean isLiked;

    long likesCount;

    List<LikesDTO> likesList;
}
