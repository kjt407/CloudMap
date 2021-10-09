package com.jongtk.cloudmap;

import com.jongtk.cloudmap.entity.Friend;
import com.jongtk.cloudmap.entity.MapLog;
import com.jongtk.cloudmap.entity.Member;
import com.jongtk.cloudmap.repository.FriendPostRepository;
import com.jongtk.cloudmap.repository.FriendRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.IntStream;

@SpringBootTest
public class FriendTests {

    @Autowired
    private FriendRepository friendRepository;
    @Autowired
    private FriendPostRepository friendPostRepository;

    @Test
    @Transactional @Rollback(false)
    public void addFriend(){
        Member member = Member.builder().email("kjt407@nate.com").build();
//        Member target = Member.builder().email("kjt40700@gmail.com").build();
        Member target = Member.builder().email("kjt4070@naver.com").build();
//        Member target = Member.builder().email("kjt407@naver.com").build();
//        Member target = Member.builder().email("kjt40700@gmail.com").build();

        Friend friend = Friend.builder()
                .member(member)
                .friend(target)
                .build();
        Friend friendr = Friend.builder()
                .member(target)
                .friend(member)
                .build();

        friendRepository.save(friend);
        friendRepository.save(friendr);
    }

}
