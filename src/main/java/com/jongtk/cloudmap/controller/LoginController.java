package com.jongtk.cloudmap.controller;

import com.jongtk.cloudmap.dto.SignupDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Log4j2
@Controller
@RequestMapping("/sign")
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

    @PostMapping("register")
    public String register(SignupDTO signupDTO, RedirectAttributes rattr){

        log.warn(signupDTO);
        rattr.addFlashAttribute("result", "반환된 내용입니다.");
        return "redirect:/sign/login";
    }

}
