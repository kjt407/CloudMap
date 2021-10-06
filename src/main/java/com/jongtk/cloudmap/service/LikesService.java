package com.jongtk.cloudmap.service;

import com.jongtk.cloudmap.dto.LikesDTO;
import com.jongtk.cloudmap.dto.LikesResultDTO;
import com.jongtk.cloudmap.repository.LikesRepository;
import com.jongtk.cloudmap.repository.MapLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LikesService {

    boolean addLike(long lno, String username);

    boolean deleteLike(long lno, String username);

    LikesResultDTO getLikes(long lno, String username);

}
