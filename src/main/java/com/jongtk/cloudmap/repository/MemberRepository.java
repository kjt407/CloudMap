package com.jongtk.cloudmap.repository;

import com.jongtk.cloudmap.entity.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,String> {

    @EntityGraph(attributePaths = {"roleSet"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("select m from Member m where m.email = :Email and m.fromSocial = :fromSocial")
    Optional<Member> findByEmail(String Email, boolean fromSocial);

    @Query("select mb from Member mb where (mb.name like %:str% or mb.email like %:str%) and not mb.email = :username")
    List<Member> findByNameOrEmail(String str, String username);

}
