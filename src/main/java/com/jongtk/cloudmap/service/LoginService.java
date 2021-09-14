package com.jongtk.cloudmap.service;

import com.jongtk.cloudmap.dto.SignupDTO;
import com.jongtk.cloudmap.entity.Member;
import com.jongtk.cloudmap.entity.MemberRole;
import com.jongtk.cloudmap.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    public String signUp(SignupDTO dto){

        Optional<Member> already =  memberRepository.findById(dto.getEmail());

        if(already.isPresent()) {
            return null;
        }

        Member member = Member.builder()
                .email(dto.getEmail())
                .name(dto.getName())
                .password(passwordEncoder.encode(dto.getPassword()))
                .fromSocial(false)
                .build();
        member.addMemeberRole(MemberRole.USER);

        memberRepository.save(member);

        return member.getEmail();
    }

    public Boolean setName(String email,String name){
        Optional<Member> member = memberRepository.findById(email);

        if(member.isPresent()){
            Member resultMember = member.get();
            resultMember.setName(name);
            resultMember.setPassword(passwordEncoder.encode("completed"));
            memberRepository.save(resultMember);
            return true;
        }
        return false;
    }
}
