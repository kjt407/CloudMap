package com.jongtk.cloudmap.service;

import com.jongtk.cloudmap.dto.MapLogDTO;
import com.jongtk.cloudmap.dto.MapLogListDTO;
import com.jongtk.cloudmap.entity.MapLog;
import com.jongtk.cloudmap.entity.MapLogImage;
import com.jongtk.cloudmap.entity.Member;
import com.jongtk.cloudmap.repository.MapLogImageRepository;
import com.jongtk.cloudmap.repository.MapLogRepository;
import com.jongtk.cloudmap.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.ArrayList;
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

    @Override
    @Transactional
    public List<MapLogListDTO> getMyList(String username) {
        List<MapLogListDTO> result = new ArrayList<>();

        Optional<Member> member = memberRepository.findById(username);

        if(member.isPresent()) {
            List<MapLog> mapLogList = mapLogRepository.getMyList(member.get());
            result = entityToDTO(mapLogList);
        }

        return result;
    }

    @Override
    public MapLogDTO getMyLog(long lno, String username) {

        MapLogDTO result;

        Optional<Member> member = memberRepository.findById(username);
        if(member.isPresent()) {
            Optional<MapLog> maplog = mapLogRepository.getMyLog(lno, member.get());

            if (maplog.isPresent()) {
                result = entityToDTO(maplog.get());
                return result;
            }
        }

        return null;
    }

}

