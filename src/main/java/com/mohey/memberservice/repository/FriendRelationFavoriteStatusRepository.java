package com.mohey.memberservice.repository;

import com.mohey.memberservice.domain.FriendRelation;
import com.mohey.memberservice.domain.FriendRelationFavoriteStatus;
import com.mohey.memberservice.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRelationFavoriteStatusRepository extends JpaRepository<FriendRelationFavoriteStatus, Long> {

}
