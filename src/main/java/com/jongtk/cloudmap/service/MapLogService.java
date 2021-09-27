package com.jongtk.cloudmap.service;

import com.jongtk.cloudmap.dto.MapLogDTO;
import com.jongtk.cloudmap.entity.MapLog;
import com.jongtk.cloudmap.entity.MapLogImage;
import com.jongtk.cloudmap.entity.Member;
import com.jongtk.cloudmap.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public interface MapLogService {

    Long register(MapLogDTO mapLogDTO);

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
}
