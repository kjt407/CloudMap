package com.jongtk.cloudmap.controller;

import com.jongtk.cloudmap.dto.AuthMemberDTO;
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
        return "redirect:/sample/main";
    }

    @GetMapping("/main")
    public void exMember(@AuthenticationPrincipal AuthMemberDTO authMemberDTO) {
        log.info(authMemberDTO);
    }

//    @GetMapping("/admin")
//    public void exAdmin(){
//
//    }

    @GetMapping("/login")
    public String exLogin(){
        return "login";
    }
}
