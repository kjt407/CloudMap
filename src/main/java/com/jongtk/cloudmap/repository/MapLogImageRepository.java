package com.jongtk.cloudmap.repository;

import com.jongtk.cloudmap.entity.MapLog;
import com.jongtk.cloudmap.entity.MapLogImage;
import com.jongtk.cloudmap.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MapLogImageRepository extends JpaRepository<MapLogImage, Long> {
    @Modifying
    @Query("delete from MapLogImage images where images.mapLog in (select mapLog from MapLog mapLog where mapLog.writer = :member)")
    void deleteAllByMember(Member member);

    @Modifying
    @Query("delete from MapLogImage images where images.uuid = :uuid")
    void deleteByUuid(String uuid);
}
