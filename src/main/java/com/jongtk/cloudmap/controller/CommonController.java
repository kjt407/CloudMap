package com.jongtk.cloudmap.controller;

import com.jongtk.cloudmap.dto.AuthMemberDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@Log4j2
@RequiredArgsConstructor
public class CommonController {
    @GetMapping({"","/"})
    public String exHome(){
        return "redirect:/sample/main";
    }

}
