package com.mohey.memberservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mohey.memberservice.domain.MemberDeviceAliveStatus;

public interface MemberDeviceAliveStatusRepository extends JpaRepository<MemberDeviceAliveStatus, Long> {

}
