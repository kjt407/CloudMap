package com.example.studyguide.service;

import com.example.studyguide.dto.AuthMemberDTO;
import com.example.studyguide.entity.Member;
import com.example.studyguide.entity.MemberRole;
import com.example.studyguide.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class OauthUserDetailsService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
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

        if(clientName.equals("Google")){
            email = oAuth2User.getAttribute("email");
        }

        Member member = saveSocialMember(email);

        AuthMemberDTO authMember = new AuthMemberDTO(
                member.getEmail(),
                member.getPassword(),
                true,
                member.getRoleSet().stream().map(
                        role -> new SimpleGrantedAuthority("ROLE_"+role.name())
                ).collect(Collectors.toList()),
                oAuth2User.getAttributes()
        );
        authMember.setName(member.getName());

        return authMember;
    }

    private Member saveSocialMember(String email){
        Optional<Member> result = memberRepository.findByEmail(email,true);

        if(result.isPresent()){
            return result.get();
            // 이미 가입된경우 return
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
}


