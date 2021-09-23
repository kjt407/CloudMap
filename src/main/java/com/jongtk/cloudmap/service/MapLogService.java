package com.jongtk.cloudmap.service;

import com.jongtk.cloudmap.dto.SignupDTO;
import com.jongtk.cloudmap.entity.Member;
import com.jongtk.cloudmap.entity.MemberRole;
import com.jongtk.cloudmap.repository.MapLogRepository;
import com.jongtk.cloudmap.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MapLogService {

    private final MapLogRepository mapLogRepository;

    public void addMapLog(){

    }

}
