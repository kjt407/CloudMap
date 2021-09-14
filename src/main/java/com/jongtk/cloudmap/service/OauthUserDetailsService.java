package com.jongtk.cloudmap.service;

import com.jongtk.cloudmap.dto.AuthMemberDTO;
import com.jongtk.cloudmap.entity.Member;
import com.jongtk.cloudmap.entity.MemberRole;
import com.jongtk.cloudmap.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class OauthUserDetailsService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        
        log.info("oauth 유저 서비스 실행되었음");

        String clientName = userRequest.getClientRegistration().getClientName();

        log.info("client Name: "+clientName);
        log.info(userRequest.getAdditionalParameters());

        OAuth2User oAuth2User = super.loadUser(userRequest);

        log.info("--------------------------");
        oAuth2User.getAttributes().forEach((k,v)->{
            log.info(k+" : "+v);
        });

        String email = null;
        Member member = null;
        String name = null;
        Map<String, Object> attr = new HashMap<>();

        if(clientName.equals("Google")){
            email = oAuth2User.getAttribute("email");
            attr.put("picture",oAuth2User.getAttribute("picture"));
        }else if(clientName.equals("kakao")){
            Map<String, Object> kakaoAccount = (Map<String, Object>) oAuth2User.getAttribute("kakao_account");
            Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
            attr.put("picture",profile.get("profile_image_url"));
            email = (String) kakaoAccount.get("email");
            name = (String) profile.get("nickname");
        }
        attr.put("clientName",clientName);

        if(isLocalMember(email)){
            OAuth2Error oauth2Error = new OAuth2Error("ALREADY_REGISTERED_EMAIL");
            throw new OAuth2AuthenticationException(oauth2Error, oauth2Error.toString());
        }

        if(name == null){
            member = saveSocialMember(email);
        }else {
            member = saveSocialMember(email,name);
        }


        AuthMemberDTO authMember = new AuthMemberDTO(
            member.getEmail(),
            member.getPassword(),
            true,
            member.getRoleSet().stream().map(
                    role -> new SimpleGrantedAuthority("ROLE_"+role.name())
            ).collect(Collectors.toList()),
            attr
        );
        authMember.setProfileImg(member.getProfileImg());
        authMember.setName(member.getName());

        return authMember;
    }


    private Member saveSocialMember(String email){
        Optional<Member> result = memberRepository.findByEmail(email,true);

        if(result.isPresent()){
            //이미 가입된 소셜 계정일 경우 회원정보 조회값 리턴
            return result.get();
        }

        Member member = Member.builder()
                .email(email)
                .password(passwordEncoder.encode("1111"))
                .fromSocial(true)
                .build();
        member.addMemeberRole(MemberRole.USER);

        memberRepository.save(member);

        return member;
    }

    private Member saveSocialMember(String email,String name){
        Optional<Member> result = memberRepository.findByEmail(email,true);

        if(result.isPresent()){
            return result.get();
            // 이미 가입된경우 return
        }

        Member member = Member.builder()
                .email(email)
                .name(name)
                .password(passwordEncoder.encode("completed"))
                .fromSocial(true)
                .build();
        member.addMemeberRole(MemberRole.USER);

        memberRepository.save(member);

        return member;
    }

    private boolean isLocalMember(String email){
        Optional<Member> result = memberRepository.findByEmail(email,false);

        if(result.isPresent()){
            return true;
        }
        return false;
    }
}


