package com.jongtk.cloudmap.service;

import com.jongtk.cloudmap.dto.AuthMemberDTO;
import com.jongtk.cloudmap.entity.Member;
import com.jongtk.cloudmap.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("UserDetailsService 호출됨 사용자 ID: "+ username);

        Optional<Member> result = memberRepository.findByEmail(username,false);

        if(result.isEmpty()){
            throw new UsernameNotFoundException("Check Email or Social");
        }

        Member member = result.get();

        log.info("----------------------");
        log.info(member);

        AuthMemberDTO authMember = new AuthMemberDTO(
                member.getEmail(),
                member.getPassword(),
                member.isFromSocial(),
                member.getRoleSet().stream().map(
                        role -> new SimpleGrantedAuthority("ROLE_"+role.name())
                ).collect(Collectors.toSet())
        );

        authMember.setName(member.getName());
        authMember.setFromSocial(member.isFromSocial());


        return authMember;
    }



}


