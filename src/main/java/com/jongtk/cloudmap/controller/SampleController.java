package com.example.studyguide.controller;

import com.example.studyguide.dto.AuthMemberDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sample/")
@Log4j2
@RequiredArgsConstructor
public class SampleController {
    @GetMapping({"/"})
    public String exHome(){
        return "redirect:/sample/all";
    }

    @GetMapping({"/all"})
    public String exAll(){
        return "all";
    }

    @GetMapping("/member")
    public String exMember(@AuthenticationPrincipal AuthMemberDTO authMemberDTO) {
        log.info(authMemberDTO);
        return "member";

    }

    @GetMapping("/admin")
    public String exAdmin(){
        return "admin";
    }

    @GetMapping("/login")
    public String exLogin(){
        return "login";
    }
}
