package com.jongtk.cloudmap.repository;

import com.jongtk.cloudmap.entity.MapLog;
import com.jongtk.cloudmap.entity.MapLogImage;
import com.jongtk.cloudmap.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MapLogRepository extends JpaRepository<MapLog, Long> {
    @Query("select ml from MapLog ml where ml.writer = :username")
    List<MapLog> getMyList(Member username);

    @Query("select ml from MapLog ml where ml.lno = :lno and ml.writer = :username")
    Optional<MapLog> getMyLog(long lno, Member username);

    @Modifying
    @Query("delete from MapLog mapLog where mapLog.writer = :member")
    void deleteAllByMember(Member member);

}
