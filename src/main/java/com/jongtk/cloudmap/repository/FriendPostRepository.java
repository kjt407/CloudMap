package com.jongtk.cloudmap.repository;

import com.jongtk.cloudmap.entity.Friend;
import com.jongtk.cloudmap.entity.FriendPost;
import com.jongtk.cloudmap.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FriendPostRepository extends JpaRepository<FriendPost, Long> {

//    @Query("select fr from Friend fr where fr.member = :member")
    List<FriendPost> findByReceiver(Member receiver);

    boolean existsBySenderAndReceiver(Member sender, Member receiver);

    Optional<FriendPost> findBySenderAndReceiver(Member sender, Member receiver);

    @Modifying
    @Query("delete from FriendPost fp where fp.sender = :member or fp.receiver = :member")
    void deleteAllByMember(Member member);
}
