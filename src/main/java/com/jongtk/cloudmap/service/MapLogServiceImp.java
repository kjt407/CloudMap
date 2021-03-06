package com.jongtk.cloudmap.service;

import com.jongtk.cloudmap.dto.LikeMapLogDTO;
import com.jongtk.cloudmap.dto.MapLogDTO;
import com.jongtk.cloudmap.dto.MapLogListDTO;
import com.jongtk.cloudmap.entity.Likes;
import com.jongtk.cloudmap.entity.MapLog;
import com.jongtk.cloudmap.entity.MapLogImage;
import com.jongtk.cloudmap.entity.Member;
import com.jongtk.cloudmap.repository.LikesRepository;
import com.jongtk.cloudmap.repository.MapLogImageRepository;
import com.jongtk.cloudmap.repository.MapLogRepository;
import com.jongtk.cloudmap.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class MapLogServiceImp implements MapLogService{

    private final MapLogRepository mapLogRepository;
    private final MapLogImageRepository mapLogImageRepository;
    private final MemberRepository memberRepository;
    private final LikesRepository likesRepository;

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
    public boolean edit(MapLogDTO mapLogDTO, String username, String[] uuids) {
        Optional<Member> memberOp = memberRepository.findById(username);
        Optional<MapLog> mapLogOp = mapLogRepository.findById(mapLogDTO.getLno());

        if(memberOp.isPresent() && mapLogOp.isPresent()) {
            Member member = memberOp.get();
            MapLog mapLog = mapLogOp.get();

            if(mapLog.getWriter() == member){
                //????????? ????????? ????????? DB??????
                log.warn("--------------- uuids ??? ????????? ?????? ??????");
                if(uuids != null && uuids.length > 0){
                    for (String uuid:uuids){
                        mapLogImageRepository.deleteByUuid(uuid);
                    }
                    log.warn("--------------- uuids ??? ????????? ?????? ??????");
                }

                //?????? ????????? ????????? DB??????
                mapLogDTO.getImageDTOList().stream().forEach(
                    image -> {
                        MapLogImage mapLogImage = MapLogImage.builder()
                                .uuid(image.getUuid())
                                .path(image.getPath())
                                .imgName(image.getImgName())
                                .mapLog(mapLog)
                                .build();
                        mapLogImageRepository.save(mapLogImage);
                    }
                );

                mapLog.editMapLog(mapLogDTO.getTitle(),mapLogDTO.getContent());
                mapLogRepository.save(mapLog);
                return true;
            }
        }
        return false;
    }

    @Override
    @Transactional
    public boolean delete(long lno, String username) {

        Optional<Member> memberOp = memberRepository.findById(username);
        Optional<MapLog> mapLogOp = mapLogRepository.findById(lno);

        if(memberOp.isPresent() && mapLogOp.isPresent()){
            Member member = memberOp.get();
            MapLog mapLog = mapLogOp.get();

            if(mapLog.getWriter() == member){
                likesRepository.deleteByMapLog(mapLog);
                mapLogRepository.delete(mapLog);
                return true;
            }
        }

        return false;
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

    @Override
    public MapLogDTO getLog(long lno) {
        MapLogDTO result;

            Optional<MapLog> maplog = mapLogRepository.findById(lno);

            if (maplog.isPresent()) {
                result = entityToDTO(maplog.get());
                return result;
            }
        return null;
    }

    @Override
    public List<LikeMapLogDTO> getMyLikes(String username) {

        List<LikeMapLogDTO> result = new ArrayList<>();

        Optional<Member> member = memberRepository.findById(username);

        if(member.isPresent()) {
            List<Likes> likes = likesRepository.findByMember(member.get());
            log.warn(likes);
            result =
            likes.stream().map(like -> {
                MapLog mapLog = like.getMapLog();
                boolean isMe = false;
                if(mapLog.getWriter().getEmail() == username){
                    isMe = true;
                }
                LikeMapLogDTO likeMapLogDTO = LikeMapLogDTO.builder()
                        .lno(mapLog.getLno())
                        .title(mapLog.getTitle())
                        .lat(mapLog.getLat())
                        .lng(mapLog.getLng())
                        .isMe(isMe)
                        .writerEmail(mapLog.getWriter().getEmail())
                        .writerName(mapLog.getWriter().getName())
                        .writerProfileImg(mapLog.getWriter().getProfileImg())
                        .likedDate(like.getRegDate())
                        .writeDate(mapLog.getRegDate())
                        .modDate(mapLog.getModDate())
                        .build();
                return likeMapLogDTO;
            }).collect(Collectors.toList());
        }

        return result;
    }
}

