package com.jongtk.cloudmap.service;

import com.jongtk.cloudmap.dto.ImageDTO;
import com.jongtk.cloudmap.dto.LikeMapLogDTO;
import com.jongtk.cloudmap.dto.MapLogDTO;
import com.jongtk.cloudmap.dto.MapLogListDTO;
import com.jongtk.cloudmap.entity.MapLog;
import com.jongtk.cloudmap.entity.MapLogImage;
import com.jongtk.cloudmap.entity.Member;
import com.jongtk.cloudmap.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public interface MapLogService {

    Long register(MapLogDTO mapLogDTO);

    List<MapLogListDTO> getMyList(String username);

    MapLogDTO getMyLog(long lno, String email);

    MapLogDTO getLog(long lno);

    List<LikeMapLogDTO> getMyLikes(String username);

    default Map<String,Object> dtoToEntity(MapLogDTO mapLogDTO, Member writer){
        Map<String,Object> entityMap = new HashMap<>();

        MapLog mapLog = MapLog.builder()
                .title(mapLogDTO.getTitle())
                .content(mapLogDTO.getContent())
                .lat(mapLogDTO.getLat())
                .lng(mapLogDTO.getLng())
                .writer(writer)
                .build();

        entityMap.put("mapLog",mapLog);

        List<MapLogImage> images = mapLogDTO.getImageDTOList().stream().map(
                image -> {
                    MapLogImage mapLogImage = MapLogImage.builder()
                            .uuid(image.getUuid())
                            .path(image.getPath())
                            .imgName(image.getImgName())
                            .mapLog(mapLog)
                            .build();
                    return mapLogImage;
                }).collect(Collectors.toList());

        entityMap.put("images",images);

        return entityMap;
    }

    default List<MapLogListDTO> entityToDTO(List<MapLog> mapLogList){
        List<MapLogListDTO> result = new ArrayList<>();

       result = mapLogList.stream().map(mapLog->{
           ImageDTO imageDTO = null;
           if(!mapLog.getImages().isEmpty()) {
               MapLogImage mapLogImage = mapLog.getImages().get(0);
               imageDTO = ImageDTO.builder()
                       .uuid(mapLogImage.getUuid())
                       .path(mapLogImage.getPath())
                       .imgName(mapLogImage.getImgName())
                       .build();
           }
           MapLogListDTO dto = MapLogListDTO.builder()
                   .lno(mapLog.getLno())
                   .title(mapLog.getTitle())
                   .lat(mapLog.getLat())
                   .lng(mapLog.getLng())
                   .writeDate(mapLog.getRegDate())
                   .modDate(mapLog.getModDate())
                   .imageDTOList(imageDTO)
                   .build();
           return dto;
       }).collect(Collectors.toList());

        return result;
    }

    default MapLogDTO entityToDTO(MapLog mapLog){
        MapLogDTO result;

        List<ImageDTO> imgList = new ArrayList<>();

        imgList = mapLog.getImages().stream().map(mapLogImage -> {
            ImageDTO imgDTO = ImageDTO.builder()
                    .uuid(mapLogImage.getUuid())
                    .imgName(mapLogImage.getImgName())
                    .path(mapLogImage.getPath())
                    .build();
            return imgDTO;
        }).collect(Collectors.toList());

        result = MapLogDTO.builder()
                .lno(mapLog.getLno())
                .title(mapLog.getTitle())
                .content(mapLog.getContent())
                .lat(mapLog.getLat())
                .lng(mapLog.getLng())
                .writer(mapLog.getWriter().getEmail())
                .writerName(mapLog.getWriter().getName())
                .writerImg(mapLog.getWriter().getProfileImg())
                .imageDTOList(imgList)
                .writeDate(mapLog.getRegDate())
                .modDate(mapLog.getModDate())
                .build();

        return result;
    }
//
//    default List<MapLogDTO> entityToDTO(List<MapLog> mapLogList){
//        List<MapLogDTO> result = new ArrayList<>();
//
//        result = mapLogList.stream().map(mapLog->{
//            MapLogDTO dto = MapLogDTO.builder()
//                    .lno(mapLog.getLno())
//                    .title(mapLog.getTitle())
//                    .content(mapLog.getContent())
//                    .writer(mapLog.getWriter().getEmail())
//                    .writerName(mapLog.getWriter().getName())
//                    .writerImg(mapLog.getWriter().getProfileImg())
//                    .lat(mapLog.getLat())
//                    .lng(mapLog.getLng())
//                    .writeDate(mapLog.getRegDate())
//                    .modDate(mapLog.getModDate())
//                    .imageDTOList(
//                            mapLog.getImages().stream().map(mapLogImage -> {
//                                ImageDTO imageDTO = ImageDTO.builder()
//                                        .uuid(mapLogImage.getUuid())
//                                        .path(mapLogImage.getPath())
//                                        .imgName(mapLogImage.getImgName())
//                                        .build();
//                                return imageDTO;
//                            }).collect(Collectors.toList()))
//                    .build();
//            return dto;
//        }).collect(Collectors.toList());
//
//        return result;
//    }
}
