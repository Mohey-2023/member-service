package com.mohey.memberservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mohey.memberservice.domain.FriendRequest;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {
	FriendRequest findFriendRequestByAlarmUuid(String alarmUuid);
}
