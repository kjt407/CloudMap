package com.jongtk.cloudmap.controller;

import com.jongtk.cloudmap.dto.*;
import com.jongtk.cloudmap.service.FriendService;
import com.jongtk.cloudmap.service.LikesService;
import com.jongtk.cloudmap.service.MapLogService;
import com.jongtk.cloudmap.service.MyPageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
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
    private final MyPageService myPageService;

    @Value("${com.jongtk.cloudmap.upload.path}")
    private String uploadPath;

    private final String folderPath = "profile";


    @GetMapping("/getMyLikes")
    public List<LikeMapLogDTO> getMyLikes(@AuthenticationPrincipal AuthMemberDTO authMemberDTO){

        return mapLogService.getMyLikes(authMemberDTO.getUsername());

    }

    @GetMapping("/getMyInfo")
    public ResponseEntity<MyInfoDTO> getMyInfo(@AuthenticationPrincipal AuthMemberDTO authMemberDTO){

        MyInfoDTO result = myPageService.getMyInfo(authMemberDTO.getUsername());

        if(result == null){
            return new ResponseEntity<>(result,HttpStatus.BAD_REQUEST);
        }

        if(result.isFromSocial()){
            result.setSocialProfileImg((String) authMemberDTO.getAttr().get("picture"));
        }

        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @PutMapping("/setLocalProfile")
    public ResponseEntity<String> setLocalProfile(@AuthenticationPrincipal AuthMemberDTO authMemberDTO, MultipartFile file){

        if (!file.getContentType().startsWith("image")) {
            log.warn("???????????? ????????? ????????? ????????? ??????");
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        String originFileName = file.getOriginalFilename();
        String convertFileName = originFileName.substring(originFileName.lastIndexOf("//") + 1);

        File uploadPathFolder = new File(uploadPath,folderPath);
        if(!uploadPathFolder.exists()){
            uploadPathFolder.mkdirs();
        }

        String uuid = UUID.randomUUID().toString();
        String saveName = uploadPath + File.separator + folderPath + File.separator + uuid + "_" + convertFileName;
        Path savePath = Paths.get(saveName);
        try {
            file.transferTo(savePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String resultUrl = URLEncoder.encode(folderPath+"/"+uuid+"_"+convertFileName);
        String result = myPageService.setLocalImg(authMemberDTO.getUsername(), "displayProfile?imgUrl="+resultUrl);
        if(result == null || result.trim().equals("")){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/setSocialProfile")
    public ResponseEntity<String> setSocialProfile(@AuthenticationPrincipal AuthMemberDTO authMemberDTO){
        log.warn("?????? ????????? ?????? ???????????? ??????");

        if (!authMemberDTO.isFromSocial() && authMemberDTO.isSocialImg()) {
            log.warn("?????? ?????????????????? ?????? ???????????????");
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        String result = myPageService.setSocialImg(authMemberDTO.getUsername(), (String) authMemberDTO.getAttributes().get("picture"));
        
        if(result == null){
            log.warn("????????? ????????? ?????????");
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        log.warn("?????? ????????? ?????? ?????? ??????");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/editMyName")
    public ResponseEntity<String> editMyName(@AuthenticationPrincipal AuthMemberDTO authMemberDTO, String name){

        if(name == null || name.isBlank()){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        String result = myPageService.editName(authMemberDTO.getUsername(), name);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/displayProfile")
    public ResponseEntity<byte[]> displayProfile(String imgUrl){
        ResponseEntity<byte[]> result = null;

        try {
            String srcFileName = URLDecoder.decode(imgUrl, "UTF-8");
            File file = new File(uploadPath + File.separator + srcFileName);
            HttpHeaders header = new HttpHeaders();
            header.add("Content-Type", Files.probeContentType(file.toPath()));

            result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file),header,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }


    @DeleteMapping("/resign")
    public boolean resign(@AuthenticationPrincipal AuthMemberDTO authMemberDTO, Authentication authentication, HttpServletResponse response, String password, String checkStr) throws IOException {

        String passwordCheck = password;

        if(authMemberDTO.isFromSocial()){
            passwordCheck = "completed";
        }

        if(!passwordCheck.isBlank() && !checkStr.isBlank() && checkStr.equals("?????????????????????.")) {

            if (myPageService.resign(authMemberDTO.getUsername(), passwordCheck)) {
                log.warn("???????????? ?????? ?????????");
                authentication.setAuthenticated(false);
//                response.sendRedirect("/logout");
                return true;
            }
        }

        return false;
    }





}
