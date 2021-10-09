package com.jongtk.cloudmap.service;

import com.jongtk.cloudmap.dto.LikesResultDTO;
import org.springframework.stereotype.Service;

@Service
public interface LikesService {

    boolean addLike(long lno, String username);

    boolean deleteLike(long lno, String username);

    LikesResultDTO getLikes(long lno, String username);

}
