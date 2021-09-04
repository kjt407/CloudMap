package com.jongtk.cloudmap.controller;

import com.jongtk.cloudmap.dto.SignupDTO;
import com.jongtk.cloudmap.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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
    public void sign(){
    }

    @GetMapping("fail")
    public void fail(){
    }

    @PostMapping("register")
    public String register(@Valid SignupDTO signupDTO, BindingResult bindingResult, RedirectAttributes rattr){

        log.warn(signupDTO);

//        boolean result = loginService.signUp(signupDTO);
        if(bindingResult.hasErrors()){
            log.warn("--- 에러 검출됨 ---");
            List<ObjectError> errorList = bindingResult.getAllErrors();
            errorList.stream().forEach(error -> {
                log.warn(error.getDefaultMessage());
            });
        }

        rattr.addFlashAttribute("result", "반환된 내용입니다.");
        return "redirect:/sign/login";
    }

}
