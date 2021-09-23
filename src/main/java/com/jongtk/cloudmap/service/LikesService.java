package com.jongtk.cloudmap.service;

import com.jongtk.cloudmap.repository.LikesRepository;
import com.jongtk.cloudmap.repository.MapLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikesService {

    private final LikesRepository likesRepository;

    public void addLike(){

    }

}
