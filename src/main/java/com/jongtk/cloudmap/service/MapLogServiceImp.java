package com.jongtk.cloudmap.service;

import com.jongtk.cloudmap.dto.MapLogDTO;
import com.jongtk.cloudmap.entity.MapLog;
import com.jongtk.cloudmap.entity.MapLogImage;
import com.jongtk.cloudmap.entity.Member;
import com.jongtk.cloudmap.repository.MapLogImageRepository;
import com.jongtk.cloudmap.repository.MapLogRepository;
import com.jongtk.cloudmap.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MapLogServiceImp implements MapLogService{

    private final MapLogRepository mapLogRepository;
    private final MapLogImageRepository mapLogImageRepository;
    private final MemberRepository memberRepository;

    @Override
    public Long register(MapLogDTO mapLogDTO) {

        Optional<Member> member = memberRepository.findById(mapLogDTO.getWriter());
        if(member.isEmpty()){
            return null;
        }

        Map<String, Object> entityMap = dtoToEntity(mapLogDTO,member.get());
        MapLog mapLog = (MapLog)entityMap.get("mapLog");
        List<MapLogImage> imageList = (List<MapLogImage>) entityMap.get("images");

        mapLogRepository.save(mapLog);
        imageList.stream().forEach(image->{
            mapLogImageRepository.save(image);
        });

        return mapLog.getLno();
    }
}

