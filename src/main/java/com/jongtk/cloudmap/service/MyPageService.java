package com.jongtk.cloudmap.service;

import com.jongtk.cloudmap.dto.FriendDTO;
import com.jongtk.cloudmap.dto.MyInfoDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MyPageService {

    MyInfoDTO getMyInfo(String username);
    String setLocalImg(String username, String imgUrl);
    String setSocialImg(String username, String imgUrl);
    String editName(String username, String str);
    boolean resign(String username, String password);


}
