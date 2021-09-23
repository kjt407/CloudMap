package com.jongtk.cloudmap.controller;

import com.jongtk.cloudmap.dto.AuthMemberDTO;
import com.nimbusds.oauth2.sdk.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/")
@Log4j2
@RequiredArgsConstructor
public class CommonController {

    @GetMapping({"","/"})
    public String Home(){
        return "/intro";
    }

    @GetMapping("/main")
    public String Main(@AuthenticationPrincipal AuthMemberDTO authMemberDTO) {
        log.info(authMemberDTO);
        if(authMemberDTO.isFromSocial() && StringUtils.isBlank(authMemberDTO.getName())){
            return "redirect:/sign/setName";
        } else {
            return "/main";
        }
    }

}
