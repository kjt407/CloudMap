package com.jongtk.cloudmap.service;

import com.jongtk.cloudmap.entity.MapLog;
import com.jongtk.cloudmap.entity.Member;
import com.jongtk.cloudmap.repository.MapLogRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;

import java.util.stream.IntStream;

@SpringBootTest
@Service
public class MapLogServiceTests {

    @Autowired
    private MapLogRepository mapLogRepository;

    @Test
    private void addMapLogTest(){

        IntStream.rangeClosed(1,10).forEach(i->{
            MapLog.builder()
                    .title("제목"+i)
                    .content("입력된 내용"+i)
                    .lat(37.0129392992)
                    .lng(128.21903882)
                    .writer(Member.builder().email("kjt407@nate.com").build())
                    .build();
        });

    }


}
