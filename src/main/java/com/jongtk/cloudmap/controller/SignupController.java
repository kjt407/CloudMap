package com.jongtk.cloudmap.controller;

import com.jongtk.cloudmap.dto.SignupDTO;
import com.jongtk.cloudmap.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.*;

@Log4j2
@RestController
@RequestMapping("/sign")
@RequiredArgsConstructor
public class SignupController {

    private final LoginService loginService;

    @PostMapping("register")
    public JSONObject register(@Valid @RequestBody SignupDTO signupDTO, BindingResult bindingResult){

        log.warn(signupDTO);
        JSONObject result = new JSONObject();
        List<String> errors = new ArrayList<>();

        if(bindingResult.hasErrors()){
            log.warn("--- 에러 검출됨 ---");

            List<ObjectError> errorList = bindingResult.getAllErrors();
            errorList.stream().forEach(error -> {
                log.warn(error.getDefaultMessage());
                errors.add(error.getDefaultMessage());
            });

            result.put("result",false);
            result.put("errors",errors);
            return result;
        }

        String email = loginService.signUp(signupDTO);

        if(email == null){
            errors.add("이미 가입된 이메일입니다.");
            result.put("result",false);
            result.put("errors",errors);
            return result;
        }

        result.put("result","true");
        result.put("email",email);
        return result;
    }

}
