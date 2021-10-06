package com.jongtk.cloudmap.service;

import com.jongtk.cloudmap.dto.LikesDTO;
import com.jongtk.cloudmap.dto.LikesResultDTO;
import com.jongtk.cloudmap.entity.Likes;
import com.jongtk.cloudmap.entity.MapLog;
import com.jongtk.cloudmap.entity.Member;
import com.jongtk.cloudmap.repository.LikesRepository;
import com.jongtk.cloudmap.repository.MapLogRepository;
import com.jongtk.cloudmap.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class LikesServiceImp implements LikesService{

    private final LikesRepository likesRepository;
    private final MemberRepository memberRepository;
    private final MapLogRepository mapLogRepository;

    @Override
    public LikesResultDTO getLikes(long lno, String username) {

        Optional<Member> memberOp = memberRepository.findById(username);
        Optional<MapLog> mapLogOp = mapLogRepository.findById(lno);

        LikesResultDTO likesResultDTO = null;

        if(memberOp.isPresent() && mapLogOp.isPresent()){
            MapLog mapLog = mapLogOp.get();
            Member member = memberOp.get();

            boolean isLiked = likesRepository.existsByMemberAndMapLog(member, mapLog);
            List<LikesDTO> likesDTOList = new ArrayList<>();

            List<Likes> likesList =likesRepository.getLikes(mapLog);
            likesDTOList = likesList.stream().map(likes -> {
                LikesDTO likesDTO = LikesDTO.builder()
                        .idx(likes.getIdx())
                        .name(likes.getMember().getName())
                        .email(likes.getMember().getEmail())
                        .profileImg(likes.getMember().getProfileImg())
                        .build();
                return likesDTO;
            }).collect(Collectors.toList());

            likesResultDTO = LikesResultDTO.builder()
                    .isLiked(isLiked)
                    .likesCount(likesDTOList.size())
                    .likesList(likesDTOList)
                    .build();

            return likesResultDTO;
        }

        return likesResultDTO;
    }

    @Override
    public boolean addLike(long lno, String username) {

        log.warn("addLike 서비스 함수 실행됨");

        Optional<Member> memberOp = memberRepository.findById(username);
        Optional<MapLog> mapLogOp = mapLogRepository.findById(lno);

        if(memberOp.isPresent() && mapLogOp.isPresent()){
        log.warn("OPtional 예외처리 통과됨");
            Member member = memberOp.get();
            MapLog mapLog = mapLogOp.get();

            if (likesRepository.existsByMemberAndMapLog(member,mapLog)){
        log.warn("이미 존재하는 경우");
                return false;
            }

            Likes likes = Likes.builder()
                    .mapLog(mapLog)
                    .member(member)
                    .build();
            likesRepository.save(likes);
        log.warn("정상적으로 등록됨");
            return true;
        }

        return false;
    }

    @Override
    public boolean deleteLike(long lno, String username) {

        Optional<Member> memberOp = memberRepository.findById(username);
        Optional<MapLog> mapLogOp = mapLogRepository.findById(lno);

        if(memberOp.isPresent() && mapLogOp.isPresent()){
            log.warn("OPtional 예외처리 통과됨");
            Member member = memberOp.get();
            MapLog mapLog = mapLogOp.get();

            try {
                Optional<Likes> likes = likesRepository.findByMemberAndMapLog(member, mapLog);

                if (likes.isPresent()){

                    likesRepository.deleteById(likes.get().getIdx());
                    log.warn("정상적으로 삭제됨");
                    return true;
                }
            } catch (Exception e){
                return false;
            }
        }

        return false;
    }

}
