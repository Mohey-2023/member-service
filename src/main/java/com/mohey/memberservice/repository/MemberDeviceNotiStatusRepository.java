package com.mohey.memberservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mohey.memberservice.domain.MemberDevice;
import com.mohey.memberservice.domain.MemberDeviceNotiStatus;

public interface MemberDeviceNotiStatusRepository extends JpaRepository<MemberDeviceNotiStatus, Long> {
	Optional<MemberDeviceNotiStatus> findFirstByMemberDeviceIdOrderByCreatedDatetimeDesc(MemberDevice memberDeviceId);
}
