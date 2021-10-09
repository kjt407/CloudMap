package com.jongtk.cloudmap.repository;

import com.jongtk.cloudmap.entity.Friend;
import com.jongtk.cloudmap.entity.Member;
import org.hibernate.query.criteria.internal.predicate.BooleanExpressionPredicate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FriendRepository extends JpaRepository<Friend, Long> {

    @Query("select fr from Friend fr where fr.member = :member")
    List<Friend> findByMember(Member member);

    Boolean existsByMemberAndFriend(Member member, Member friend);

    Optional<Friend> findByMemberAndFriend(Member member, Member friend);

}
