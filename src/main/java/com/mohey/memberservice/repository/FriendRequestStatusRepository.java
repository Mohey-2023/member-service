package com.mohey.memberservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mohey.memberservice.domain.FriendRequestStatus;

public interface FriendRequestStatusRepository extends JpaRepository<FriendRequestStatus, Long> {
	FriendRequestStatus findFriendRequestStatusById(Long id);
}
