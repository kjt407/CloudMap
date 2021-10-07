package com.jongtk.cloudmap.repository;

import com.jongtk.cloudmap.entity.FreindPost;
import com.jongtk.cloudmap.entity.Likes;
import com.jongtk.cloudmap.entity.MapLog;
import com.jongtk.cloudmap.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FreindPostRepository extends JpaRepository<FreindPost, Long> {

}
