package com.jongtk.cloudmap.controller;

import com.jongtk.cloudmap.dto.*;
import com.jongtk.cloudmap.service.FriendService;
import com.jongtk.cloudmap.service.LikesService;
import com.jongtk.cloudmap.service.MapLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@PreAuthorize("isAuthenticated()")
@Log4j2
@RequiredArgsConstructor
public class MyPageController {

    private final MapLogService mapLogService;

    @GetMapping("/getMyLikes")
    public List<LikeMapLogDTO> getMyLikes(@AuthenticationPrincipal AuthMemberDTO authMemberDTO){

        return mapLogService.getMyLikes(authMemberDTO.getUsername());

    }

    @GetMapping("/getMyInfo")
    public List<LikeMapLogDTO> getMyInfo(@AuthenticationPrincipal AuthMemberDTO authMemberDTO){

        return mapLogService.getMyLikes(authMemberDTO.getUsername());

    }



}
