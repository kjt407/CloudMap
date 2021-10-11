package com.jongtk.cloudmap.service;

import com.jongtk.cloudmap.dto.FriendDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FriendService {

    List<FriendDTO> getFreindList(String username);
    List<FriendDTO> getReceiveList(String username);
    List<FriendDTO> getSearch(String username, String str);
    boolean postFreind(String username, String targetEmail);
    boolean acceptFreind(String username, String targetEmail);
    boolean refuseFreind(String username, String targetEmail);
    boolean deleteFreind(String username, String targetEmail);
    boolean isFreind(String username, String targetEmail);
    boolean isFreind(String username, long lno);

}
