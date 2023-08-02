package com.mohey.memberservice.repository;

import com.mohey.memberservice.domain.FriendRelation;
import com.mohey.memberservice.domain.Member;
import com.mohey.memberservice.domain.MemberProfileImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRelationRepository extends JpaRepository<FriendRelation, Long> {
    FriendRelation findByMemberIdAndFriendId(Member my , Member You);

    boolean existsByMemberIdAndFriendId(Member my, Member You);


}
