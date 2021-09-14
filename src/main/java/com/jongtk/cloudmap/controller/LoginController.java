package com.jongtk.cloudmap.controller;

import com.jongtk.cloudmap.dto.AuthMemberDTO;
import com.jongtk.cloudmap.dto.SignupDTO;
import com.jongtk.cloudmap.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Log4j2
@Controller
@RequestMapping("/sign")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @GetMapping({"","/"})
    public String home(){
        return "redirect:/sign/login";
    }

    @GetMapping("login")
    public String sign(@AuthenticationPrincipal AuthMemberDTO authMemberDTO){
        if(authMemberDTO != null){
            log.warn("로그인된 상태임");
            return "redirect:/main";
        }
        return "/sign/login";
    }

    @GetMapping("fail")
    public void fail(){
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("setName")
    public String setName(@AuthenticationPrincipal AuthMemberDTO authMember){
        if(authMember.getName() == null || authMember.getName().trim().equals("")){
            return "/sign/setName";
        }else {
            return "redirect:/main";
        }
    }

    @PostMapping("setName")
    public String setName(@AuthenticationPrincipal AuthMemberDTO authMember, String name){
        if (name == null || name.trim().equals("")){
            return "redirect:/sign/setName";
        }

        if(loginService.setName(authMember.getEmail(), name)){
            authMember.setName(name);
            return "redirect:/main";
        }else {
            return "redirect:/sign/setName";
        }
    }

    @GetMapping("oauthfail")
    public String oauthFail(String result, RedirectAttributes rattr){
        if(result != null && result.equals("isLocal")) {
            rattr.addFlashAttribute("error", "로컬계정에 가입된 Email 입니다");
        } else {
            rattr.addFlashAttribute("error", "사용할 수 없는 계정입니다");
        }
        return "redirect:/sign/login";
    }

}
