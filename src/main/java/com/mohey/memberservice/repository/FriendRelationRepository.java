package com.mohey.memberservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mohey.memberservice.domain.FriendRelation;
import com.mohey.memberservice.domain.Member;

public interface FriendRelationRepository extends JpaRepository<FriendRelation, Long> {
	FriendRelation findByMemberIdAndFriendId(Member my, Member You);

	boolean existsByMemberIdAndFriendId(Member my, Member You);

}
