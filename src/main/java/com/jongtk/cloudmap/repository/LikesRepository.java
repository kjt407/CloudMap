package com.jongtk.cloudmap.repository;

import com.jongtk.cloudmap.entity.Likes;
import com.jongtk.cloudmap.entity.MapLog;
import com.jongtk.cloudmap.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
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

    @Query("delete from Likes likes where likes.member = :member")
    void deleteByEmail(Member member);
}
