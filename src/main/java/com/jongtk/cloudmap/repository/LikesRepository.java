package com.jongtk.cloudmap.repository;

import com.jongtk.cloudmap.entity.Likes;
import com.jongtk.cloudmap.entity.MapLog;
import com.jongtk.cloudmap.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Long> {

    Boolean existsByMemberAndMapLog(Member member, MapLog mapLog);

    Optional<Likes> findByMemberAndMapLog(Member member, MapLog mapLog);

    @Query("select likes from Likes likes where likes.member = :member order by likes.idx desc ")
    List<Likes> findByMember(Member member);

    @Query("select likes from Likes likes where likes.mapLog = :mapLog")
    List<Likes> getLikes (MapLog mapLog);

    @Modifying
    @Query("delete from Likes likes where (likes.member = :member and likes.mapLog in (select mapLog from MapLog mapLog where mapLog.writer = :friend)) or (likes.member = :friend and likes.mapLog in (select mapLog from MapLog mapLog where mapLog.writer = :member))")
    void deleteByMemberAndFriend(Member member, Member friend);

    @Modifying
    @Query("delete from Likes likes where likes.member = :member or likes.mapLog in (select mapLog from MapLog mapLog where mapLog.writer = :member)")
    void deleteAllByMember(Member member);

}
