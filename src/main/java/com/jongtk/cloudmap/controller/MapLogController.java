package com.jongtk.cloudmap.controller;

import com.jongtk.cloudmap.dto.*;
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
public class MapLogController {

    private final MapLogService mapLogService;
    private final LikesService likesService;

    @Value("${com.jongtk.cloudmap.upload.path}")
    private String uploadPath;

    @PreAuthorize("#authMemberDTO != null && #authMemberDTO.username == #mapLogDTO.writer")
    @PostMapping("/register")
    public ResponseEntity<Boolean> register(@AuthenticationPrincipal AuthMemberDTO authMemberDTO, MapLogDTO mapLogDTO, MultipartFile[] files) {

//        log.info("업로드 된 파일---------------------------------");
//        log.info(files);
//        log.info(files.length);

        List<ImageDTO> imageDTOList = new ArrayList<>();

        if(files != null && !files[0].isEmpty()) {
            for (MultipartFile file : files) {
                if (!file.getContentType().startsWith("image")) {
                    log.warn("업로드된 파일이 이미지 형식이 아님");
                    return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
                }
                String originFileName = file.getOriginalFilename();
                String convertFileName = originFileName.substring(originFileName.lastIndexOf("//") + 1);
                log.warn("원본이름: " + originFileName);
                log.warn("편집이름: " + convertFileName);

                String folderPath = makeFolder();

                String uuid = UUID.randomUUID().toString();

                String saveName = uploadPath + File.separator + folderPath + File.separator + uuid + "_" + convertFileName;

                Path savePath = Paths.get(saveName);

                try {
                    file.transferTo(savePath);
                    imageDTOList.add(new ImageDTO(uuid, convertFileName, folderPath));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        mapLogDTO.setImageDTOList(imageDTOList);
        mapLogService.register(mapLogDTO);

        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    private String makeFolder() {

        String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        String folderPath = str.replace("//",File.separator);

        File uploadPathFolder = new File(uploadPath,folderPath);
        if(!uploadPathFolder.exists()){
            uploadPathFolder.mkdirs();
        }

        return folderPath;
    }

    @GetMapping("/getList/{username}")
    public String getLog(@PathVariable("lno") long lno, @AuthenticationPrincipal AuthMemberDTO authMemberDTO){
        return "요청받은 번호는"+lno+" 요청한 사람은 "+authMemberDTO.getName();
    }

    @GetMapping("/getMyList")
    public List<MapLogListDTO> getMyLog(@AuthenticationPrincipal AuthMemberDTO authMemberDTO){
        List<MapLogListDTO> result = mapLogService.getMyList(authMemberDTO.getEmail());

        log.warn(result);

        return result;
    }

    @GetMapping("/getMyLog/{lno}")
    public MapLogDTO getMyLog(@PathVariable("lno") long lno, @AuthenticationPrincipal AuthMemberDTO authMemberDTO){
        MapLogDTO result = mapLogService.getMyLog(lno, authMemberDTO.getEmail());

        return result;
    }

    @GetMapping("/display")
    public ResponseEntity<byte[]> getMyLog(String imgUrl){
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

    @GetMapping("/getLikes")
    public LikesResultDTO getLikes(@AuthenticationPrincipal AuthMemberDTO authMemberDTO, long lno){

        return likesService.getLikes(lno, authMemberDTO.getUsername());
    }

    @PostMapping("/addLikes")
    public boolean addLike(@AuthenticationPrincipal AuthMemberDTO authMemberDTO, long lno){
        return likesService.addLike(lno, authMemberDTO.getUsername());
    }


    @DeleteMapping("/deleteLikes")
    public boolean deleteLike(@AuthenticationPrincipal AuthMemberDTO authMemberDTO, long lno){
        return likesService.deleteLike(lno, authMemberDTO.getUsername());
    }

}
