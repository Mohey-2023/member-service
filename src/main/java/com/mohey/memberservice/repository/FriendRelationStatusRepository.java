package com.mohey.memberservice.repository;

import com.mohey.memberservice.domain.FriendRelation;
import com.mohey.memberservice.domain.FriendRelationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRelationStatusRepository extends JpaRepository<FriendRelationStatus, Long> {

}
