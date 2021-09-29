package com.jongtk.cloudmap.repository;

import com.jongtk.cloudmap.entity.MapLog;
import com.jongtk.cloudmap.entity.MapLogImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MapLogImageRepository extends JpaRepository<MapLogImage, Long> {

}
