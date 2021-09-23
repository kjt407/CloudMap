package com.jongtk.cloudmap.repository;

import com.jongtk.cloudmap.entity.Likes;
import com.jongtk.cloudmap.entity.MapLog;
import com.jongtk.cloudmap.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikesRepository extends JpaRepository<Likes, Long> {
    Boolean existsByMemberAndMapLog(Member member, MapLog mapLog);
}
