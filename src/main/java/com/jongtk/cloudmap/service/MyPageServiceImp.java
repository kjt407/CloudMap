package com.jongtk.cloudmap.service;

import com.jongtk.cloudmap.dto.FriendDTO;
import com.jongtk.cloudmap.dto.MyInfoDTO;
import com.jongtk.cloudmap.entity.Friend;
import com.jongtk.cloudmap.entity.FriendPost;
import com.jongtk.cloudmap.entity.MapLog;
import com.jongtk.cloudmap.entity.Member;
import com.jongtk.cloudmap.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class MyPageServiceImp implements MyPageService {

    private final MemberRepository memberRepository;
    private final LikesRepository likesRepository;
    private final FriendRepository friendRepository;
    private final FriendPostRepository friendPostRepository;
    private final MapLogRepository mapLogRepository;
    private final MapLogImageRepository mapLogImageRepository;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


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
        String result = null;
        Optional<Member> memberOp = memberRepository.findById(username);

        if(memberOp.isPresent()){
            Member member = memberOp.get();
            member.setSocialImg(false);
            member.setProfileImg(imgUrl);
            memberRepository.save(member);
            result = member.getProfileImg();
        }

        return result;
    }

    @Override
    public String setSocialImg(String username, String imgUrl) {
        String result = null;
        Optional<Member> memberOp = memberRepository.findById(username);

        if(memberOp.isPresent()){
            Member member = memberOp.get();
            if(member.isFromSocial() && !member.isSocialImg()) {
                member.setSocialImg(true);
                member.setProfileImg(imgUrl);
                memberRepository.save(member);
                result = member.getProfileImg();
            }
        }

        return result;
    }

    @Override
    public String editName(String username, String str) {
        String result = null;

        Optional<Member> memberOp = memberRepository.findById(username);

        if(memberOp.isPresent()){
            Member member = memberOp.get();
            member.setName(str);
            memberRepository.save(member);
            result = member.getName();
        }
        return result;
    }

    @Override
    @Transactional
    public boolean resign(String username, String password) {

        Optional<Member> memberOp = memberRepository.findById(username);

        if(memberOp.isPresent()){

            Member member = memberOp.get();
            log.warn("전달받은 패스워드: "+password);
            log.warn("전달받은 패스워드(인코딩) : "+passwordEncoder.encode(password));
            log.warn("현재 패스워드 : "+member.getPassword());

            if( passwordEncoder.matches( password, member.getPassword() ) ){

                likesRepository.deleteAllByMember(member);
                mapLogImageRepository.deleteAllByMember(member);
                mapLogRepository.deleteAllByMember(member);
                friendPostRepository.deleteAllByMember(member);
                friendRepository.deleteAllByMember(member);
                memberRepository.deleteById(username);
                return true;
            }
        }
        return false;
    }
}
