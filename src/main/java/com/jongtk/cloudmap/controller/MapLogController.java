package com.jongtk.cloudmap.controller;

import com.jongtk.cloudmap.dto.AuthMemberDTO;
import com.nimbusds.oauth2.sdk.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@PreAuthorize("isAuthenticated()")
@Log4j2
@RequiredArgsConstructor
public class MapLogController {

    @GetMapping("/addLog/{lno}")
    public String addLog(@PathVariable("lno") long lno, @AuthenticationPrincipal AuthMemberDTO authMemberDTO){
        return "요청받은 번호는"+lno+" 요청한 사람은 "+authMemberDTO.getName();
    }

    @GetMapping("/getLog/{lno}")
    public String getLog(@PathVariable("lno") long lno, @AuthenticationPrincipal AuthMemberDTO authMemberDTO){
        return "요청받은 번호는"+lno+" 요청한 사람은 "+authMemberDTO.getName();
    }


}
