package com.jongtk.cloudmap.service;

import com.jongtk.cloudmap.dto.FriendDTO;
import com.jongtk.cloudmap.dto.MyInfoDTO;
import com.jongtk.cloudmap.entity.Friend;
import com.jongtk.cloudmap.entity.FriendPost;
import com.jongtk.cloudmap.entity.MapLog;
import com.jongtk.cloudmap.entity.Member;
import com.jongtk.cloudmap.repository.FriendPostRepository;
import com.jongtk.cloudmap.repository.FriendRepository;
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
public class MyPageServiceImp implements MyPageService {

    private final MemberRepository memberRepository;

    @Override
    public MyInfoDTO getMyInfo(String username) {
        Optional<Member> memberOp = memberRepository.findById(username);

        if(memberOp.isPresent()){
            Member member = memberOp.get();
            MyInfoDTO myInfoDTO = MyInfoDTO.builder()
                    .fromSocial(member.isFromSocial())
                    .isSocialImg(member.isSocialImg())
                    .email(member.getEmail())
                    .name(member.getName())
                    .profileImg(member.getProfileImg())
                    .build();

            return myInfoDTO;
        }

        return null;
    }

    @Override
    public String setLocalImg(String username, String imgUrl) {
        return null;
    }

    @Override
    public String setSocialImg(String username, String imgUrl) {
        return null;
    }
}
