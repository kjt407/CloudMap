package com.jongtk.cloudmap;

import com.jongtk.cloudmap.entity.Likes;
import com.jongtk.cloudmap.entity.MapLog;
import com.jongtk.cloudmap.entity.Member;
import com.jongtk.cloudmap.repository.LikesRepository;
import com.jongtk.cloudmap.repository.MapLogRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.IntStream;

@SpringBootTest
public class MapLogTests {

    @Autowired
    private MapLogRepository mapLogRepository;
    @Autowired
    private LikesRepository likesRepository;

    @Test
    @Transactional @Rollback(false)
    public void addMapLogTest(){

        IntStream.rangeClosed(1,10).forEach(i->{
            MapLog maplog = MapLog.builder()
                    .title("제목"+i)
                    .content("입력된 내용"+i)
                    .lat(37.0129392992)
                    .lng(128.21903882)
                    .writer(Member.builder().email("kjt407@nate.com").build())
                    .build();
            mapLogRepository.save(maplog);
        });
        System.out.println(mapLogRepository.findAll());
    }


    @Test
    @Transactional @Rollback(false)
    public void getMapLogTest(){
        System.out.println(mapLogRepository.findById(71L).get());
    }

    @Test
    @Transactional @Rollback(false)
    public void addLike(){
        Member member = Member.builder().email("kjt40700@gmail.com").build();
        MapLog mapLog = MapLog.builder().lno(72L).build();

        if(likesRepository.existsByMemberAndMapLog(member, mapLog)){
            System.out.println("이미 중복된 좋아요 요청입니다");
            return;
        } else {
            Likes likes = Likes.builder()
                    .member(member)
                    .mapLog(mapLog)
                    .build();
            likesRepository.save(likes);
            System.out.println(mapLogRepository.findById(71L).get());

            return;
        }
    }

    @Test
    @Transactional
    public void getMyListTests(){
        Member member = Member.builder().email("kjt40700@gmail.com").build();

        System.out.println(mapLogRepository.getMyList(member));

        return;
    }

}
