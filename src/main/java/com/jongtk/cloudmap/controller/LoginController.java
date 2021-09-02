package com.jongtk.cloudmap.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sign")
@Log4j2
@RequiredArgsConstructor
public class LoginController {
    @GetMapping({"","/"})
    public String home(){
        return "redirect:/sign/login";
    }

    @GetMapping("login")
    public void sign(){
    }

    @GetMapping("fail")
    public void fail(){
    }


}
